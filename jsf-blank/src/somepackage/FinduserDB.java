package somepackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FinduserDB implements AuthInt {
	private String userid = null;
	private String passid = null;
	private String status = null;
	private Userauth user;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Userauth getUser() {
		return user;
	}
	public void setUser(Userauth user) {
		this.user = user;
	}
	@Override
	public Userauth finduser (String username, String passwd, String usertype)throws SQLException {
		 
			try
			{
			Connection con1 = null;
			Statement stmt = null;
			String user = username.toLowerCase();
			String pass = passwd;
			String usert = null;
			switch (usertype) {
			case "Student": usert="students";
				break;
			case "Instructor": usert="instructors"; 
				break;
			case "Grader": usert="graders";
				break;
			case "Admin" : usert="admin";
			default:
				break;
			}
		    
			Dbcon lc = new Dbcon();
			con1 = lc.getLocalConnection();
			stmt = con1.createStatement();
			String queryU = "select id,name,surname,mail from "+usert+" where \""+user+"\" =mail";
			ResultSet rs1 = null;
			rs1 = stmt.executeQuery(queryU );
			String name = null;
			String mail = null;
			String surname = null;
			if(rs1.next()){
			this.userid = rs1.getString("id");
			name = rs1.getString("name");
			mail = rs1.getString("mail"); 
			surname = rs1.getString("surname");
			}else this.userid=null;
			
			rs1.close();
			if(this.userid!=null){
				queryU ="select id,status from h_passwd where hash=\""+pass+"\"and user=\""+usert+"\" and user_id="+this.userid;
				ResultSet rs2 = null;
				rs2 = stmt.executeQuery(queryU);
				
				if(rs2.next()){
				this.passid = rs2.getString("id");
				this.status = rs2.getString("status");
				}
				else this.passid = null;
				rs2.close();
				if(this.passid!=null)
				this.user = new Userauth(this.userid, this.passid, mail, name,surname,usert,this.status);
				else {this.user = null; return this.user ;}
			}else {this.user = null; return this.user ;}
			
			}catch(Exception ex)
			{
				System.out.println("Exception is:-"+ex.getMessage());
			}
			return this.user ;
		}
	
	public String change_pass(String newpass,String passid){
		try {
			Connection con1 = null;
			Statement stmt = null;
			Dbcon lc = new Dbcon();
			con1 = lc.getLocalConnection();
			stmt = con1.createStatement();
			String queryU = "update h_passwd set hash=\""+newpass+"\",status=\"Active\" where id=\""+passid+"\"";
			stmt.executeUpdate(queryU);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant update passwd "+e);
			
		}
		
		return "Fail";	
	}
	
}

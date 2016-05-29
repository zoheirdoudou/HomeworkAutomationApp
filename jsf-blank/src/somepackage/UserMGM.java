package somepackage;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
@ManagedBean

public class UserMGM implements UserManag {
	Connection con1 = null;
	Dbcon lc = new Dbcon();
	
	
		public UserMGM() {
	// TODO Auto-generated constructor stub
		try {
			con1 = lc.getLocalConnection();
		} catch (Exception e) {
			System.out.println("Cont connect to DB "+e);
			// TODO: handle exception
		}
	}
	
	
	
	
	@Override
	public String adduser(String name,String surname,String mail,String state,String gender,String usertype,String univ_n) 
			throws SQLException, NoSuchAlgorithmException {
		Statement stmt = null;
		String usert=null;
		String queryU = null;
		stmt = this.con1.createStatement();
		try {
			switch (usertype) {
			case "Student": usert="students";
				break;
			case "Instructor": usert="instructors"; 
				break;
			case "Grader": usert="graders";
				break;
				default:		
			}
			queryU = "insert into "+usert+" (name,surname,mail,gender,univ_n) values (\""+name+"\",\""+surname+"\",\""+mail+"\",\""+gender+"\",\""+univ_n+"\")";
			//ResultSet rs1 = null;
			stmt.executeUpdate(queryU);
		} catch (Exception e) {
			System.out.println("Cont insert the query" +e);
			return "fail";
			// TODO: handle exception
		}
		//String passwd = null;
		String user_id = null;
		ResultSet rs1 =null;
		queryU = "select id from "+usert+" where \""+mail+"\" =mail ";
		try {
			 rs1 = stmt.executeQuery(queryU);
			if(rs1.next()) user_id = rs1.getString("id"); else user_id = null;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cont get the user id" +e);
			return "fail";
		}
		Passwd newpasswd = new Passwd();
		String passwd = newpasswd.SaltString();
		newpasswd.setPasswordToHash(passwd); 
		String 	h_passwd = Passwd.get_SHA_512_SecurePassword();
		queryU = "insert into h_passwd (hash,status,user,user_id) values ('"+h_passwd+"','"+state+"','"+usert+"','"+user_id+"')";		
		try { 
			stmt.executeUpdate(queryU);
			Mail firstmail = new Mail();
			firstmail.setEmail(mail);
			firstmail.setName(name+" "+surname);
			firstmail.setPasswd(passwd);
			firstmail.setSubject("New Account at Homework Application");
			firstmail.send();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cont insert the password" +e);
			return "Fail";
		}
		
		return "Success";
	}

	@Override
	public String modify(User user ) throws SQLException, NoSuchAlgorithmException
	{
		Statement stmt = null;
		String queryU = null;
		stmt = this.con1.createStatement();
		queryU = "update "+user.getUsertype()+" set univ_n=\""+user.getUniv_n()+"\", name=\""+user.getName()+"\",surname=\""+user.getSurname()+"\" where id=\""+user.getId()+"\"";
		String queryU2 = "update h_passwd set status=\""+user.getState()+"\" where user_id=\""+user.getId()+"\" and user=\""+user.getUsertype()+"\"";
		try {
			stmt.executeUpdate(queryU);
			stmt.executeUpdate(queryU2);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant update user info "+e);
		}
		if(user.getState().equals("Close"))
		{
			Passwd newpasswd = new Passwd();
			String passwd = newpasswd.SaltString();
			newpasswd.setPasswordToHash(passwd); 
			String 	h_passwd = Passwd.get_SHA_512_SecurePassword();
			queryU = "update h_passwd set hash='"+h_passwd+"' where "+user.getPassid()+"= id";		
			try { 
				stmt.executeUpdate(queryU);
				Mail firstmail = new Mail();
				firstmail.setEmail(user.getMail());
				firstmail.setName(user.getName()+" "+user.getSurname());
				firstmail.setPasswd(passwd);
				firstmail.setSubject("Reset passwd at Homework Application");
				firstmail.send();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Cont insert the password" +e);
				
			}
		}
		
		return null;
	}
	
	
	public List<User> listuser(String byid,String byname, String bysurname, String bytype,int page) throws SQLException{
		
		switch (bytype) {
		case "Student": bytype="students";
			break;
		case "Instructor": bytype="instructors"; 
			break;
		case "Grader": bytype="graders";
			break;
		case "Admin" : bytype="admin";
		default:
			break;
		}
		String and="";
		String qurey = "from h_passwd P, ";
		if(!bytype.equals("")) qurey=qurey+bytype+" T";
		qurey= qurey+" where P.user_id=T.id  ";
		and = " and ";
		if(!byid.isEmpty()||!byname.isEmpty()||!bysurname.isEmpty())
		{  //qurey = qurey+" where ";
		if(!byid.equals(""))
		{	qurey=qurey+" and univ_n=\""+byid+"\" "; 	
			 and=" and ";
		}
		if(!byname.equals(""))
			{	
				qurey=qurey+and+" name=\""+byname+"\" "; 
				 and=" and ";
			}
		if(!bysurname.equals("")) 
			{	
				qurey=qurey+and+"  surname=\""+bysurname+"\" ";
			}
		}
		int max,min;
		min = 15 * (page - 1);
		max = 15 * page;
		qurey = qurey+" limit "+min+","+max;
		qurey = "select P.id, P.status, T.id,T.univ_n,T.name,T.surname,T.mail "+qurey;
		
		Statement stmt = null;
		stmt = this.con1.createStatement();
		ResultSet rs1 = null;
		rs1 = stmt.executeQuery(qurey);
		List<User> userlist =  new ArrayList<User>() ;  
		int i;
		i = 0;
		while(rs1.next()){
			
			User newuser = new User();
			newuser.setUniv_n(rs1.getString("T.univ_n"));
			newuser.setName(rs1.getString("T.name"));
			newuser.setSurname(rs1.getString("T.surname"));
			newuser.setMail(rs1.getString("T.mail"));
			newuser.setUsertype(bytype);
			newuser.setState(rs1.getString("P.status"));
			newuser.setId(rs1.getString("T.id"));
			newuser.setPassid(rs1.getString("P.id"));
			userlist.add(newuser);
			i++;
		}
		return userlist;
	 //return null;
	}
	  

}

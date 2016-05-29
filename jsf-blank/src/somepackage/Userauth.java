package somepackage;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@SessionScoped
public class Userauth implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userid;	
	private String passid;
	private String username;
	private String passwd;
	private String name;
	private String surname;
	private String usertype;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Userauth(String userid,String passid,String username,String name,String surname,String usertype,String status)
	{
		this.userid = userid;
		this.passid = passid;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.usertype = usertype;
		this.status = status;
	}
	public String getPassid() {
		return passid;
	}
	public void setPassid(String passid) {
		this.passid = passid;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Userauth(String username,String passwd,String usertype){
		this.username = username;
		this.passwd = passwd;
		this.username = usertype;
	}
	/*
	public String addAction() throws SQLException {
		try
		{
		Connection con1 = null;
		Statement stmt = null;
		String user = this.username;
		String pass = this.passwd;
		String usert = null;
		switch (this.usertype) {
		case "Student": usert="students";
			break;
		case "Instructor": usert="instructors"; 
			break;
		case "Grader": usert="graders";
			break;
		case "Admin" : usert="instructors";
		default:
			break;
		}
		
		Dbcon lc = new Dbcon();
		con1 = lc.getLocalConnection();
		stmt = con1.createStatement();
		String queryU = "select id from"+usert+"where"+user+"=mail";
		ResultSet rs1 = null;
		rs1 = stmt.executeQuery(queryU);
		this.userid = rs1.getString(0); 
		if(this.userid!=null){
			queryU ="select id from h_passwd where hash="+pass+"and user="+usert+"and userid="+rs1.getString(0);
			ResultSet rs2 = null;
			rs2 = stmt.executeQuery(queryU);
			this.passid = rs2.getString(0);
		}else this.passid=null;
		
		}catch(Exception ex)
		{
			System.out.println("Exception is:-"+ex.getMessage());
		}
		return "success";
	}
*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void removeSessionBean(final String beanName) {
	    try{
	    	//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
	    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    }catch(Exception e)
	    {
	    	System.out.println("cant remove");
	    }
	}
}

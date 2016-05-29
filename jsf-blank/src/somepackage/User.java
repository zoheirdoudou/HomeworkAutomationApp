package somepackage;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;

@ManagedBean
@ViewScoped
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String passid;
	private String name =null;
	private String surname =null;
	private String mail =null;
	private String usertype=null;
	private String gender=null;
	private String state=null;
	private String univ_n=null;
	private String account_s ;
	private boolean isEditablestatus;
	private String[] userstatusL = {"Active","Close","Disable"};
	private static UserMGM newuser = new UserMGM();
	private List<User> userlist =  new ArrayList<User>() ;
	private HtmlDataTable usertable ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEditablestatus() {
		return isEditablestatus;
	}

	public void setEditablestatus(boolean isEditablestatus) {
		this.isEditablestatus = isEditablestatus;
	}

	public String[] getUserstatusL() {
		return userstatusL;
	}

	public void setUserstatusL(String[] userstatusL) {
		this.userstatusL = userstatusL;
	}

	
	public HtmlDataTable getUsertable() {
		return usertable;
	}

	public void setUsertable(HtmlDataTable usertable) {
		this.usertable = usertable;
	}

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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail.toLowerCase();
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUniv_n() {
		return univ_n;
	}
	public void setUniv_n(String univ_n) {
		this.univ_n = univ_n;
	}
	public List<User> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}
	public String newuser(){
		try {
			if(!this.name.isEmpty() && !this.surname.isEmpty() && !this.mail.isEmpty() &&
					!this.state.isEmpty() && !this.gender.isEmpty() && !this.univ_n.isEmpty() && !this.usertype.isEmpty())
			{
				if(newuser.adduser(name, surname, mail, state, gender, usertype, univ_n)=="Success")
				account_s ="Seccess";else account_s = "Fail"; 
			}
			} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant"+e);
		}
		
		return null;
	}
	public String getAccount_s() {
		return account_s;
	}
	public void setAccount_s(String account_s) {
		this.account_s = account_s;
	}
	public static UserMGM getNewuser() {
		return newuser;
	}
	public static void setNewuser(UserMGM newuser) {
		User.newuser = newuser;
	}
	public String adsearchUser() throws SQLException{
		
		UserMGM userlist = new UserMGM();
		this.userlist = userlist.listuser(this.univ_n, this.name,this.surname, this.usertype, 1);
		return null;
	}
	public void modify(User user) throws IOException, SQLException, NoSuchAlgorithmException{
		this.isEditablestatus=false; 
		UserMGM userUp = new UserMGM();
		userUp.modify(user);
		
	}
	public void update(){
		this.isEditablestatus=false;
	}

	public String getPassid() {
		return passid;
	}

	public void setPassid(String passid) {
		this.passid = passid;
	}


}

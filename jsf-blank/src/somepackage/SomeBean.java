package somepackage;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import java.io.Serializable;


@ManagedBean(name="someBean")
@SessionScoped 
public class SomeBean implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
/**
	 * 
	 */
	
	private String newpasswd;
	private String someProperty = "Error";
	public	Passwd h_pass = new Passwd();
	private String username = "username";
	private String passwd;
	private String status = null;
	private Userauth useraut = null;
	private String usertype[] = {"Student","Grader","Instructor","Admin"};
	private static AuthInt userlookup = new FinduserDB();
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNewpasswd() {
		return newpasswd;
	}

	public void setNewpasswd(String newpasswd) {
		this.newpasswd = newpasswd;
	}

	
	public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			
			this.username = username.toLowerCase();
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) throws NoSuchAlgorithmException {
			h_pass.setPasswordToHash(passwd); 
			String 	h_passwd = Passwd.get_SHA_512_SecurePassword();
			this.passwd = h_passwd;
		}

		public Userauth getUseraut() {
			return useraut;
		}

		public void setUseraut(Userauth useraut) {
			this.useraut = useraut;
		}


	  public String getSomeProperty() {
		  return(someProperty);
  }

  public void setSomeProperty(String someProperty) {
    this.someProperty = someProperty;
  }

  public String someActionControllerMethod()  throws SQLException, NoSuchAlgorithmException {
	 // char[] password = passwd.toCharArray(); 
	  
	  
	  if(!this.someProperty.isEmpty()&& !this.passwd.isEmpty() && !this.username.isEmpty())
	  {	  useraut = userlookup.finduser(this.username, this.passwd,this.someProperty);
		  
	  	  if(useraut!=null)
	  	  {
		  if(useraut.getStatus().equals("Close"))
		  return("passwd");
		  else {
			  switch (useraut.getUsertype()) {
			case "students": return("student"); 
			case "instructors":return("instructor");
			case "graders":return("grader");
			case "admin":return("admin");
			default:
				break;
			}}
		  }
	  }
	  this.setStatus("Fail");
	  //logout();
	  return null;
  }
  
  public String returnindex(){
	  return("index");
  }
  
public String logout() throws SQLException {
	    removeSessionBean("someBean");
	 /* HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			   .getExternalContext().getSession(true);
			    session.removeAttribute("somebean");
		*/	    
			 		   
	  return("index.xhtml?faces-redirect=true");  // Means to go to index.xhtml (since condition is not mapped in faces-config.xml)
  }


  
    public void removeSessionBean(final String beanName) {
	    try{
	    	//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
	    	this.useraut.removeSessionBean(beanName);
	    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    	
	    }catch(Exception e)
	    {
	    	System.out.println("cant remove");
	    }
	}

	public String[] getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype[]) {
		this.usertype = usertype;
	}

}

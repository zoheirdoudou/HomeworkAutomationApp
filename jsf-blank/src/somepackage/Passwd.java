package somepackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped
public class Passwd {
	private static	String passwordToHash,salt="doudou zoheir";
	private String chgstate="";
	private String newpass;
	private String confirmation;
	public String getChgstate() {
		return chgstate;
	}
	public void setChgstate(String chgstate) {
		this.chgstate = chgstate;
	}
	public static String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		Passwd.salt = salt;
	}
	public static String getPasswordToHash() {
		return passwordToHash;
	}
	public void setPasswordToHash(String passwordToHash) {
		Passwd.passwordToHash = passwordToHash;
	}
	public String SaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@_-!";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	public static String get_SHA_512_SecurePassword() throws NoSuchAlgorithmException
	  {
		
			String generatedPassword = null;
	      try {
	          MessageDigest md = MessageDigest.getInstance("SHA-512");
	          md.update(getSalt().getBytes());
	          byte[] bytes = md.digest(getPasswordToHash().getBytes());
	          StringBuilder sb = new StringBuilder();
	          for(int i=0; i< bytes.length ;i++)
	          {
	              sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	          }
	          generatedPassword = sb.toString();
	      } 
	      catch (NoSuchAlgorithmException e) 
	      {
	          e.printStackTrace();
	      }
	      return generatedPassword;   
	  }
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String passchange(){
		if(this.newpass.equals(this.confirmation))
		{
			FinduserDB newpasswd = new FinduserDB();
			String pass=null;
			String passid = null;
			try {
				FacesContext context = FacesContext.getCurrentInstance();
				SomeBean login =  (SomeBean) context.getExternalContext().getSessionMap().get("someBean");
				pass = login.getPasswd();
				passid = login.getUseraut().getPassid();
				 //System.out.println("paaaaaaaaa  "+user.getPasswd());
				this.passwordToHash= this.newpass;
				this.newpass = get_SHA_512_SecurePassword();
				if(!pass.equals(this.newpass)){
				this.chgstate =	newpasswd.change_pass(this.newpass, passid);
				if(this.chgstate.equals("Success"))
				switch (login.getSomeProperty()) {
				case "Student": return("student"); 
				case "Instructor":return("instructor");
				case "Grader":return("grader");
				case "Admin":return("admin");
				default:
					break;
				}				
				else return null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Cont get the passwd  "+e );
			}
			
			
		}
		this.chgstate="Fail";
		return null;
		
	}
}

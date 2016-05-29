package somepackage;

import javax.faces.bean.*;

@ManagedBean

public class Admin_task {

	private String manage_user;
	public String getManage_user() {
		return manage_user;
	}
	public void setManage_user(String manage_user) {
		this.manage_user = manage_user;
	}

	public String manageuser(){
		switch(manage_user)
		{
		case "add": return("adduser"); 
		case "modify": return("modify");
		}
		return("log");
	}
	
	

}

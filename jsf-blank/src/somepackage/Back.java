package somepackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped
public class Back {

	public String returnback(){
		SomeBean user = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		String usertype = user.getSomeProperty();
		switch (usertype) {
		case "Student": return("student");
			
		case "Instructor":return("instructor");
			
		case "Grader": return("grader");
		case "Admin":return("admin");
			default:
		}
		return null;
		
	}
}

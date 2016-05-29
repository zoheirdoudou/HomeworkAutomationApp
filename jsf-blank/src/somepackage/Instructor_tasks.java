package somepackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Instructor_tasks {

	private String[] course_taskL={"Create Course","Modify Course","Assign Grader to Course","Assign Students to Course"};
	private String[] homework_taskL={"Create Homework","Modify Homework","Grade Homework"};
	private String selcoursetask;

	
	public String[] getCourse_taskL() {
		return course_taskL;
	}
	public void setCourse_taskL(String[] course_taskL) {
		this.course_taskL = course_taskL;
	}
	public String getSeltask() {
		return selcoursetask;
	}
	public void setSeltask(String seltask) {
		this.selcoursetask = seltask;
	}
	public String[] getHomework_taskL() {
		return homework_taskL;
	}
	public void setHomework_taskL(String[] homework_taskL) {
		this.homework_taskL = homework_taskL;
	}
	public String managecourse(){
		switch (this.selcoursetask) {
		case "Create Course": return("createcourse");
		case "Modify Course": return("modifycourse");
		case "Assign Grader to Course":return("assigngrader");
		case "Assign Students to Course" : return("assignstudent");
		default:
			break;
		}
		return null;
	}
	public String managehomework()
	{
		switch (this.selcoursetask) {
		case "Create Homework": return("createhomework");
		case	"Modify Homework":return("modifyhomework");
		case "Grade Homework":return("gradehomework");
		default:
			break;
		}
		return null;

	}
}

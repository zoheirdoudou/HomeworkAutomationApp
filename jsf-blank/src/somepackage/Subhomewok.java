package somepackage;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Subhomewok {

	
	private User student = new User();
	private String homework_id;
	private String student_id;
	private String remaraq;
	private int grade;
	private String status;
	private boolean edit;
	private List<String> filename = new ArrayList<String>();
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public String getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(String homework_id) {
		this.homework_id = homework_id;
	}
	public String getRemaraq() {
		return remaraq;
	}
	public void setRemaraq(String remaraq) {
		this.remaraq = remaraq;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public List<String> getFilename() {
		return filename;
	}
	public void setFilename(List<String> filename) {
		this.filename = filename;
	}
	
}

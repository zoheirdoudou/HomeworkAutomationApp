package somepackage;


import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;



@ManagedBean
@ViewScoped
public class CreateCourse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String code;
	private String description;
	private String semester;
	private List<User> graderL = new  ArrayList<User>() ;
	private String[] statusL = {"Active","Disactive","Disable"};
	private String selstatus;
	private String creation_status;
	private String[] semesterL = {"autumn","spring"};
	private static CourseLookup courseLP = new CourseBL();
	private CourseBL newcourse;
	private List<CourseBL> courselist = new ArrayList<CourseBL>();
	private boolean editname=false;
	private boolean selectgrader=false;
	private String up_stat;
	private UploadedFile up_file;
	private InputStream in_file;
	
	public InputStream getIn_file() {
		return in_file;
	}
	public void setIn_file(InputStream in_file) {
		this.in_file = in_file;
	}
	public UploadedFile getUp_file() {
		return up_file;
	}
	public void setUp_file(UploadedFile up_file) {
		this.up_file = up_file;
	}
	public boolean isSelectgrader() {
		return selectgrader;
	}
	public void setSelectgrader(boolean selectgrader) {
		this.selectgrader = selectgrader;
	}
	public boolean isEditname() {
		return editname;
	}
	public void setEditname(boolean editname) {
		this.editname = editname;
	}
	public List<CourseBL> getCourselist() {
		return courselist;
	}
	public void setCourselist(List<CourseBL> courselist) {
		this.courselist = courselist;
	}
	public String getCreation_status() {
		return creation_status;
	}
	public void setCreation_status(String creation_status) {
		this.creation_status = creation_status;
	}
	public CourseBL getNewcourse() {
		return newcourse;
	}
	public void setNewcourse(CourseBL newcourse) {
		this.newcourse = newcourse;
	}
	public String[] getSemesterL() {
		return semesterL;
	}
	public void setSemesterL(String[] semesterL) {
		this.semesterL = semesterL;
	}
	public CreateCourse() throws SQLException {
		// TODO Auto-generated constructor stub
	/*for (User value : this.graderL) {
		this.usernaleL.add(value.getName());
		
	}*/
	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<User> getGraderL() {
		return graderL;
	}
	public void setGraderL(ArrayList<User> graderL) {
		this.graderL = graderL;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	public String[] getStatusL() {
		return statusL;
	}
	public void setStatusL(String[] statusL) {
		this.statusL = statusL;
	}
	public String getSelstatus() {
		return selstatus;
	}
	public void setSelstatus(String selstatus) {
		this.selstatus = selstatus;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String createcourse(){
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().;
		SomeBean inst_id = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		this.creation_status= courseLP.createcourse(this.name, this.code,this.description, this.semester, this.selstatus,inst_id.getUseraut().getUserid());
			return null;
	}
	public String searchcourse() throws SQLException
	{
		SomeBean inst_id = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		
		this.courselist =  courseLP.listcourse(inst_id.getUseraut().getUserid(),inst_id.getSomeProperty());
		UserMGM listgrader = new UserMGM();
		this.graderL =  listgrader.listuser("","","", "Grader", 1);
		
		return null;
	}
	public String modify(CourseBL coursemodif)
	{
		coursemodif.setEdit(false);
		this.up_stat = courseLP.updatecourse(coursemodif);
		return null;
	}
	public void selcourse(CourseBL selected) throws SQLException{
		this.editname = true;
		this.newcourse = selected;
		UserMGM listgrader = new UserMGM();
		this.graderL =  listgrader.listuser("","","", "Grader", 1);
	}
	public void assigngrader(User U) throws SQLException
	{
		
	this.up_stat =	courseLP.asigncourse(U.getId(),this.newcourse.getCourse_id());
	this.searchcourse();
	}
	public void update_grader(AjaxBehaviorEvent event) throws SQLException
	{	
		this.searchcourse();
	}
	public String update(CourseBL course)
	{
		course.setEdit(false);
		
		
		return null;
	}
	public String getUp_stat() {
		return up_stat;
	}
	public void setUp_stat(String up_stat) {
		this.up_stat = up_stat;
	}
	public void showupload(CourseBL selected) throws IOException
	{	this.setNewcourse(selected);
		//this.setIn_file(null);
		this.selectgrader=true;
	}
	public String studentupload() throws IOException{
		
		if(this.getIn_file()==null) { this.up_stat = "You Should upload file"; return null;}
		
		this.up_stat =	courseLP.uploadstudents(this.getIn_file(), this.getNewcourse());
		
		this.selectgrader=false; 
		this.setIn_file(null);
		
		return null;
	}
	public void handleFileUpload(FileUploadEvent event) throws IOException  {
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.setIn_file(event.getFile().getInputstream());
        
	}
}

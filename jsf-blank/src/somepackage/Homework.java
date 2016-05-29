package somepackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

//import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
@ManagedBean
@ViewScoped
public class Homework implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String homework_id;
	private String name;
	private String description;
	private java.util.Date from_d;
	private java.util.Date to_d;
	
	private CourseBL course;
	private String status;
	private String[] statusL = {"Open","Close","Disable"};
	private List<Subhomewok> listsubhomework = new ArrayList<Subhomewok>(); 
	private static CourseLookup courseLP = new CourseBL();
	private static HomeworkLookup addhomework = new HomeworkBL();
	private List<CourseBL> courselist = new ArrayList<CourseBL>();
	private List<HomeworkBL> homeworkL = new ArrayList<HomeworkBL>();
	private List<InputStream> fileL = new ArrayList<InputStream>(); 
	private List<User> course_student = new ArrayList<User>();
	private boolean selected;
	private int number_f;
	private String insert_s;
	private List<UploadedFile> fileLup = new ArrayList<>();
	
	public List<Subhomewok> getListsubhomework() {
		return listsubhomework;
	}
	public void setListsubhomework(List<Subhomewok> listsubhomework) {
		this.listsubhomework = listsubhomework;
	}
	public List<User> getCourse_student() {
		return course_student;
	}
	public void setCourse_student(List<User> course_student) {
		this.course_student = course_student;
	}
	
	public int getNumber_f() {
		return number_f;
	}
	public void setNumber_f(int number_f) {
		this.number_f = number_f;
	}
	public String getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(String homework_id) {
		this.homework_id = homework_id;
	}
	public List<UploadedFile> getFileLup() {
		return fileLup;
	}
	public void setFileLup(List<UploadedFile> fileLup) {
		this.fileLup = fileLup;
	}
	public String getInsert_s() {
		return insert_s;
	}
	public void setInsert_s(String insert_s) {
		this.insert_s = insert_s;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Homework(){
		SomeBean user_id = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		if(user_id.getSomeProperty().equals("Instructor"))
		{
			this.setCourselist(courseLP.listcourse(user_id.getUseraut().getUserid(),user_id.getSomeProperty()));
			
		}else if(user_id.getSomeProperty().equals("Grader"))
		{	this.setCourselist(courseLP.listcourse(user_id.getUseraut().getUserid(),user_id.getSomeProperty()));
		}
		
		
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
	public java.util.Date getFrom_d() {
		return from_d;
	}
	public void setFrom_d(Date from_d) {
		this.from_d = from_d;
	}
	public java.util.Date getTo_d() {
		return to_d;
	}
	public void setTo_d(Date to_d) {
		this.to_d = to_d;
	}
	public CourseBL getCourse() {
		return course;
	}
	public void setCourse(CourseBL course) {
		if(course!=null)
		this.course = course;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getStatusL() {
		return statusL;
	}
	public void setStatusL(String[] statusL) {
		this.statusL = statusL;
	}
	public List<CourseBL> getCourselist() {
		return courselist;
	}
	public void setCourselist(List<CourseBL> courselist) {
		this.courselist = courselist;
	}
	public String selcourse(CourseBL sel)
	{
		this.course = sel;
		return null;
	}
	public	String newhomehork() throws Throwable
	{   
		Date current = new Date();	   		
		if(this.course==null){
			this.insert_s = "You Must Select Course";
			return null;
		}
		if(this.from_d.compareTo(this.to_d)==0||this.from_d.compareTo(to_d)>0||this.from_d.compareTo(current)<0)
		{
			this.insert_s = "Start Date Must be Before Due Date";
			return null;
		}
		
		Homework newhomework = new Homework();
		newhomework.name = this.name;
		newhomework.description = this.description;
		newhomework.from_d = this.from_d;
		newhomework.to_d = this.to_d;
		newhomework.course = this.course;
		newhomework.status = this.status;
		newhomework.number_f = this.number_f;
		this.homework_id= Homework.addhomework.createhomework(newhomework);
		if(homework_id.equals("Fail"))
		{
			this.insert_s = "Fail";
			return null;
		}else this.insert_s = "Success";
		
			for(int i=0;i<this.fileL.size();i++){
			
		
			
			try {
		       	 copyFile(this.getHomework_id()+"_"+this.fileLup.get(i).getFileName(),this.fileL.get(i),"homework/");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
		
			}
			
			SomeBean user_id = (SomeBean) FacesContext.getCurrentInstance().
					getExternalContext().getSessionMap().get("someBean");
			if(user_id.getSomeProperty().equals("Instructor"))
				return "instructor";
			else return "grader";
	}
	public static HomeworkLookup getAddhomework() {
		return addhomework;
	}
	public static void setAddhomework(HomeworkLookup addhomework) {
		Homework.addhomework = addhomework;
	}
	public void handleFileUpload(FileUploadEvent event) throws IOException  {
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.fileL.add(event.getFile().getInputstream());
        this.fileLup.add(event.getFile());
      	this.number_f++;	
   }
	public List<InputStream> getFileL() {
		return fileL;
	}
	public void setFileL(List<InputStream> fileL) {
		this.fileL = fileL;
	}
	 public void copyFile(String fileName, InputStream in,String place) {
         try {
            
        	 String destination = "/home/zoheirdoudou/workspace/jsf-blank/WebContent/"+place;
              // write the inputStream to a FileOutputStream
              OutputStream out = new FileOutputStream(new File(destination + fileName));
            
              int read = 0;
              byte[] bytes = new byte[1024];
            
              while ((read = in.read(bytes)) != -1) {
                  out.write(bytes, 0, read);
              }
            
              in.close();
              out.flush();
              out.close();
            
              
              } catch (IOException e) {
              System.out.println(e.getMessage());
              }
  } 
	public String listhomework(CourseBL course) throws IOException{
		
		
		if(course!=null)
		{
		
		this.course=course;	
		this.homeworkL = addhomework.listhomework(course);
		for(HomeworkBL list:this.homeworkL)
		{  showfile(list);
		}}
		SomeBean user_id = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		if(user_id.getSomeProperty().equals("Student")) return("studenthomework");
		return null;
		
	}
	public List<HomeworkBL> getHomehorkL() {
		return homeworkL;
	}
	public void setHomehorkL(List<HomeworkBL> homehorkL) {
		this.homeworkL = homehorkL;
	}
	public String update(HomeworkBL homework)
	{
		
		homework.setEdit(false);
		
		
		return null;
	}
	public String modifyHomework(HomeworkBL homework) throws IOException
	{	Date current = new Date();
		if(homework.getFrom_d().compareTo(homework.getTo_d())==0 || homework.getFrom_d().compareTo(homework.getTo_d())>0 || homework.getFrom_d().compareTo(current)<0)
		{
			this.insert_s = "Your Date Note correct, Check them";
			return null;
		}
	 homework.setEdit(false);
	 this.addfile(homework);
	 this.insert_s = Homework.addhomework.modifyhomework(homework);
	 showfile(homework);
	 return null;
	}
	
	public String showfile(HomeworkBL selected) throws IOException{
		
		File folder = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/homework/");
		File[] listOfFiles = folder.listFiles();
		selected.getFilename().clear();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  
		        if(listOfFiles[i].getName().indexOf(selected.getHomework_id())==0)
		        {
		        	selected.getFilename().add(listOfFiles[i].getName().replaceFirst(selected.getHomework_id()+"_", ""));
		        	
		        }
		      }      
		    }

		
		return null;
	}
	
	public String Download(String D_file,HomeworkBL homework,String place) throws IOException{
		
		// Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/"+place, homework.getHomework_id()+"_"+D_file);
        
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            
            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", new MimetypesFileTypeMap().getContentType(file));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" +D_file + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
           // close(output);
           // close(input);
        	input.close();
        	output.close();
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
        return null;
	}
public String Downloadsub(String D_file,Subhomewok sub,String place) throws IOException{
		
		// Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/"+place,sub.getStudent_id()+"_"+sub.getHomework_id()+"_"+D_file);
        
        
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            
            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", new MimetypesFileTypeMap().getContentType(file));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" +D_file + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
           // close(output);
           // close(input);
        	input.close();
        	output.close();
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
        return null;
	}
	
	public String deletefile(String D_file,HomeworkBL homework) throws IOException
	{	File file = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/homework/", homework.getHomework_id()+"_"+D_file);
	if(file.delete()){
		this.insert_s= "File is Deleted";
		homework.setNumber_f(homework.getNumber_f()-1);
	
	}
	showfile(homework);	
	return null;
	}
	public String addfile(HomeworkBL selected)
	{
		for(int i=0;i<this.fileL.size();i++){
								
					try {
				       	 copyFile(selected.getHomework_id()+"_"+this.fileLup.get(i).getFileName(),this.fileL.get(i),"homework/");
				       	 selected.setNumber_f(selected.getNumber_f()+1);
				      
					} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
				
					}
		return null;
	}
	public String edithomework(HomeworkBL selected)
	{
		for(HomeworkBL homework:this.homeworkL)
		{
			homework.setEdit(false);
		}
		this.fileL.removeAll(fileL);
		this.fileLup.removeAll(fileLup);
		selected.setEdit(true);
		
		return null;
	}
	public String listsubhomeworks(HomeworkBL selhomework){
		if(selhomework !=null)
		{
		this.course_student = courseLP.coursestudent(this.course);
		if(this.course_student!=null)
		this.listsubhomework = addhomework.listsubhomework(selhomework, this.course_student);
			for(Subhomewok submit:this.listsubhomework)
			{
				showsubfile(submit);
			}
			
		}
		
		for(HomeworkBL sel:this.homeworkL)
		{
			sel.setEdit(false);
		}
		selhomework.setEdit(true);
		
		return null;
	}
	public String submithomework(HomeworkBL homework)
	{
		SomeBean user = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		Subhomewok submit = new Subhomewok();
		submit.setHomework_id(homework.getHomework_id());
		submit.setStudent_id(user.getUseraut().getUserid());
		submit.setStatus("Submitted");
		if(this.number_f!=1){this.insert_s="You Must Upload file"; return null;}
		this.insert_s = addhomework.Studentsubmission(submit);
		if(this.insert_s.equals("Success"))
		{
		File folder = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/studentsubmission/");
		File[] listOfFiles = folder.listFiles();
		
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  
		        if(listOfFiles[i].getName().indexOf(user.getUseraut().getUserid()+"_"+homework.getHomework_id())==0)
		        {
		        	listOfFiles[i].delete();
		        	
		        }
		      }      
		    }
		
		for(int i=0;i<this.fileL.size();i++){			
						
						try {
					       	 copyFile(user.getUseraut().getUserid()+"_"+homework.getHomework_id()+"_"+this.fileLup.get(i).getFileName(),this.fileL.get(i),"studentsubmission/");
							} catch (Exception e) {
								e.printStackTrace();
								// TODO: handle exception
							}
					
		}
		}
		homework.setEdit(false);
		this.fileL.removeAll(fileL);
		this.fileLup.removeAll(fileLup);
		this.number_f=0;
		showsubfile(submit);
		return null;
		
	}
	public String editsubhomework(Subhomewok sel) {
		for(Subhomewok homework:this.listsubhomework)
		{
			homework.setEdit(false);
		}
		sel.setEdit(true);
		
		
			return null;
	}
	public String gradehomework(Subhomewok submit)
	{
		this.insert_s  = addhomework.gradehomework(submit);
		submit.setStatus("Graded");
		submit.setEdit(false);
		return null;
	}
	public String showsubfile(Subhomewok submit)
	{
		File folder = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/studentsubmission/");
		File[] listOfFiles = folder.listFiles();
		
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  
		        if(listOfFiles[i].getName().indexOf(submit.getStudent().getId()+"_"+submit.getHomework_id())==0)
		        {
		        	submit.getFilename().add(listOfFiles[i].getName().replaceFirst(submit.getStudent().getId()+"_"+submit.getHomework_id()+"_", ""));
		        	
		        }
		      }      
		    }


		
		return null;
	}
public String DownloadAll(HomeworkBL homework) throws IOException{
	File folder = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/studentsubmission/");
	File[] listOfFiles = folder.listFiles();
	
	String[] parts;
	String id;
	for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  parts = listOfFiles[i].getName().split("_");
	    	  if(parts[1].equals(homework.getHomework_id()))
	        {
	        	

	            File file = new File("/home/zoheirdoudou/workspace/jsf-blank/WebContent/studentsubmission/"+listOfFiles[i].getName());
	            FacesContext facesContext = FacesContext.getCurrentInstance();
	            ExternalContext externalContext = facesContext.getExternalContext();
	            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
	            
	            BufferedInputStream input = null;
	            BufferedOutputStream output = null;

	            try {
	                // Open file.
	                input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
	                id = parts[0]+"_"+parts[1]+"_";
	                // Init servlet response.
	                response.reset();
	                response.setHeader("Content-Type", new MimetypesFileTypeMap().getContentType(file));
	                response.setHeader("Content-Length", String.valueOf(file.length()));
	                response.setHeader("Content-Disposition", "inline; filename=\"" +listOfFiles[i].getName().substring(id.length())+ "\"");
	                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

	                // Write file contents to response.
	                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	                int length;
	                while ((length = input.read(buffer)) > 0) {
	                    output.write(buffer, 0, length);
	                }

	                // Finalize task.
	                output.flush();
	            } finally {
	                // Gently close streams.
	               // close(output);
	               // close(input);
	            	input.close();
	            	output.close();
	            }

	            // Inform JSF that it doesn't need to handle response.
	            // This is very important, otherwise you will get the following exception in the logs:
	            // java.lang.IllegalStateException: Cannot forward after response has been committed.
	            facesContext.responseComplete();
	            

	        	
	        }
	      }      
	    }


	    return null;
		// Prepare.
	}


}

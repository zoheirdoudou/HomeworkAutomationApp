package somepackage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseBL implements CourseLookup {
	private String name;
	private String code;
	private String description;
	private String semester;
	private String instructor_id;
	private String status;
	private String grader_id;
	private User instructor = new User();
	private User grader = new User();
	private boolean edit;
	Connection con1 = null;
	Dbcon lc = new Dbcon();
	private String course_id;
	public boolean isEdit() {
		return edit;
	}


	public void setEdit(boolean edit) {
		this.edit = edit;
	}


	public User getGrader() {
		return grader;
	}


	public void setGrader(User grader) {
		this.grader = grader;
	}


	public User getInstructor() {
		return instructor;
	}


	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}


	public String getGrader_id() {
		return grader_id;
	}


	public void setGrader_id(String grader_id) {
		this.grader_id = grader_id;
	}


	public String getCourse_id() {
		return course_id;
	}


	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getInstructor_id() {
		return instructor_id;
	}


	public void setInstructor_id(String instructor_id) {
		this.instructor_id = instructor_id;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public CourseBL() {
		// TODO Auto-generated constructor stub
		try {
			con1 = lc.getLocalConnection();
		} catch (Exception e) {
			System.out.println("Cont connect to DB "+e);
		}
	}
	
	
	@Override
	public String createcourse(String name, String code, String description, String semester, String status,String instructor_id) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String queryU;
		
		queryU = "insert into Course (name,semester,description,instructor_id,code,status) values (\""+name+"\",\""+semester+"\",\""+description+"\",\""+instructor_id+"\",\""+code+"\",\""+status+"\")";
		System.out.println("couser : "+queryU);
		try {
			stmt = this.con1.createStatement();
			stmt.executeUpdate(queryU);
			return "Seccess";
		} catch (Exception e) {
			System.out.println("Cant inter course: "+e);
			// TODO: handle exception
		}
		
		return "Fail";
	}


	@Override
	public List<CourseBL> listcourse(String user_id,String usertype) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String queryU = null;		
		switch (usertype) {
		case "Student": usertype="students";
			break;
		case "Instructor": usertype="instructors"; 
			break;
		case "Grader": usertype="graders";
			break;
			default:
		}
		//queryU = "select id,name,semester,description,instructor_id,code,status,grader_id from Course where instructor_id=\""+instructor_id+"\"";
		if(usertype.equals("instructors"))
		queryU = " select distinct I.name,I.surname,I.mail,I.gender, C.id,C.name,C.semester,C.description,C.instructor_id,C.code,C.status,C.grader_id, if(C.grader_id is NULL,C.grader_id,G.name),if(C.grader_id is NULL,C.grader_id,G.surname),if(C.grader_id is NULL,C.grader_id,G.mail),if(C.grader_id is NULL,C.grader_id,G.gender),if(C.grader_id is NULL,C.grader_id,G.univ_n)     from instructors I, Course C, graders G where I.id=C.instructor_id and C.instructor_id=\""+user_id+"\" and (C.grader_id=G.id or  C.grader_id is NULL) ";
		else 
			if(usertype.equals("graders"))
			queryU = " select distinct I.name,I.surname,I.mail,I.gender,C.id,C.name,C.semester,C.description,C.instructor_id,C.code,C.status,C.grader_id, if(C.grader_id is NULL,C.grader_id,G.name),if(C.grader_id is NULL,C.grader_id,G.surname),if(C.grader_id is NULL,C.grader_id,G.mail),if(C.grader_id is NULL,C.grader_id,G.gender),if(C.grader_id is NULL,C.grader_id,G.univ_n)     from instructors I, Course C, graders G where I.id=C.instructor_id and C.grader_id=\""+user_id+"\" and (C.grader_id=G.id or  C.grader_id is NULL) ";
			else{
				queryU = " select distinct I.name,I.surname,I.mail,I.gender,C.id,C.name,C.semester,C.description,C.instructor_id,C.code,C.status,C.grader_id, if(C.grader_id is NULL,C.grader_id,G.name),if(C.grader_id is NULL,C.grader_id,G.surname),if(C.grader_id is NULL,C.grader_id,G.mail),if(C.grader_id is NULL,C.grader_id,G.gender),if(C.grader_id is NULL,C.grader_id,G.univ_n)     from instructors I, Course_students S, Course C, graders G where I.id=C.instructor_id and C.id=S.course_id and S.student_id=\""+user_id+"\" and (C.grader_id=G.id or  C.grader_id is NULL)";}
		
		try {
			
			stmt = con1.createStatement();
			ResultSet rs1 =null;
			rs1 = stmt.executeQuery(queryU);
			List<CourseBL> courselist = new ArrayList<CourseBL>();
			while(rs1.next())
			{
			
				CourseBL coursefind = new CourseBL();
				coursefind.setName(rs1.getString("C.name"));
				coursefind.setCode(rs1.getString("C.code"));
				coursefind.setDescription(rs1.getString("C.description"));
				coursefind.setInstructor_id(rs1.getString("C.instructor_id"));
				coursefind.setSemester(rs1.getString("C.semester"));
				coursefind.setStatus(rs1.getString("C.status"));
				coursefind.setCourse_id(rs1.getString("C.id"));
				coursefind.setGrader_id(rs1.getString("C.grader_id"));
				User instructor = new User();
				instructor.setName(rs1.getString("I.name"));
				instructor.setSurname(rs1.getString("I.surname"));
				instructor.setMail(rs1.getString("I.mail"));
				instructor.setGender(rs1.getString("I.gender"));
				instructor.setId(rs1.getString("C.instructor_id"));
				coursefind.setInstructor(instructor);
				
				User findgrader = new User();
					if(coursefind.getGrader_id()!=null)
					{
					findgrader.setName(rs1.getString("if(C.grader_id is NULL,C.grader_id,G.name)"));
					findgrader.setSurname(rs1.getString("if(C.grader_id is NULL,C.grader_id,G.surname)"));
					findgrader.setGender(rs1.getString("if(C.grader_id is NULL,C.grader_id,G.gender)"));
					findgrader.setMail(rs1.getString("if(C.grader_id is NULL,C.grader_id,G.mail)"));
					findgrader.setUniv_n(rs1.getString("if(C.grader_id is NULL,C.grader_id,G.univ_n)"));
					
					
					}else
					{	findgrader.setName("Not Assigned");
						findgrader.setSurname("Not Assigned");
						findgrader.setGender("Not Assigned");
						findgrader.setMail("Not Assigned");
						findgrader.setUniv_n("Not Assigned");
					}
					coursefind.setGrader(findgrader);
					courselist.add(coursefind);
			}
			return courselist;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public String updatecourse(CourseBL up_course) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String queryU;
		
		queryU = "update Course set name=\""+up_course.getName()+"\",semester=\""+up_course.getSemester()+"\",description=\""+up_course.getDescription()+"\",status=\""+up_course.getStatus()+"\",code=\""+up_course.getCode()+"\" where id=\""+up_course.course_id+"\"";
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate(queryU);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant update course: "+e);
		}
		return "Fail";
	}


	@Override
	public String asigncourse(String grader_id,String course_id) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String queryU;
		
		queryU = "update Course set grader_id=\""+grader_id+"\" where id=\""+course_id+"\"";
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate(queryU);
			return "Success";
		} catch (Exception e) {
			System.out.println("Cant assign grader: "+e);
			// TODO: handle exception
		}
		return "Fail";
	}


	@Override
	public String uploadstudents(InputStream in, CourseBL selected) {
		// TODO Auto-generated method stub
			Statement stmt = null;
			String queryU;
			
			String line = "";
	    	String cvsSplitBy = ",";
	    	try {
	    	//	String strContent;
	    		BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
	            
	           
	    		while ((line = br.readLine()) != null) {
	    		
	    			stmt = con1.createStatement();
	    			// use comma as separator
	    		String[] field = line.split(cvsSplitBy);
	    		if(field[1].equals(selected.getCode())){
	    		//queryU = "insert into Course_students (course_id,student_id) values(\""+selected.getCourse_id()+"\",(select id from students where univ_n=\""+field[0]+"\")) ";
	      		queryU = "insert IGNORE into Course_students  SET course_id=\""+selected.getCourse_id()+"\",student_id=(select id from students where univ_n=\""+field[0]+"\")";
	    			
	    		stmt.executeUpdate(queryU);
	    	
	    		}
	    	}
	    		return "Success";
	    	} catch (Exception e) {
				// TODO: handle exception
	    		System.out.println("Cant upload students "+e);
			}

		
		
		return null;
	}


	@Override
	public List<User> coursestudent(CourseBL course) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String query = null;
		query  = "select s.id, s.status, s.univ_n,s.name,s.surname,s.mail  from Course_students cs , students s where cs.course_id ='"+course.course_id+"' and s.id=cs.student_id";
		List<User> userlist = new ArrayList<User>();
		try {
			
			stmt = con1.createStatement();
			ResultSet rs1 = null;
			rs1 = stmt.executeQuery(query);
			while(rs1.next())
			{   
				User newuser = new User();
				newuser.setUniv_n(rs1.getString("s.univ_n"));
				newuser.setName(rs1.getString("s.name"));
				newuser.setSurname(rs1.getString("s.surname"));
				newuser.setMail(rs1.getString("s.mail"));
				newuser.setUsertype("Student");
				newuser.setState(rs1.getString("s.status"));
				newuser.setId(rs1.getString("s.id"));
				userlist.add(newuser);

			}
			return userlist;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant select student "+e);
		}
		
		return null;
	}

}

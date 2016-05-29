package somepackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

public class HomeworkBL implements HomeworkLookup{
	Connection con1 = null;
	Dbcon lc = new Dbcon();
	
	private String homework_id;
	private String name;
	private String description;
	private java.util.Date from_d;
	private java.util.Date to_d;
	private CourseBL course;
	private String status;
	private String creator;
	private String creator_id;
	private boolean edit;
	private List<String> filename = new ArrayList<String>();
	private int number_f=0;
	
	public List<String> getFilename(){
		return filename;
		
	}
	public void setFilename(List<String> filename)
	{
		this.filename = filename;
	}
	public HomeworkBL() {
		try {
			con1 = lc.getLocalConnection();
		} catch (Exception e) {
			System.out.println("Cont connect to DB "+e);
			// TODO: handle exception
		}
		
	}
	
	@Override
	public String createhomework(Homework newhomework) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String queryU = null;
		
		SomeBean user = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start_date = sdf.format(newhomework.getFrom_d());
		String to_date = sdf.format(newhomework.getTo_d());
			
		queryU = "insert into homework (name,description,from_d,to_d,state,course_id,creator,creator_id,number_f) values (\""+newhomework.getName()+"\",\""+newhomework.getDescription()+"\",\""+start_date+"\",\""+to_date+"\",\""+newhomework.getStatus()+"\",\""+newhomework.getCourse().getCourse_id()+"\",\""+user.getSomeProperty()+"\",\""+user.getUseraut().getUserid()+"\",\""+newhomework.getNumber_f()+"\")" ;
		
		try {
			stmt = this.con1.createStatement();
			stmt.executeUpdate(queryU);
			queryU = "SELECT id FROM homework ORDER BY id DESC LIMIT 1";
			ResultSet rs1 =null;
			rs1 = stmt.executeQuery(queryU);
			while(rs1.next())
				
			return rs1.getString("id");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant create new homework "+e);
		
		}
		
		return "Fail";
	}

	@Override
	public String modifyhomework(HomeworkBL modified) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start_date = sdf.format(modified.getFrom_d());
		String to_date = sdf.format(modified.getTo_d());
		String query = "update homework set number_f=\""+modified.getNumber_f()+"\", name=\""+modified.getName()+"\",description=\""+modified.getDescription()+"\",state=\""+modified.getStatus()+"\",from_d=\""+start_date+"\",to_d=\""+to_date+"\" where id=\""+modified.getHomework_id()+"\"";
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate(query);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cant update homework "+e);
		}
		return "Fail";
	}

	@Override
	public List<HomeworkBL> listhomework(CourseBL course) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String query = null;
		SomeBean user = (SomeBean) FacesContext.getCurrentInstance().
				getExternalContext().getSessionMap().get("someBean");
		if(user.getSomeProperty().equals("Instructor")||user.getSomeProperty().equals("Grader"))
		{
		 query =" select h.id ,h.name, h.description, h.from_d,h.to_d,state , h.course_id,h.creator,h.creator_id,h.number_f from homework h where h.creator = \""+user.getSomeProperty()+"\" and h.creator_id = \""+user.getUseraut().getUserid()+"\" and h.course_id=\""+course.getCourse_id()+"\"";
		}else 
			query = "select h.id ,h.name, h.description, h.from_d,h.to_d,state , h.course_id,h.creator,h.creator_id,h.number_f from homework h , Course_students s where s.course_id = \""+course.getCourse_id()+"\" and s.student_id = \""+user.getUseraut().getUserid()+"\" ";
		
		try {
			
			stmt = con1.createStatement();
			ResultSet rs1= null;
			rs1 = stmt.executeQuery(query);
			List<HomeworkBL > homeworkL = new ArrayList<HomeworkBL>();
			while(rs1.next())
			{  
				HomeworkBL selhomework = new HomeworkBL();
				selhomework.setName(rs1.getString("h.name"));
				selhomework.setHomework_id(rs1.getString("h.id"));
				selhomework.setDescription(rs1.getString("h.description"));
				selhomework.setFrom_d(rs1.getDate("h.from_d"));
				selhomework.setTo_d(rs1.getDate("h.to_d"));
				selhomework.setCourse(course);
				selhomework.setCreator(rs1.getString("h.creator"));
				selhomework.setCreator_id(rs1.getString("h.creator_id"));
				selhomework.setStatus(rs1.getString("h.state"));
				selhomework.setNumber_f(rs1.getInt("h.number_f"));
				if(user.getSomeProperty().equals("Student") && rs1.getString("h.state").equals("Disable"))
				continue;
					homeworkL.add(selhomework);
				
			}
			return homeworkL;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cant select homework"+e);
		}
				
		return null;
	}

	public String getHomework_id() {
		return homework_id;
	}

	public void setHomework_id(String homework_id) {
		this.homework_id = homework_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.Date getFrom_d() {
		return from_d;
	}

	public void setFrom_d(java.util.Date from_d) {
		this.from_d = from_d;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getTo_d() {
		return to_d;
	}

	public void setTo_d(java.util.Date to_d) {
		this.to_d = to_d;
	}

	public CourseBL getCourse() {
		return course;
	}

	public void setCourse(CourseBL course) {
		this.course = course;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public int getNumber_f() {
		return number_f;
	}
	public void setNumber_f(int number_f) {
		this.number_f = number_f;
	}
	@Override
	public List<Subhomewok> listsubhomework(HomeworkBL homework, List<User> students) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String query = null;
			
			List<Subhomewok> submit = new ArrayList<Subhomewok>();
			for(User sel:students)
			{	
				Subhomewok student_sub = new Subhomewok();
				query = "select hs.status ,hs.remarq,hs.grade  from   homework_students hs where hs.student_id='"+sel.getId()+"' and hs.homework_id='"+homework.getHomework_id()+"' ";
				try {
					
					stmt = con1.createStatement();
					ResultSet rs1 = null;
					rs1 = stmt.executeQuery(query);
					int i = 0;
					while(rs1.next())
					{ 
						Integer grade = new Integer(rs1.getString("hs.grade"));
						student_sub.setGrade(grade);
						student_sub.setRemaraq(rs1.getString("hs.remarq"));
						student_sub.setStatus(rs1.getString("hs.status"));
						i++;
					}
					if(i==0)
					{
						student_sub.setRemaraq("Not submited");
						student_sub.setStatus("Not submited");
					}
					
					student_sub.setStudent(sel);
					student_sub.setStudent_id(sel.getId());
					student_sub.setHomework_id(homework.getHomework_id());
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("cant select student homework "+e);
				}
				submit.add(student_sub);
			
			return submit;
		}
		
		return null;
		
	
	}
	@Override
	public String Studentsubmission(Subhomewok submit) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String query = null;
		query = "insert IGNORE into homework_students SET homework_id="+submit.getHomework_id()+",student_id="+submit.getStudent_id()+",status=\""+submit.getStatus()+"\",remarq='Not Graded'";
		try {
			
			stmt = con1.createStatement();
			stmt.executeUpdate(query);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cant insert student homework submission "+e);
		}
		
		return "Fail";
	}
	@Override
	public String gradehomework(Subhomewok submit) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		String query = null;
		if(submit.getStatus().equals("Not submited")){
			
			query = "insert into homework_students (homework_id,student_id,status,remarq,grade) values('"+submit.getHomework_id()+"','"+submit.getStudent_id()+"','Graded','"+submit.getRemaraq()+"','"+submit.getGrade()+"')";
			
		}
		else{
			
		query ="update homework_students set status='Graded',remarq='"+submit.getRemaraq()+"',grade='"+submit.getGrade()+"' where homework_id='"+submit.getHomework_id()+"' and student_id='"+submit.getStudent().getId()+"'";
		}
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate(query);
			return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cant update grade homework "+e );
		}
		return "Fail";
	}

}

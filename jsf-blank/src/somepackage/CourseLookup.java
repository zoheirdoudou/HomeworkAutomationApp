package somepackage;

import java.io.InputStream;
import java.util.List;

public interface CourseLookup {
	public String createcourse(String name, String code, String description, String semester,String status,String instructor_id);
	public List<CourseBL> listcourse(String user_id,String usertype);
	public String updatecourse(CourseBL up_course);
	public String asigncourse(String grader_id,String course_id);
	public String uploadstudents(InputStream in, CourseBL selected);
	public List<User> coursestudent(CourseBL course);
}

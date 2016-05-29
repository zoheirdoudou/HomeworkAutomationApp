package somepackage;

import java.sql.SQLException;
import java.util.List;

public interface HomeworkLookup {
	public String createhomework(Homework newhomework) throws SQLException;
	public String modifyhomework(HomeworkBL modified);
	public List<HomeworkBL> listhomework(CourseBL course);
	public List<Subhomewok> listsubhomework(HomeworkBL homework,List<User> students);
	public String Studentsubmission(Subhomewok submit);
	public String gradehomework(Subhomewok submit);
}

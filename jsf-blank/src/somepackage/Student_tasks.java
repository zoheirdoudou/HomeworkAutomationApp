package somepackage;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Student_tasks {
	private String[] taskL={"List Course","List Homework"};
	private String task;
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String[] getTaskL() {
		return taskL;
	}

	public void setTaskL(String[] taskL) {
		this.taskL = taskL;
	}
	public String student_task(){
		switch (this.task) {
		case "List Course": return "studentcourse";
		case "List Homework":	return "studenthomework";
			

		default:
			break;
		}
		return null;
	}
	
}

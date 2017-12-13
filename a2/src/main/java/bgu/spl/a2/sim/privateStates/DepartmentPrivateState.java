package bgu.spl.a2.sim.privateStates;

import java.util.List;
import java.util.Vector;

import bgu.spl.a2.PrivateState;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState{
	private List<String> courseList;
	private List<String> studentList;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {
		courseList = new Vector<String>();
		studentList = new Vector<String>();
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void addCourse(String courseName){this.courseList.add(courseName);}

	public void addStudent(String student){this.studentList.add(student);}

	public void deleteCourse(String courseName){
		int courseIndex = this.courseList.indexOf(courseName);
		if (courseIndex!=-1)
			this.courseList.remove(courseIndex);
	}

	public void deleteStudent(String studentName){
		int studentIndex = this.studentList.indexOf(studentName);
		if (studentIndex!=-1)
			this.studentList.remove(studentIndex);
	}

}

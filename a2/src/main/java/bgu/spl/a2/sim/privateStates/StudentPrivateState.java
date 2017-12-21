package bgu.spl.a2.sim.privateStates;

import java.util.HashMap;

import bgu.spl.a2.PrivateState;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState{

	private HashMap<String, Integer> grades;
	private long signature = 0;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
	this.grades = new HashMap<>();
	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}

	public void setSignature(long signature){this.signature=signature;}

	public void addGrade(String courseName, Integer grade){
		this.grades.put(courseName,grade);
	}

	public void removeCourse(String courseName){
		if(this.grades.containsKey(courseName))
			this.grades.remove(courseName);
	}
}

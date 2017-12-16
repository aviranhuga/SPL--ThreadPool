package bgu.spl.a2.sim;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Computer {

	String computerType;
	long failSig;
	long successSig;
	private SuspendingMutex Mutex;
	
	public Computer(String computerType) {
		this.computerType = computerType;
		this.Mutex = new SuspendingMutex(this);
	}
	
	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 */
	public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades){
		Iterator<String> courseIt = courses.iterator();
		while (courseIt.hasNext()) {
			String nextCourse = courseIt.next();
			if(!(coursesGrades.containsKey(nextCourse)))return failSig;
			else if(coursesGrades.get(nextCourse)<=56) return failSig;
		}//end of while
		return successSig;
	}

	public void setSig(long failSig,long successSig){
		this.failSig=failSig;
		this.successSig=successSig;
	}

	public SuspendingMutex getSuspendingMutex(){
		return this.Mutex;
	}

	public String getComputerType(){return computerType;}
}

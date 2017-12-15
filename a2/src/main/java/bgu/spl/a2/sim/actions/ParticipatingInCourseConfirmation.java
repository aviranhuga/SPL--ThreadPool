package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class ParticipatingInCourseConfirmation extends Action<Boolean>{

    private String StudentActorId;
    private String CourseActorId;

    @Override
    protected void start() {
        CoursePrivateState Course = (CoursePrivateState)this.pool.getPrivateState(this.CourseActorId);
        StudentPrivateState Student = (StudentPrivateState)this.pool.getPrivateState(this.StudentActorId);
        HashMap<String,Integer> StudentGrades = Student.getGrades();
        Iterator<String> prequisites = ((Vector)Course.getPrequisites()).iterator();
        Boolean preCond = true;
        if(Course.getAvailableSpots().intValue()==-1) {
            preCond = false;
            System.out.println(CourseActorId + " is Closed!");
        }else if(Course.getAvailableSpots().intValue() <= Course.getRegistered().intValue() ){
            System.out.println("Course: " + CourseActorId + "is Full!");
            preCond=false;
        }

        while (prequisites.hasNext() && preCond){
            String currentPre = prequisites.next();
            if(!StudentGrades.containsKey(currentPre)) {
                preCond = false;
                System.out.println( StudentActorId + " didnt fill the prequisites for " + CourseActorId + " he need to take the course " + currentPre);
            }
        }

        this.complete(preCond);
    }

     ParticipatingInCourseConfirmation(String StudentId , String CourseId){
        this.StudentActorId = StudentId;
        this.CourseActorId = CourseId;
        this.setActionName("Participating In Course Confirmation");
        this.Result = new Promise<Boolean>();
    }


}

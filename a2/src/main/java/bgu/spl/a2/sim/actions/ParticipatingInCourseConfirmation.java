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
    private HashMap<String,Integer> StudentGrades;

    @Override
    protected void start() {
        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        Iterator<String> prequisites = ((Vector)Course.getPrequisites()).iterator();

        Boolean preCond = true;
        if(Course.getAvailableSpots().intValue()==-1) {
            preCond = false;
            System.out.println(this.actorId + " is Closed!");
        }else if(Course.getAvailableSpots().intValue() <= Course.getRegistered().intValue() ){
            System.out.println("Course: " + this.actorId + " is Full!");
            preCond=false;
        }
        while (prequisites.hasNext() && preCond){
            String currentPre = prequisites.next();
            if(!StudentGrades.containsKey(currentPre)) {
                preCond = false;
                System.out.println( StudentActorId + " didnt fill the prequisites for " + this.actorId + " he need to take the course " + currentPre);
            }
        }
        if (preCond==true) Course.addStudents(this.StudentActorId);

        this.complete(preCond);
    }

     ParticipatingInCourseConfirmation(String StudentId, HashMap<String,Integer> StudentGrades){
        this.StudentActorId = StudentId;
        this.StudentGrades = StudentGrades;
        this.setActionName("Participating In Course Confirmation");
        this.Result = new Promise<Boolean>();
    }


}

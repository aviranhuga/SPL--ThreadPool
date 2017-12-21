package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ParticipatingInCourseConfirmation extends Action<Boolean> {
    //we are in the Student Actor
    private String courseActorId;
    private LinkedList<String> prequisites;
    private int Grade;

    @Override
    protected void start() {
        HashMap<String, Integer> StudentGrades = ((StudentPrivateState) this.actorState).getGrades();
        Iterator<String> prequisites = this.prequisites.iterator();
        while (prequisites.hasNext()) {
            if (!StudentGrades.containsKey(prequisites.next())) {
                this.complete(false);
                return;
            }//end of if
        }//end of while
            //((StudentPrivateState) this.actorState).addGrade(courseActorId,new Integer(Grade));
            this.complete(true);
        }

        ParticipatingInCourseConfirmation(String courseActorId, LinkedList < String > prequisites, int Grade){
            this.courseActorId = courseActorId;
            this.prequisites = prequisites;
            this.Grade = Grade;
            this.setActionName("Participating In Course Confirmation");
            this.Result = new Promise<>();
        }


    }

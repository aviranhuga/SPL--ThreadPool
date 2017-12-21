package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ParticipatingInCourse extends Action<Boolean> {
    //we are in the courseActorId
    private String StudentActorId;
    private int Grade;

    @Override
    protected void start() {
        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        //check if the course is closed
        if (Course.getAvailableSpots().intValue() == -1) {
            this.complete(false);
            this.actorState.addRecord(getActionName());
            return;
        }
        //check if the course has space
        if (Course.getAvailableSpots().intValue() <= Course.getRegistered().intValue()) {
            this.complete(false);
            this.actorState.addRecord(getActionName());
            return;
        }
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new ParticipatingInCourseConfirmation(this.actorId,(LinkedList<String>)Course.getPrequisites(),Grade);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, StudentActorId , new StudentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                Course.addStudents(this.StudentActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                //System.out.println("Student: " + this.actorId + " Participating In Course: " + CourseActorId + " Grade: " + Grade);
            }else {
                //System.out.println("Student: " + this.actorId + " Participating In Course: " + CourseActorId + " Failed!");
                this.complete(false);
                this.actorState.addRecord(getActionName());
            }
        });
    }

    public ParticipatingInCourse(String StudentActorId, int Grade){
        this.StudentActorId=StudentActorId;
        this.Grade=Grade;
        this.setActionName("Participate In Course");
        this.Result = new Promise<>();
    }


}

package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseFinalConfirmation extends Action<Boolean> {
    //we are in the Student Actor
    private String courseActorId;
    private int Grade;

    @Override
    protected void start() {
        ((StudentPrivateState)this.actorState).addGrade(courseActorId,new Integer(Grade));
        this.complete(true);
    }

    ParticipatingInCourseFinalConfirmation(String courseActorId, int Grade){
        this.courseActorId = courseActorId;
        this.Grade = Grade;
        this.setActionName("Participating In Course Final Confirmation");
        this.Result = new Promise<>();
    }


}

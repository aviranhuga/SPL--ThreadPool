package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class UnregisterConfirmation extends Action<Boolean>{
    //we are in the student actor
    private String courseActorId;

    @Override
    protected void start() {

        StudentPrivateState student = (StudentPrivateState)this.actorState;
        if(student.getGrades().containsKey(courseActorId)){
            student.removeCourse(this.courseActorId);
            this.complete(true);
        }
        else this.complete(false);
    }

    public UnregisterConfirmation(String courseActorId){
        this.courseActorId = courseActorId;
        this.setActionName("Unregister Confirmation");
        this.Result = new Promise<>();
    }


}
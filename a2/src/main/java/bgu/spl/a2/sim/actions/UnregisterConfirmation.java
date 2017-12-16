package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class UnregisterConfirmation extends Action<Boolean>{
    //The ActorId is the Course we want to delete the Student From
    private String StudentActorId;

    @Override
    protected void start() {

        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        if(Course.getRegStudents().contains(StudentActorId)) {
            this.complete(true);
            Course.removeStudents(StudentActorId);
        }
        else this.complete(false);
    }

    public UnregisterConfirmation(String StudentId){
        this.StudentActorId = StudentId;
        this.setActionName("Unregister Confirmation");
        this.Result = new Promise<Boolean>();
    }


}
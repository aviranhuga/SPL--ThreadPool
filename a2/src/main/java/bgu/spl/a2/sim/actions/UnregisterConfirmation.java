package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class UnregisterConfirmation extends Action<Boolean>{

    private String StudentActorId;
    private String CourseActorId;

    @Override
    protected void start() {

        CoursePrivateState Course = (CoursePrivateState)this.pool.getPrivateState(this.CourseActorId);
        if(Course.getRegStudents().contains(StudentActorId))
            this.complete(true);
        else this.complete(false);
    }

    public UnregisterConfirmation(String StudentId , String CourseId){
        this.StudentActorId = StudentId;
        this.CourseActorId = CourseId;
        this.setActionName("Unregister Confirmation");
        this.Result = new Promise<Boolean>();
    }


}
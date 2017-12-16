package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddStudentConfirmation extends Action<Boolean>{

    private String StudentActorId;

    @Override
    protected void start() {
        if (((DepartmentPrivateState)this.actorState).getStudentList().contains(this.StudentActorId)) {
            this.complete(false);
        }
        else {
            ((DepartmentPrivateState)this.actorState).addStudent(this.StudentActorId);
            this.complete(true);
        }

    }

    AddStudentConfirmation(String StudentId){
        this.StudentActorId=StudentId;
        this.setActionName("Add a Student Confirmation");
        this.Result = new Promise<Boolean>();
    }


}

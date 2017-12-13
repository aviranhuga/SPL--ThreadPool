package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddStudentConfirmation extends Action<Boolean>{

    private String StudentActorId;
    private String DepartmentActorId;

    @Override
    protected void start() {
        DepartmentPrivateState Department = (DepartmentPrivateState)this.pool.getPrivateState(DepartmentActorId);
        if (Department.getCourseList().contains(this.StudentActorId)) {
            this.complete(false);
        }
        else {
            this.complete(true);
        }

    }

    AddStudentConfirmation(String StudentId,String DepartmentId){
        this.StudentActorId=StudentId;
        this.DepartmentActorId=DepartmentId;
        this.setActionName("Add A Student Conifmation");
        this.Result = new Promise<Boolean>();
    }


}

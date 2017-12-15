package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class CloseACourseConfirmation extends bgu.spl.a2.Action<Boolean>{

    private String courseName;
    private String DepartmentActorId;

    @Override
    protected void start() {
        DepartmentPrivateState Department = (DepartmentPrivateState)this.pool.getPrivateState(DepartmentActorId);
        if (Department.getCourseList().contains(this.courseName))
            this.complete(true);
        else
            this.complete(false);
    }

    public CloseACourseConfirmation(String DepartmentActorId , String courseName){
        this.courseName = courseName;
        this.DepartmentActorId = DepartmentActorId;
        this.setActionName("Close A Course Confirmation");
        this.Result = new Promise<Boolean>();
    }

}

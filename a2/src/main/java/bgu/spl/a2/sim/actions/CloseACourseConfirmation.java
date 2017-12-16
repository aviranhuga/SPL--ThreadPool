package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class CloseACourseConfirmation extends bgu.spl.a2.Action<Boolean>{

    private String courseName;

    @Override
    protected void start() {
        DepartmentPrivateState Department = (DepartmentPrivateState)this.actorState;
        if (Department.getCourseList().contains(this.courseName)) {
            Department.deleteCourse(this.courseName);
            this.complete(true);
        }
        else
            this.complete(false);
    }

    public CloseACourseConfirmation(String courseName){
        this.courseName = courseName;
        this.setActionName("Close A Course Confirmation");
        this.Result = new Promise<Boolean>();
    }

}

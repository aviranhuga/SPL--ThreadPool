package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpenANewCourseConfirmation extends bgu.spl.a2.Action<Boolean>{

    private String courseName;
    @Override
    protected void start() {
        DepartmentPrivateState Department = (DepartmentPrivateState)this.actorState;
        if (Department.getCourseList().contains(this.courseName))
            this.complete(false);
        else {
            Department.addCourse(this.courseName);
            this.complete(true);
        }
    }

    public OpenANewCourseConfirmation(String courseName){
        this.courseName = courseName;
        this.setActionName("Open Course Confirmation");
        this.Result = new Promise<Boolean>();
    }

}

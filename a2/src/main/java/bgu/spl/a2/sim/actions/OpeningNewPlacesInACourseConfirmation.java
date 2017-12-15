package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpeningNewPlacesInACourseConfirmation extends bgu.spl.a2.Action<Boolean>{

    private String courseName;

    @Override
    protected void start() {
        if ((((CoursePrivateState)this.pool.getPrivateState(courseName)).getAvailableSpots())==-1)
            this.complete(false);
        else
            this.complete(true);
    }

    public OpeningNewPlacesInACourseConfirmation(String courseName){
        this.courseName = courseName;
        this.setActionName("Open A New Course Confirmation");
        this.Result = new Promise<Boolean>();
    }
}

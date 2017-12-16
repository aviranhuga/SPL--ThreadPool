package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlacesInACourseConfirmation extends bgu.spl.a2.Action<Boolean>{

    private String courseName;
    private int ExtraSpots;

    @Override
    protected void start() {
        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        if ((Course.getAvailableSpots())==-1)
            this.complete(false);
        else {
            Course.setAvailableSpots(Course.getAvailableSpots() + this.ExtraSpots);
            this.complete(true);
        }
    }

    public OpeningNewPlacesInACourseConfirmation(String courseName , int ExtraSpots){
        this.ExtraSpots = ExtraSpots;
        this.courseName = courseName;
        this.setActionName("Open A New Course Confirmation");
        this.Result = new Promise<Boolean>();
    }
}

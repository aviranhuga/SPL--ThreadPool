package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;

public class OpenANewCourseConfirmation extends bgu.spl.a2.Action<Boolean>{
    //we are in the course actor
    private Integer availableSpots;
    private LinkedList<String> prequisites;

    @Override
    protected void start() {
        ((CoursePrivateState)this.actorState).setPrequisites(this.prequisites);
        ((CoursePrivateState)this.actorState).setAvailableSpots(this.availableSpots);
        this.complete(true);
    }

    public OpenANewCourseConfirmation(Integer availableSpots,LinkedList<String> prequisites){
        this.availableSpots = availableSpots;
        this.prequisites=prequisites;
        this.setActionName("Open Course Confirmation");
        this.Result = new Promise<>();
    }

}

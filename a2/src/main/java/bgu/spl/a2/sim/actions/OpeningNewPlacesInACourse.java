package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlacesInACourse extends bgu.spl.a2.Action<Boolean>{
    //we are in the course Actor
    private int extraAvailableSpots;

    @Override
    protected void start(){
        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        //check if the course is closed
        if ((Course.getAvailableSpots())==-1) {
            this.complete(false);
            this.actorState.addRecord(getActionName());
        }else { //add Spaces
            Course.setAvailableSpots(Course.getAvailableSpots() + this.extraAvailableSpots);
            this.complete(true);
            this.actorState.addRecord(this.ActionName);
        }
    }

    public OpeningNewPlacesInACourse(int extraAvailableSpots){
        this.ActionName = "Add Spaces";
        this.Result = new Promise<>();
        this.extraAvailableSpots = extraAvailableSpots;
    }
}

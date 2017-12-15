package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class OpeningNewPlacesInACourse extends bgu.spl.a2.Action<Boolean>{

    private int availableSpots;
    private String courseName;

    @Override
    protected void start(){
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new OpeningNewPlacesInACourseConfirmation(this.courseName);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, courseName , new CoursePrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                CoursePrivateState course = ((CoursePrivateState)this.pool.getPrivateState(courseName));
                course.setAvailableSpots(course.getAvailableSpots()+this.availableSpots);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                System.out.println("Added" + availableSpots + "Places in Course: " + courseName);
            }else {
                System.out.println("Open new places in " + courseName + "Failed!");
                this.complete(false);
            }
        });

    }

    public OpeningNewPlacesInACourse(int availableSpots,String courseName){
        this.ActionName = "Add Spaces";
        this.Result = new Promise<Boolean>();
        this.availableSpots = availableSpots;
        this.courseName = courseName;
    }
}

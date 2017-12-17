package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OpenANewCourse extends bgu.spl.a2.Action<Boolean>{

    private Integer availableSpots;
    private LinkedList<String> prequisites;
    private String DepartmentActorId;

    @Override
    protected void start(){
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new OpenANewCourseConfirmation(this.actorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, DepartmentActorId , new DepartmentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((CoursePrivateState)this.actorState).setPrequisites(this.prequisites);
                ((CoursePrivateState)this.actorState).setAvailableSpots(this.availableSpots);
                this.complete(true);
                this.actorState.addRecord(getActionName());
              //  System.out.println("New Course: " + this.actorId + " add to Department: " + DepartmentActorId);
            }else {
              //  System.out.println("New Course: " + this.actorId + " add to Department: " + DepartmentActorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public OpenANewCourse(Integer availableSpots,LinkedList<String> prequisites, String DepartmentActorId){
        this.ActionName = "Open A New Course";
        this.Result = new Promise<Boolean>();
        this.availableSpots = availableSpots;
        this.prequisites = prequisites;
        this.DepartmentActorId = DepartmentActorId;
    }
}


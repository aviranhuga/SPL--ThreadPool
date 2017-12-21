package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OpenANewCourse extends bgu.spl.a2.Action<Boolean>{
    //we are in the Department actor
    private Integer availableSpots;
    private LinkedList<String> prequisites;
    private String courseActorId;

    @Override
    protected void start(){
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new OpenANewCourseConfirmation(this.availableSpots,this.prequisites);
        actions.add(Confirmation);
        //Check if the course already exist
        if (((DepartmentPrivateState)this.actorState).getCourseList().contains(this.courseActorId)) {
            this.complete(false);
            return;
        }
        //open a new course
        this.sendMessage(Confirmation, courseActorId , new CoursePrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((DepartmentPrivateState)this.actorState).addCourse(courseActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
              //  System.out.println("New Course: " + this.actorId + " add to Department: " + DepartmentActorId);
            }else {
              //  System.out.println("New Course: " + this.actorId + " add to Department: " + DepartmentActorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public OpenANewCourse(Integer availableSpots,LinkedList<String> prequisites, String courseActorId){
        this.ActionName = "Open A New Course";
        this.Result = new Promise<>();
        this.availableSpots = availableSpots;
        this.prequisites = prequisites;
        this.courseActorId = courseActorId;
    }
}


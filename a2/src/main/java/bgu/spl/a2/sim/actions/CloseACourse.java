package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class CloseACourse extends bgu.spl.a2.Action<Boolean>{
    //we in the Department Actor
    private String courseActorId;

    @Override
    protected void start(){
        //Delete Course From the Department
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new CloseACourseConfirmation();
        actions.add(Confirmation);

        //no such a course in the Department
        if (!(((DepartmentPrivateState)this.actorState).getCourseList().contains(this.courseActorId))) {
            this.complete(false);
            return;
        }
        //Close course
        this.sendMessage(Confirmation, courseActorId , new CoursePrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((DepartmentPrivateState)this.actorState).deleteCourse(this.courseActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
     //           System.out.println("Course: " + this.actorId + " Closed in the Department: " + DepartmentActorId);
            }else {
     //           System.out.println("Closing The Course: " + this.actorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public CloseACourse(String courseActorId){
        this.ActionName = "Close A Course";
        this.Result = new Promise<>();
        this.courseActorId = courseActorId;
    }
}


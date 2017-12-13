package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class OpenANewCourse extends bgu.spl.a2.Action<Boolean>{

    private Integer availableSpots;
    private Vector<String> prequisites;
    private String DepartmentActorId;
    private String courseName;

    @Override
    protected void start(){
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new OpenANewCourseConfirmation(this.DepartmentActorId,this.courseName);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, DepartmentActorId , new DepartmentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((DepartmentPrivateState) this.pool.getPrivateState(DepartmentActorId)).addCourse(this.courseName);
                ((CoursePrivateState)this.pool.getPrivateState(courseName)).setPrequisites(this.prequisites);
                ((CoursePrivateState)this.pool.getPrivateState(courseName)).setAvailableSpots(this.availableSpots);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                System.out.println("New Course: " + courseName + " add to Department: " + DepartmentActorId);
            }else {
                System.out.println("New Course: " + courseName + " add to Department: " + DepartmentActorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public OpenANewCourse(String courseName,Integer availableSpots,Vector<String> prequisites, String DepartmentActorId){
        this.ActionName = "Open A New Course";
        this.Result = new Promise<Boolean>();
        this.availableSpots = availableSpots;
        this.prequisites = prequisites;
        this.DepartmentActorId = DepartmentActorId;
        this.courseName = courseName;
    }
}


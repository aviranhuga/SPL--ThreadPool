package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CloseACourse extends bgu.spl.a2.Action<Boolean>{

    private String DepartmentActorId;

    @Override
    protected void start(){
        //Delete Course From the Department
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new CloseACourseConfirmation(this.actorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, DepartmentActorId , new DepartmentPrivateState());
        //Unregister all the Students
        Iterator<String> StudentsList = ((CoursePrivateState)this.actorState).getRegStudents().iterator();
        Action<Boolean> removeStudent;
        while(StudentsList.hasNext()){
            removeStudent = new Unregister(this.actorId);
            actions.add(removeStudent);
            this.sendMessage(removeStudent,StudentsList.next(),new StudentPrivateState());
        }

        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((CoursePrivateState)this.actorState).setAvailableSpots(-1);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                System.out.println("Course: " + this.actorId + " Closed in the Department: " + DepartmentActorId);
            }else {
                System.out.println("Closing The Course: " + this.actorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public CloseACourse(String DepartmentActorId){
        this.ActionName = "Close A Course";
        this.Result = new Promise<Boolean>();
        this.DepartmentActorId = DepartmentActorId;
    }
}


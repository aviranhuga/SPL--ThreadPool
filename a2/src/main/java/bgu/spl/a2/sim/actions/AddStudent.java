package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends bgu.spl.a2.Action<Boolean>{
    //we in the Department Actor
    private String StudentActorId;

    @Override
    protected void start() {
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new AddStudentConfirmation();
        actions.add(Confirmation);

        //if the student already exist
        if(((DepartmentPrivateState)this.actorState).getStudentList().contains(StudentActorId)) {
            this.complete(false);
            this.actorState.addRecord(getActionName());
            return;
        }
        //Create new student
        this.sendMessage(Confirmation, StudentActorId , new StudentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((DepartmentPrivateState)this.actorState).addStudent(this.StudentActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                //System.out.println("Student: " + this.actorId + " added to Department: " + DepartmentActorId);
            }else {
               // System.out.println("Student: " + this.actorId + " added to Department: " + DepartmentActorId + " Failed!");
                this.complete(false);
                this.actorState.addRecord(getActionName());
            }
        });

    }

    public AddStudent(String StudentActorId){
        this.StudentActorId = StudentActorId;
        this.setActionName("Add Student");
        this.Result = new Promise<>();
    }

}

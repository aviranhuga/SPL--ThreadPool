package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends bgu.spl.a2.Action<Boolean>{

    private String DepartmentActorId;

    @Override
    protected void start() {
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new AddStudentConfirmation(this.actorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, DepartmentActorId , new DepartmentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                this.complete(true);
                this.actorState.addRecord(getActionName());
                //System.out.println("Student: " + this.actorId + " added to Department: " + DepartmentActorId);
            }else {
               // System.out.println("Student: " + this.actorId + " added to Department: " + DepartmentActorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public AddStudent(String DepartmentId){
        this.DepartmentActorId = DepartmentId;
        this.setActionName("Add Student");
        this.Result = new Promise<Boolean>();
    }

}

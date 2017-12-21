package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;

public class AddStudentConfirmation extends Action<Boolean>{
    //we in the Student Actor
    private String DepartmentActorId;

    @Override
    protected void start() {
            this.complete(true);
    }

    AddStudentConfirmation(String DepartmentActorId){
        this.DepartmentActorId=DepartmentActorId;
        this.setActionName("Add a Student Confirmation");
        this.Result = new Promise<Boolean>();
    }


}


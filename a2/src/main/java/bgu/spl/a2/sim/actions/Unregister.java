package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class Unregister extends Action<Boolean> {
    //we are in the course Actor
    private String studentActorId;

    @Override
    protected void start() {
        //check if the student is register to the course
        CoursePrivateState Course = (CoursePrivateState)this.actorState;
        if(!(Course.getRegStudents().contains(studentActorId))){
            this.actorState.addRecord(getActionName());
            this.complete(false);
            return;
        }

        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new UnregisterConfirmation(this.actorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, studentActorId , new StudentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                Course.removeStudents(studentActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
           //     System.out.println("Student" + this.actorId + " removed from Course: " + CourseActorId);
            }else {
            //    System.out.println("Student" + this.actorId + " Cannot be removed from " + CourseActorId);
                this.complete(false);
                this.actorState.addRecord(getActionName());
            }
        });
    }

    public Unregister(String studentActorId){
        this.studentActorId=studentActorId;
        this.setActionName("Unregister");
        this.Result = new Promise<>();
    }


}

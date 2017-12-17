package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class Unregister extends Action<Boolean> {

    private String CourseActorId;

    @Override
    protected void start() {
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new UnregisterConfirmation(this.actorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, CourseActorId , new CoursePrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((StudentPrivateState)this.actorState).removeCourse(this.CourseActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
           //     System.out.println("Student" + this.actorId + " removed from Course: " + CourseActorId);
            }else {
            //    System.out.println("Student" + this.actorId + " Cannot be removed from " + CourseActorId);
                this.complete(false);
            }
        });
    }

    public Unregister(String CourseId){
        this.CourseActorId=CourseId;
        this.setActionName("Unregister");
        this.Result = new Promise<Boolean>();
    }


}

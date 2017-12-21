package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CloseACourseConfirmation extends bgu.spl.a2.Action<Boolean>{
    //we in the course Actor

    @Override
    protected void start() {
        List<Action<Boolean>> actions = new ArrayList<>();
        //Unregister all the Students
        Iterator<String> StudentsList = ((CoursePrivateState)this.actorState).getRegStudents().iterator();
        Action<Boolean> removeStudent;
        while(StudentsList.hasNext()){
            removeStudent = new Unregister(StudentsList.next());
            actions.add(removeStudent);
            this.sendMessage(removeStudent,this.actorId,this.actorState);
        }

        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((CoursePrivateState)this.actorState).setAvailableSpots(-1);
                this.complete(true);
                //           System.out.println("Course: " + this.actorId + " Closed in the Department: " + DepartmentActorId);
            }else {
                //           System.out.println("Closing The Course: " + this.actorId + " Failed!");
                this.complete(false);
            }
        });

    }

    public CloseACourseConfirmation(){
        this.setActionName("Close A Course Confirmation");
        this.Result = new Promise<Boolean>();
    }

}

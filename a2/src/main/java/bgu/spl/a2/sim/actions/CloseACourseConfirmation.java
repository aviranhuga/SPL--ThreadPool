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
        ((CoursePrivateState)this.actorState).setAvailableSpots(-1);
        List<Action<Boolean>> actions = new ArrayList<>();
        //Unregister all the Students
        Iterator<String> StudentsList = ((CoursePrivateState)this.actorState).getRegStudents().iterator();
        Action<Boolean> removeStudent;
        while(StudentsList.hasNext()){
            removeStudent = new Unregister(StudentsList.next());
            actions.add(removeStudent);
            this.sendMessage(removeStudent,this.actorId,this.actorState);
        }
        this.then(actions,()-> this.complete(true) );
    }

    public CloseACourseConfirmation(){
        this.setActionName("Close A Course Confirmation");
        this.Result = new Promise<Boolean>();
    }

}

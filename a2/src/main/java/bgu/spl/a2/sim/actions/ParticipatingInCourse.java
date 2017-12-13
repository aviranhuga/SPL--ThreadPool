package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class ParticipatingInCourse extends Action<Boolean> {

    private String StudentActorId;
    private String CourseActorId;
    private int Grade;

    @Override
    protected void start() {
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new ParticipatingInCourseConfirmation(this.StudentActorId,this.CourseActorId);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, CourseActorId , new CoursePrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                ((StudentPrivateState)this.pool.getPrivateState(StudentActorId)).addGrade(this.CourseActorId,this.Grade);
                ((CoursePrivateState)this.pool.getPrivateState(CourseActorId)).addStudents(StudentActorId);
                this.complete(true);
                this.actorState.addRecord(getActionName());
                System.out.println("Student: " + StudentActorId + " Participating In Course: " + CourseActorId + " Grade: " + Grade);
            }else {
                System.out.println("Student: " + StudentActorId + " Participating In Course: " + CourseActorId + " Failed!");
                this.complete(false);
            }
        });
    }

    public ParticipatingInCourse(String StudentId, String CourseId, int Grade){
        this.StudentActorId=StudentId;
        this.CourseActorId=CourseId;
        this.Grade=Grade;
        this.setActionName("Participating In Course");
        this.Result = new Promise<Boolean>();
    }


}

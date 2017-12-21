package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;


public class RegisterWithPreferences extends Action<Boolean>{
    //we are in the Student Actor
    private LinkedList<String> Preferences;
    private LinkedList<Integer> Grades;

    @Override
    protected void start() {
        if (Preferences.isEmpty() || Grades.isEmpty()){
            this.complete(false);
            this.actorState.addRecord(getActionName());
            return;
        }
        LinkedList<Action<Boolean>> actions = new LinkedList<>();
        Action<Boolean> TryRegForCourse = new ParticipatingInCourse(this.actorId,Grades.poll());
        actions.add(TryRegForCourse);
        this.sendMessage(TryRegForCourse, Preferences.poll(),new CoursePrivateState());

        this.then(actions,()->{
            if(actions.poll().getResult().get()) {
                this.complete(true);
                this.actorState.addRecord(getActionName());
            }else
                this.sendMessage(this,this.actorId,this.actorState);
        });
    }

    public RegisterWithPreferences(LinkedList<String> Preferences,LinkedList<Integer> Grades){
        this.Grades=Grades;
        this.Preferences=Preferences;
        this.setActionName("Register With Preferences");
        this.Result = new Promise<>();
    }

}


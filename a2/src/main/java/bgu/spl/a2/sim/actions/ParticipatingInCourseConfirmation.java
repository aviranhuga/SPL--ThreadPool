package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class ParticipatingInCourseConfirmation extends Action<Boolean>{

    private String StudentActorId;
    private String CourseActorId;

    @Override
    protected void start() {
        CoursePrivateState Course = (CoursePrivateState)this.pool.getPrivateState(this.CourseActorId);
        StudentPrivateState Student = (StudentPrivateState)this.pool.getPrivateState(this.StudentActorId);
        HashMap<String,Integer> StudentGrades = Student.getGrades();
        Iterator<String> prequisites = ((Vector)Course.getPrequisites()).iterator();
        Boolean preCond = true;

        while (prequisites.hasNext() && preCond){
            if(!StudentGrades.containsKey(prequisites.next()))
                preCond=false;
        }

        this.complete(preCond);
    }

     ParticipatingInCourseConfirmation(String StudentId , String CourseId){
        this.StudentActorId = StudentId;
        this.CourseActorId = CourseId;
        this.setActionName("Participating In Course Confirmation");
        this.Result = new Promise<Boolean>();
    }


}

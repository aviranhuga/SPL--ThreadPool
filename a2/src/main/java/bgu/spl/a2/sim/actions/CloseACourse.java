package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CloseACourse extends bgu.spl.a2.Action<Boolean>{

    private String DepartmentActorId;
    private String courseName;

    @Override
    protected void start(){
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> Confirmation = new CloseACourseConfirmation(this.DepartmentActorId,this.courseName);
        actions.add(Confirmation);
        this.sendMessage(Confirmation, DepartmentActorId , new DepartmentPrivateState());
        this.then(actions,()->{
            if(actions.get(0).getResult().get()) {
                //Delete Course from the Department
                ((DepartmentPrivateState) this.pool.getPrivateState(DepartmentActorId)).deleteCourse(courseName);

                Iterator<String> StudentsList = ((CoursePrivateState)this.pool.getPrivateState(courseName)).getRegStudents().iterator();
                while(StudentsList.hasNext())//delete course from grade sheet
                    ((StudentPrivateState)this.pool.getPrivateState(StudentsList.next())).removeCourse(this.courseName);
                ((CoursePrivateState)this.pool.getPrivateState(courseName)).getRegStudents().clear();
                ((CoursePrivateState)this.pool.getPrivateState(courseName)).setAvailableSpots(-1);

                this.complete(true);
                this.actorState.addRecord(getActionName());
                System.out.println("Course: " + courseName + " Closed in " + DepartmentActorId);
            }else {
                System.out.println("Closing " + courseName + " Failed!");
                this.complete(false);
            }
        });

    }

    public CloseACourse(String courseName , String DepartmentActorId){
        this.ActionName = "Close A Course";
        this.Result = new Promise<Boolean>();
        this.DepartmentActorId = DepartmentActorId;
        this.courseName = courseName;
    }
}


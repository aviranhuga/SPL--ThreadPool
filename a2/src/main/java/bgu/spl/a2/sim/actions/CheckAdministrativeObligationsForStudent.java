package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class CheckAdministrativeObligationsForStudent extends Action<Boolean> {
    private Computer computer;
    private LinkedList<String> Conditions;

    @Override
    protected void start() {
        StudentPrivateState student = (StudentPrivateState)this.actorState;
        student.setSignature(this.computer.checkAndSign(Conditions,student.getGrades()));
    //    System.out.println(this.actorId + " sig is: " +student.getSignature());
        this.complete(true);
    }

    public CheckAdministrativeObligationsForStudent(Computer computer, LinkedList<String> Conditions){
        this.computer=computer;
        this.Conditions=Conditions;
        this.Result = new Promise<Boolean>();
        this.ActionName="Check Administrative Obligations For Student";
    }
}

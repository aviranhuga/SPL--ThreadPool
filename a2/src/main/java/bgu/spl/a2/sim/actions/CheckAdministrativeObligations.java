package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CheckAdministrativeObligations extends Action<Boolean>{

    private LinkedList<String> Students;
    private LinkedList<String> Conditions;
    private String ComputerType;
    private Warehouse warehouse;
    private Promise<Computer> computerPromise;

    @Override
    protected void start() {
            this.computerPromise = this.warehouse.acquireComputer(ComputerType);
            computerPromise.subscribe(() -> {
                this.call = ()-> {
                    List<Action<Boolean>> actions = new ArrayList<>();
                    Iterator<String> studentIt = this.Students.iterator();

                    while (studentIt.hasNext()) {
                        Action<Boolean> Confirmation = new CheckAdministrativeObligationsForStudent(computerPromise.get(), Conditions);
                        actions.add(Confirmation);
                        this.sendMessage(Confirmation, studentIt.next(), new StudentPrivateState());
                    }

                    this.then(actions, () -> {
                        this.warehouse.releaseComputer(ComputerType);
                        this.complete(true);
                        this.actorState.addRecord(getActionName());
    //                    System.out.println("Finish Administrative Check" );

                    });
                };
                this.Continue = true;
                sendMessage(this, this.actorId, this.actorState);
            });
    }

    public CheckAdministrativeObligations(LinkedList<String> Students,LinkedList<String> Conditions,String ComputerType,Warehouse warehouse){
        this.ComputerType=ComputerType;
        this.Conditions=Conditions;
        this.Students=Students;
        this.warehouse=warehouse;
        this.ActionName="Administrative Check";
        this.Result = new Promise<Boolean>();

    }
}

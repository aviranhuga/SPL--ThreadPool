package bgu.spl.a2.sim;

import bgu.spl.a2.Promise;

import java.util.HashMap;
/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {

    private HashMap<String,Computer> Computers;

	public Warehouse(HashMap<String,Computer> Computers){this.Computers=Computers;}

	private Computer getComputer(String computerType){return this.Computers.get(computerType);}

	public Promise<Computer> acquireComputer(String computerType){
	    return this.getComputer(computerType).getSuspendingMutex().down();
    }

    public void releaseComputer(String computerType){
	    this.getComputer(computerType).getSuspendingMutex().up();
    }
}

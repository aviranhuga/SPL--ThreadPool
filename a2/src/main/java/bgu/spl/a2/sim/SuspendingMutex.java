package bgu.spl.a2.sim;
import bgu.spl.a2.Promise;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	private Computer computer;
	private AtomicBoolean available;
	private ConcurrentLinkedQueue<Promise<Computer>> PromiseQueue;

	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		this.computer=computer;
		this.available= new AtomicBoolean(true);
		this.PromiseQueue = new ConcurrentLinkedQueue<>();
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(){
		Promise<Computer> getComputer = new Promise<Computer>();
		Boolean avilableComptuer;
		if(available.compareAndSet(true,false)){
			getComputer.resolve(this.computer);
			return getComputer;
		}
		else{
			PromiseQueue.add(getComputer);
			return getComputer;
		}
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){

	}
}

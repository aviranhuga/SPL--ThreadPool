package bgu.spl.a2;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {

	private ConcurrentHashMap<String,ConcurrentLinkedQueue> actors;
	private ConcurrentHashMap<String,PrivateState> privatestate;
	private ConcurrentHashMap<String,Boolean> availableActor;
	private final int nthreads;
	private Thread[] threads;
	private VersionMonitor version;
	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads) {

		this.nthreads = nthreads;
		this.threads = new Thread[nthreads];
		this.version = new VersionMonitor();
		this.actors = new ConcurrentHashMap<String,ConcurrentLinkedQueue>();
		this.privatestate = new ConcurrentHashMap<String, PrivateState>();
		this.availableActor = new ConcurrentHashMap<String,Boolean> ();
		for (int i=0 ; i< this.threads.length ; i++)
			threads[i] = new Thread(()-> ThreadMission());
	}

	/**
	 * getter for actors
	 * @return actors
	 */
	public Map<String, PrivateState> getActors(){
		return this.privatestate;
	}
	
	/**
	 * getter for actor's private state
	 * @param actorId actor's id
	 * @return actor's private state
	 */
	public PrivateState getPrivateState(String actorId){
		return this.privatestate.get(actorId);
	}


	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {
		if(!actors.containsKey(actorId)) { // The actor don't have a Queue
			ConcurrentLinkedQueue<Action<?>> actorQueue = new ConcurrentLinkedQueue<Action<?>>();
			actors.put(actorId,actorQueue);
			availableActor.put(actorId,true);
			privatestate.put(actorId,actorState);
		}//add action to the Queue
		actors.get(actorId).add(action);
		this.version.inc();
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public void shutdown() throws InterruptedException {
		for (Thread thread:threads)
			thread.interrupt();
		for (Thread thread:threads)
			thread.join();
		System.out.println("___________________________________________________________________________________________________");
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		for (Thread thread:threads)
			thread.start();
	}

	private void ThreadMission(){
		String actorId = "";
		Boolean foundAction = false;

		while(!Thread.currentThread().isInterrupted()) {
			//Find available Actor Queue
			synchronized (availableActor) {
				Iterator<String> ActorList = availableActor.keySet().iterator();// Id Iterator
				while (ActorList.hasNext() && !foundAction) {
					actorId = ActorList.next();
					if (availableActor.get(actorId) && !(actors.get(actorId).isEmpty())) {//Found available Action
						availableActor.put(actorId, false);
						foundAction = true;
					}
				}
			}//end of sync
			if (foundAction) {
				((Action<?>) this.actors.get(actorId).poll()).handle(this, actorId, this.getPrivateState(actorId));// make the Action
				availableActor.put(actorId, true);
				this.version.inc();
				foundAction = false;
			}
			else{//no available Action
				try {
					this.version.await(this.version.getVersion());
				}
				catch (InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}//end of else
		}//end of while
	}
}

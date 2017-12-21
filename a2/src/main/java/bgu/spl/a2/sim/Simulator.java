/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	protected static JsonHandler jsonHandler;

	public static ActorThreadPool actorThreadPool;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
		jsonHandler.buildWarehouse();
		actorThreadPool.start();

		System.out.println("___________Phase 1 Starting_________");
		ActionsList actions = jsonHandler.phaseActions("Phase 1");
		CountDownLatch phase1 = new CountDownLatch(actions.getSize());
		while(!actions.isEmpty()) {
			Action<?> nextAction = actions.getNextAction();
			actorThreadPool.submit(nextAction, actions.getNextAcorId(), actions.getNextPrivatestate());
			nextAction.getResult().subscribe(() -> phase1.countDown());
		}
		try {
			phase1.await();
		} catch (InterruptedException e) {}

		System.out.println("___________Phase 2 Starting_________");
		actions = jsonHandler.phaseActions("Phase 2");
		CountDownLatch phase2 = new CountDownLatch(actions.getSize());
		while(!actions.isEmpty()) {
			Action<?> nextAction = actions.getNextAction();
			actorThreadPool.submit(nextAction, actions.getNextAcorId(), actions.getNextPrivatestate());
			nextAction.getResult().subscribe(() -> phase2.countDown());
		}
		try {
			phase2.await();
		} catch (InterruptedException e) {}

		System.out.println("___________Phase 3 Starting_________");
		actions = jsonHandler.phaseActions("Phase 3");
		CountDownLatch phase3 = new CountDownLatch(actions.getSize());
		while(!actions.isEmpty()) {
			Action<?> nextAction = actions.getNextAction();
			actorThreadPool.submit(nextAction, actions.getNextAcorId(), actions.getNextPrivatestate());
			nextAction.getResult().subscribe(() -> phase3.countDown());
		}
		try {
			phase3.await();
		} catch (InterruptedException e) {}


		System.out.println("__________________end_______________");
		end();
	}
	
	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
			actorThreadPool = myActorThreadPool;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String,PrivateState> end(){
		Map<String,PrivateState> SimlationResult = actorThreadPool.getActors();
		HashMap<String,PrivateState> Result = new HashMap<>(SimlationResult);
		try {
			actorThreadPool.shutdown();
			FileOutputStream fout = new FileOutputStream("result.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(Result);

		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return Result;
	}
	
	
	public static void main(String [] args) {
		//String Path = "C:\\Users\\avira\\Desktop\\SPL-Assinment2\\test.json";
			jsonHandler = new JsonHandler(args[0]);
			attachActorThreadPool(jsonHandler.buildActorThreadPool());
			start();

	}
}

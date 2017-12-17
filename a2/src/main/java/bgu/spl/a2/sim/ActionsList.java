package bgu.spl.a2.sim;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;

import java.util.LinkedList;

public class ActionsList {
    private LinkedList<Action<?>> actions;
    private LinkedList<String> actorIds;
    private LinkedList<PrivateState> privateStates;
    private int size;

    public ActionsList(LinkedList<Action<?>> actions,LinkedList<String> actorIds,LinkedList<PrivateState> privateStates){
        this.actions=actions;
        this.actorIds=actorIds;
        this.privateStates=privateStates;
        size = actions.size();
    }

    public Action<?> getNextAction(){
        if(actions.isEmpty()) return null;
        return  actions.poll();
    }

    public String getNextAcorId(){
        if(actorIds.isEmpty()) return null;
        return actorIds.poll();
    }

    public PrivateState getNextPrivatestate(){
        if(privateStates.isEmpty()) return null;
        return privateStates.poll();
    }

    public boolean isEmpty(){
        return actions.size()==0;
    }

    public int getSize(){return this.size;}
}

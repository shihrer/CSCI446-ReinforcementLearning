package CSCI446.Project4;

import CSCI446.Project4.Algorithms.Action;
import CSCI446.Project4.Track.PhysX;
import CSCI446.Project4.Track.Result;
import CSCI446.Project4.Track.State;

public class Car {
    private Tuple position;
    private Tuple velocity;
    private PhysX physx;

    private State lastState;
    private State curState;
    private Action lastAction;
    private Action curAction;

    public Car(Tuple position, PhysX physX){
        this.position = position;
        velocity = new Tuple(0,0);
        curState = new State(this.position, this.velocity);
        lastState = curState;
        this.physx = physX;
    }

    Result applyAction(Action newAction) throws Exception {
        Tuple newAcc = newAction.getTuple();
        if(newAcc.getX() > 1)
            newAcc.setX(1);
        if(newAcc.getX() < -1)
            newAcc.setX(-1);
        if(newAcc.getY() > 1)
            newAcc.setY(1);
        if(newAcc.getY() < -1)
            newAcc.setY(-1);
        if(curAction == null)
            curAction = newAction;
        lastAction = curAction;
        curAction = newAction;
        State newState = physx.calculateNextState(newAcc, curState);
        Result result = physx.findResult(curState, newState);
        updateState(newState);
        return result;
    }
    private void updateState(State newState){
        position = newState.getLocation();
        velocity = newState.getVelocity();
        lastState = curState;
        curState = newState;
    }

    public void resetState(State newState){
        position = newState.getLocation();
        velocity = newState.getVelocity();
        curState = newState;
    }

    Tuple getCurrentLocation() { return position;}
    Tuple getCurrentVelocity() { return velocity; }
    State getCurrentState() { return curState; }

    Action getLastAction() {
        return lastAction;
    }

    State getLastState() {
        return lastState;
    }

    public void goToLastState() {
        curState = new State(lastState.getLocation(), new Tuple(0,0));
        position = curState.getLocation();
        velocity = curState.getVelocity();
    }
}

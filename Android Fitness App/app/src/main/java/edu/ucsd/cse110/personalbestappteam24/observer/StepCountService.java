package edu.ucsd.cse110.personalbestappteam24.observer;

import java.util.Observable;

public class StepCountService extends Observable {
    //boolean isNotified;

    public StepCountService() {
        //isNotified = false;
    }

    public void notify(int arg) {
        setChanged();
        notifyObservers(arg);
    }

}

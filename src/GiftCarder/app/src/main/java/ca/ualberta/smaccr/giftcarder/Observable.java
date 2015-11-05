package ca.ualberta.smaccr.giftcarder;

import java.util.Observer;

/**
 * Created by splant on 11/4/15.
 */
public interface Observable {
    public void addObserver(Observer o);
    public void deleteObserver(Observer o);
    public void notifyObservers();
}

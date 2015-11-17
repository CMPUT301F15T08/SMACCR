package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/16/15.
 */

public interface Observable {
    public void addObserver(Observer o);
    public void deleteObserver(Observer o);
    public void notifyObservers();
}
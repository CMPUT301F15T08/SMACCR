package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by Richard on 2015-10-24.
 * Refactored and maintained by Spencer from 2015-10-28
 * Some of this class comes from the CMPUT 301 lab on ElasticSearch
 */
public class Inventory extends ArrayList<GiftCard> implements Serializable, Observable {
    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();
    ArrayList<GiftCard> inv = new ArrayList<GiftCard>();
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t08/giftcard/";
    private static final String SEARCH_URL   = "http://cmput301.softwareprocess.es:8080/cmput301f15t08/giftcard/_search";

    public Inventory() {}

    public void addGiftCard(GiftCard gc){
        inv.add(0, gc);
    }

    public void deleteGiftCard(int gcIndex){
        inv.remove(gcIndex);
    }

    // Taken from CMPUT 301 ElasticSearch Lab
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            // o.notifyUpdated(this);
        }
    }

    public int getSize(){
        return inv.size();
    }

    // Getters and setters
    public ArrayList<GiftCard> getInvList() {
        return inv;
    }

    public void setInv(ArrayList<GiftCard> inv) {
        this.inv = inv;
    }

    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * Java wants this, we don't need it for Gson/Json
     * *** From CMPUT 301 Lab on ElasticSearch
     */
    private static final long serialVersionUID = 3199561696102797345L;
}

package ca.ualberta.smaccr.giftcarder;
/*
collection of inventories
display order based on date
 */

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by mrijlaar on 10/27/15.
 */
public class Cache {

    private LinkedList<GiftCard> items;
    private Date lastUpdated;

    public Cache(){
        items = new LinkedList();
        lastUpdated = new Date();
    }

    public Cache(Collection<GiftCard> col){
        this.items = new LinkedList<GiftCard>(col);
        lastUpdated = new Date();
    }

    public LinkedList<GiftCard> getItems() {
        return items;
    }

    public void setItems(LinkedList<GiftCard> giftCards) {
        this.items = giftCards;
    }

    /*
    * add
    * add one giftcard to the cache
    * @Giftcard
    */
    public void add(GiftCard giftCard){
        this.items.addFirst(giftCard);
    }

    /*
    * add
    * add a collection of of Giftcards to the cache
    * @Collection<Giftcard>
    */
    public void add(Collection<GiftCard> col){
        Iterator<GiftCard> iterator = col.iterator();
        GiftCard gc;
        while (iterator.hasNext()){
            gc = iterator.next();
            if(!items.contains(gc)){
                items.add(gc);
            }
        }
        updateDate();
    }

    /*
    * add
    * add all the giftcards in an inventory to a cache
    * @Inventory
    * void
    * */
    public void add(Inventory inv){
        add(inv.getInvList());
    }

    private void updateDate(){
        this.lastUpdated = new Date();
    }

    /*
    * size
    * returns the number of giftcards in the cache
    * none
    * return int
    * */
    public int size(){
        return items.size();
    }
}

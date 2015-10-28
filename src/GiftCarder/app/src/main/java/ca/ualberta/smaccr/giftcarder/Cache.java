package ca.ualberta.smaccr.giftcarder;


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

    public void add(GiftCard giftCard){
        this.items.addFirst(giftCard);
    }

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

    private void updateDate(){
        this.lastUpdated = new Date();
    }

    public int size(){
        return items.size();
    }
}

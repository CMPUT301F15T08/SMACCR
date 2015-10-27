package ca.ualberta.smaccr.giftcarder;


import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by mrijlaar on 10/27/15.
 */
public class Cache {

    private LinkedList<Item> items;

    public void Cache(){
        items = new LinkedList();
    }

    public void Cache(Collection<Item> col){
        this.items = new LinkedList<Item>(col);
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public void add(Item item){
        this.items.addFirst(item);
    }

}

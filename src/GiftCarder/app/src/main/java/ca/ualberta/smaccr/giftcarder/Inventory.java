package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-24.
 */
public class Inventory {
    ArrayList<GiftCard> myinventory = new ArrayList<GiftCard>();

    public void addGiftCard(GiftCard gc){
        myinventory.add(gc);
    }

    public void deleteGiftCard(GiftCard gc){
        myinventory.remove(gc);
    }

    public int getSize(){
        return myinventory.size();
    }


    //Getters and setters
    public ArrayList<GiftCard> getMyinventory() {
        return myinventory;
    }

    public void setMyinventory(ArrayList<GiftCard> myinventory) {
        this.myinventory = myinventory;
    }
}

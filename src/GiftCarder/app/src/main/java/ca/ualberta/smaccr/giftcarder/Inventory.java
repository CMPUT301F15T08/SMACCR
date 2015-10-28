package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-24.
 */
public class Inventory {
    ArrayList<GiftCard> inv = new ArrayList<GiftCard>();

    public void addGiftCard(GiftCard gc){
        inv.add(gc);
    }

    public void deleteGiftCard(GiftCard gc){
        inv.remove(gc);
    }

    public int getSize(){
        return inv.size();
    }


    // Getters and setters
    public ArrayList<GiftCard> getInv() {
        return inv;
    }

    public void setInv(ArrayList<GiftCard> inv) {
        this.inv = inv;
    }
}

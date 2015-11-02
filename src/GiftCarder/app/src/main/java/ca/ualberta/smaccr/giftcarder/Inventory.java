package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-24.
 * Refactored and maintained by Spencer from 2015-10-28
 */
public class Inventory implements Serializable {
    ArrayList<GiftCard> inv = new ArrayList<GiftCard>();

    public void addGiftCard(GiftCard gc){
        inv.add(0, gc);
    }

    public void deleteGiftCard(int gcIndex){
        inv.remove(gcIndex);
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
}

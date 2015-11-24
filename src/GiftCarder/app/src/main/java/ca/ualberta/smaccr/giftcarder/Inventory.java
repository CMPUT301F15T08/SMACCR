package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-24.
 * Refactored and maintained by Spencer from 2015-10-28
 */
public class Inventory implements Serializable {
    private ArrayList<GiftCard> inv;

    public Inventory() {this.inv = new ArrayList<GiftCard>(); }

    public Inventory(ArrayList<GiftCard> that) {this.inv = that; }

    public void addGiftCard(GiftCard gc){
        this.inv.add(0, gc);
    }

    public void deleteGiftCard(int gcIndex){
        this.inv.remove(gcIndex);
    }

    public int getSize(){
        return this.inv.size();
    }


    // Getters and setters
    public ArrayList<GiftCard> getInvList() {
        return this.inv;
    }

    public void setInv(ArrayList<GiftCard> inv) {
        this.inv = inv;
    }

    public GiftCard getGiftCard(int position) {
        return this.inv.get(position);
    }
}

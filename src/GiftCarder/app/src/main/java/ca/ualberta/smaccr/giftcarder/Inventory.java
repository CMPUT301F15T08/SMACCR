package ca.ualberta.smaccr.giftcarder;

import java.util.Collection;

/**
 * Created by Spencer on 10/25/2015.
 */

// An inventory class for storing all the Giftcards of a user
public class Inventory {
    Collection<GiftCard> contents;

    public Inventory getInventory() {
        return this;
    }

    public void addGiftCard(GiftCard gc) {
        contents.add(gc);
    }

    public void remGiftCard(GiftCard gc) {
        if (contents.contains(gc)) {
            contents.remove(gc);
        }
    }
}

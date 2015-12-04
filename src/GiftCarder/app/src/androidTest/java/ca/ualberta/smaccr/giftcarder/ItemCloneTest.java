package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by cbli on 12/4/15.
 */
public class ItemCloneTest extends ActivityInstrumentationTestCase2 {

    public ItemCloneTest() {
        super(ca.ualberta.smaccr.giftcarder.ItemActivity.class);
    }

    /**
     * Tests that ItemActivity starts
     */
    public void testStart() throws Exception {
        ItemActivity activity = (ItemActivity) getActivity();
    }

    // Tests that friend's item is cloned into user's inventory (UC 13.1)
    public void testSingleItemClone() throws Exception {

        // Create friend with one gift card
        GiftCard friendGCard  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        Inventory friendInv = new Inventory();
        friendInv.addGiftCard(friendGCard);

        User friend = new User();
        friend.setUsername("friend");
        friend.setInv(friendInv);
        assertFalse(friend.getInv().getSize() == 0); // friend's inventory has one gift card

        // Create user
        User user = new User();
        user.setUsername("user");
        Inventory userInv = user.getInv();
        assertTrue(user.getInv().getSize() == 0); // user's inventory has no gift cards

        ItemController ic = new ItemController();
        userInv = ic.cloneItem(friendInv.getGiftCard(0), userInv, "user");
        user.setInv(userInv);
        assertFalse(user.getInv().getSize() == 0); // user's inventory has one gift card

        // gift card is the same as friend's gift card
        assertTrue(user.getInv().getGiftCard(0).getMerchant().equals("Starbucks"));
    }

    // Tests that friend's item can be cloned into user's inventory twice (UC 13.1)
    public void testDoubleItemClone() throws Exception {

        // Create friend with one gift card
        GiftCard friendGCard  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        Inventory friendInv = new Inventory();
        friendInv.addGiftCard(friendGCard);

        User friend = new User();
        friend.setUsername("friend");
        friend.setInv(friendInv);
        assertFalse(friend.getInv().getSize() == 0); // friend's inventory has one gift card

        // Create user
        User user = new User();
        user.setUsername("user");
        Inventory userInv = user.getInv();
        assertTrue(user.getInv().getSize() == 0); // user's inventory has no gift cards

        ItemController ic = new ItemController();
        userInv = ic.cloneItem(friendInv.getGiftCard(0), userInv, "user");
        userInv = ic.cloneItem(friendInv.getGiftCard(0), userInv, "user");
        user.setInv(userInv);
        assertTrue(user.getInv().getSize() == 2); // user's inventory has two gift cards

        // both gift cards are the same as friend's gift card
        assertTrue(user.getInv().getGiftCard(0).getMerchant().equals("Starbucks"));
        assertTrue(user.getInv().getGiftCard(1).getMerchant().equals("Starbucks"));
    }


}

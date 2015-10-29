package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by Richard on 2015-10-29.
 */
public class GiftCardTest extends ActivityInstrumentationTestCase2 {
    public GiftCardTest() {
        super(ca.ualberta.smaccr.giftcarder.MainActivity.class);
    }

    //GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
    // As an owner, I want to edit an item
    // As an owner, not every item in my inventory will be shared or listed
    // As an owner, I want to edit and modify inventory items
    public void testEditingGiftCard()throws Exception {
        GiftCard gcard  = new GiftCard("$5 Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);

        GiftCard gcard2 = new GiftCard("$10 Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);

        gcard2.setMerchant("$5 Starbucks");
        gcard2.setQuantity(2);
        gcard2.setQuality(2);
        gcard2.setCategory(1);
        gcard2.setComments("Will get you half a coffee");
        gcard2.setShared(Boolean.TRUE);

        assertTrue(gcard.getMerchant().equals(gcard2.getMerchant()));
        assertTrue(gcard.getQuantity() == gcard2.getQuantity());
        assertTrue(gcard.getCategory() == gcard2.getCategory());
        assertTrue(gcard.getComments().equals(gcard2.getComments()));
        assertTrue(gcard.getShared() == gcard2.getShared());

        //quality does not match
        assertFalse(gcard.getQuality() == gcard2.getQuality());
    }

    // As an owner, I want the category for an item to be one of 10 relevant categories for <THINGS>.

    /*
    Category is represented as a integer
     0=Food-Beverage
     1=Clothing
     2=Home-garden
     3=Electronics
     4=Department Store(eg. Walmart)
     5=Services
     6=Entertainment
     7=Online Retailers(eg. Amazon)
     9=Health-beauty
     10=Other
     */
    public void testVerifyCategory() throws Exception {
        GiftCard gcard  = new GiftCard("$5 Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);

        assertTrue((gcard.getCategory() >= 0)&& (gcard.getCategory() < 11));

        gcard.setCategory(100);
        assertFalse((gcard.getCategory() >= 0) && (gcard.getCategory() < 11));

    }

    // As an owner, I want the entry of an item to have minimal required
    // navigation in the user interface. It must be easy to describe an item.
    //Go UI design is easy to navigate
}
package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
     1=Food-Beverage
     2=Clothing
     3=Home-garden
     4=Electronics
     5=Department Store(eg. Walmart)
     6=Services
     7=Entertainment
     8=Online Retailers(eg. Amazon)
     9=Health-beauty
     10=Other
     */
    public void testVerifyCategory() throws Exception {
        GiftCard gcard  = new GiftCard("$5 Starbucks", 2, 1, 0, "Will get you half a coffee", Boolean.TRUE);

        assertTrue(gcard.checkCategory());
        gcard.setCategory(10);
        assertTrue(gcard.checkCategory());

        gcard.setCategory(100);
        assertFalse(gcard.checkCategory());

    }

    // As an owner, I want the entry of an item to have minimal required
    // navigation in the user interface. It must be easy to describe an item.
    // http://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java
    public void testSortVal() throws Exception{
        ArrayList<GiftCard> gcards = new ArrayList<GiftCard>();
        GiftCard gcard  = new GiftCard("$5 Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        GiftCard gcard2 = new GiftCard("$10 Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);
        GiftCard gcard3 = new GiftCard("Subway", 1, 1, 1, "Wonderful hexcrements ;) (hex increments ~ incrments of 6)",Boolean.FALSE);
        gcards.add(gcard);
        gcards.add(gcard2);
        gcards.add(gcard3);

        Collections.sort(gcards, new Comparator<GiftCard>() {
            @Override
            public int compare(GiftCard card1, GiftCard card2) {
                if (card1.getCategory()  > (card2.getCategory())){
                    return 1;
                }
                else if (card1.getCategory()  < (card2.getCategory())){
                    return -1;
                }
                return 0;
            }
        });
        assertTrue((gcards.get(0) == gcard) && (gcards.get(1) == gcard3));
    }

    // As an owner, I want the entry of an item to have minimal required
    // navigation in the user interface. It must be easy to describe an item.
    public void testGetCategory() throws Exception{
        ArrayList<GiftCard> gcards = new ArrayList<GiftCard>();
        GiftCard gcard  = new GiftCard("$5 Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        GiftCard gcard2 = new GiftCard("$10 Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);
        GiftCard gcard3 = new GiftCard("Subway", 1, 1, 1, "Wonderful hexcrements ;) (hex increments ~ incrments of 6)",Boolean.FALSE);
        gcards.add(gcard);
        gcards.add(gcard2);
        gcards.add(gcard3);


        ArrayList<GiftCard> outlist = new ArrayList<GiftCard>();

        for (int i = 0; i < gcards.size(); ++i) {
            if (gcards.get(i).getCategory() == 1) {
                outlist.add(gcards.get(i));
            }
        }
        assertTrue(outlist.size() == 2);
        assertFalse(outlist.size() == 3);
    }
}
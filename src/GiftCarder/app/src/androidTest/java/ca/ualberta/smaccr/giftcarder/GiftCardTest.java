package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by Richard on 2015-10-29.
 */
public class GiftCardTest extends ActivityInstrumentationTestCase2 {

    private EditText itemName;
    private EditText itemValue;
    private EditText quantity;
    private Spinner qualitySpinner;
    private Spinner categorySpinner;
    private EditText comments;
    private CheckBox checkbox;
    public Inventory inv;
    int position;

    public GiftCardTest() {
        super(ca.ualberta.smaccr.giftcarder.ItemActivity.class);
    }

    //GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
    // As an owner, I want to edit an item
    // As an owner, not every item in my inventory will be shared or listed
    // As an owner, I want to edit and modify inventory items
    public void testEditingGiftCard()throws Exception {
        GiftCard gcard  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);

        GiftCard gcard2 = new GiftCard(10, "Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);

        gcard2.setMerchant("$5 Starbucks");
        gcard2.setQuantity(2);
        gcard2.setQuality(2);
        gcard2.setCategory(1);
        gcard2.setComments("Will get you half a coffee");
        gcard2.setShared(Boolean.TRUE);

        assertFalse(gcard.getMerchant().equals(gcard2.getMerchant()));
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
        GiftCard gcard  = new GiftCard(10 ,"Starbucks", 2, 1, 0, "Will get you half a coffee", Boolean.TRUE);

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
        GiftCard gcard  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        GiftCard gcard2 = new GiftCard(10, "Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);
        GiftCard gcard3 = new GiftCard(10, "Subway", 1, 1, 1, "Wonderful hexcrements ;) (hex increments ~ incrments of 6)",Boolean.FALSE);
        gcards.add(gcard);
        gcards.add(gcard2);
        gcards.add(gcard3);

        Collections.sort(gcards, new Comparator<GiftCard>() {
            @Override
            public int compare(GiftCard card1, GiftCard card2) {
                if (card1.getCategory() > (card2.getCategory())) {
                    return 1;
                } else if (card1.getCategory() < (card2.getCategory())) {
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
        GiftCard gcard  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        GiftCard gcard2 = new GiftCard(2, "Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);
        GiftCard gcard3 = new GiftCard(5, "Subway", 1, 1, 1, "Wonderful hexcrements ;) (hex increments ~ incrments of 6)",Boolean.FALSE);
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

    //Test if we can get activity
    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testEditGiftCard() throws Exception {
        ItemActivity activity = (ItemActivity)getActivity();

        final String teststring = "Subway";
        final int testnumber = 1;

        itemName = activity.getItemName();
        itemValue = activity.getItemValue();
        quantity = activity.getQuantity();
        qualitySpinner = activity.getQualitySpinner();
        categorySpinner = activity.getCategorySpinner();
        comments = activity.getComments();
        checkbox = activity.getCheckbox();
        inv = activity.getInv();
        position = activity.getPosition();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemName.setText(teststring);
                itemValue.setText(String.valueOf(testnumber));
                quantity.setText(String.valueOf(testnumber));
                qualitySpinner.setSelection(testnumber, false);
                categorySpinner.setSelection(testnumber, false);
                comments.setText(teststring);
                checkbox.setSelected(true);
            }
        });

        getInstrumentation().waitForIdleSync();

        ItemController ic = new ItemController();

        inv = ic.setGiftCardInfo(inv, position, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);

        assertTrue(teststring.equals(inv.getInvList().get(position).getMerchant()));

    }
}
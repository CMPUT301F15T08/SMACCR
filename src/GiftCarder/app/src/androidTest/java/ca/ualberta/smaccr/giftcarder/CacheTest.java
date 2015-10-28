package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by mrijlaar on 10/28/15.
 */
public class CacheTest extends ActivityInstrumentationTestCase2 {

    public CacheTest() {
        super(ca.ualberta.smaccr.giftcarder.Cache.class);
    }

    public void testCache() throws Exception {
        Cache c1 = new Cache();
    }

    public void testCache1() throws Exception {

        ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>();

        GiftCard giftCard1 = new GiftCard();
        giftCard1.setMerchant("Bestbuy");
        giftCard1.setQuantity(1);
        giftCard1.setQuality(3);
        giftCard1.setCategory(1);
        giftCard1.setComments("scratched but usable");
        giftCard1.setShared(Boolean.TRUE);

        giftCards.add(giftCard1);

        Cache cache = new Cache((Collection<GiftCard>)giftCards);

        assertTrue(cache.size()>0);
    }

    public void testGetItems() throws Exception {
        Cache c1 = new Cache();

        GiftCard giftCard1 = new GiftCard();
        giftCard1.setMerchant("Bestbuy");
        giftCard1.setQuantity(1);
        giftCard1.setQuality(3);
        giftCard1.setCategory(1);
        giftCard1.setComments("scratched but usable");
        giftCard1.setShared(Boolean.TRUE);

        c1.add(giftCard1);

        LinkedList<GiftCard> linkedList;
        linkedList = c1.getItems();

        assertTrue(linkedList==c1.getItems());
    }

    public void testSetItems() throws Exception {
        LinkedList<GiftCard> linkedList = new LinkedList<GiftCard>();
        Cache c1 = new Cache();

        GiftCard giftCard1 = new GiftCard();
        giftCard1.setMerchant("Bestbuy");
        giftCard1.setQuantity(1);
        giftCard1.setQuality(3);
        giftCard1.setCategory(1);
        giftCard1.setComments("scratched but usable");
        giftCard1.setShared(Boolean.TRUE);

        linkedList.add(giftCard1);

    }

    public void testAdd() throws Exception {

    }

    public void testAdd1() throws Exception {

    }
}
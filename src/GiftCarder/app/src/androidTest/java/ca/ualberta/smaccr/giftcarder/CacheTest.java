package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrijlaar on 10/28/15.
 */

public class CacheTest extends ActivityInstrumentationTestCase2 {

    public CacheTest() {
        super(ca.ualberta.smaccr.giftcarder.Cache.class);
    }

    public void testCache() throws Exception {
        //Cache c1 = new Cache();
        username = "mtatest";
    }

    String username;
}/*

    public void testCache1() throws Exception {

        ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>();

        GiftCard giftCard1 = new GiftCard(username, 1.10, "Test Merchant", 1,3,1, "scratched but usable");

        giftCards.add(giftCard1);

        Inventory inventory = new Inventory(giftCards);

        Cache cache = new Cache(this, );
        cache.add(inventory);
        cache.loadItems();

        assertTrue(cache.itemsSize() > 0);
    }

    public void testGetItems() throws Exception {
        Cache c1 = new Cache();
        Inventory inventory = new Inventory();

        GiftCard giftCard1 = new GiftCard();
        giftCard1.setValue(10.10);
        giftCard1.setMerchant("Bestbuy");
        giftCard1.setQuantity(1);
        giftCard1.setQuality(3);
        giftCard1.setCategory(1);
        giftCard1.setComments("scratched but usable");
        giftCard1.setShared(Boolean.TRUE);

        c1.add(inventory);

        ArrayList<GiftCard> arrayList;
        arrayList = c1.getItems();
        assertTrue(arrayList == null);
        c1.loadItems();
        arrayList = c1.getItems();
        assertTrue(arrayList != null);

        assertTrue("val = "+arrayList.size(), arrayList.size()==0);

        inventory.addGiftCard(giftCard1);
        c1.loadItems();

        assertTrue(arrayList==c1.getItems());
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

    public void testMergeSort() throws Exception {


        LinkedList<GiftCard> giftCard= new LinkedList<GiftCard>();
        int size = 5;
        for (int i=0; i<size; i++){
            giftCard.add( new GiftCard((double)i, "TestMerchant", 1, (int)(Math.random()*4), (int)(Math.random()*11)));
            TimeUnit.SECONDS.sleep(1);
        }

        ArrayList<GiftCard> al1, al2;
        al1 = new ArrayList<GiftCard>();
        al2 = new ArrayList<GiftCard>();

        int r;//andom 0 or 1
        int s;//elect 0-giftcar.size
        for (int i=4; i>=0; i--){
            r = (int)(Math.random()*2);

            if (r==0){
                al1.add(0, giftCard.remove(i));
            } else {
                al2.add(0, giftCard.remove(i));
            }
        }


        Cache cache = new Cache();
        Inventory inv1 = new Inventory(al1), inv2 = new Inventory(al2);
        cache.add(inv1);
        cache.add(inv2);

        int v1 = cache.merge(al1, al2).size(), v2 = al1.size()+al2.size();
        assertTrue("testMerge v2: (" + al1.size()+" + "+al2.size()+")= "+v2+" != v1: "+v1 ,v1 == v2);//


        cache.loadItems();
        ArrayList<GiftCard> items = cache.getItems();
        String list = System.lineSeparator();

        for (int i=0; i<size-1; i++){
            list = list + i+", "+(i+1)+" = "+items.get(i).getDate().toString()+" < "+ items.get(i+1).getDate().toString()+ System.lineSeparator();
            assertTrue(list, items.get(i).getDate().before(items.get(i+1).getDate()));
        }

    }

    public void testBrowseAll(){

        ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>(4);
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 10));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 0));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 2));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 2));

        Inventory inv = new Inventory(giftCards);

        Cache cache = new Cache();
        cache.add(inv);

        assertTrue(cache.getfInv().size() == 1);

        assertTrue(cache.getResults() == null);

        cache.browseAll();

        assertTrue(cache.getResults().size()==inv.getSize());
        */
 /*   }

    public void testBrowseCategory(){

        ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>(4);
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 10));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 0));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 2));
        giftCards.add(new GiftCard(10, "TestMerchant", 1, 0, 2));

        Inventory inv = new Inventory(giftCards);

        Cache cache = new Cache();
        cache.add(inv);

        cache.loadItems();
        cache.browseCategory(2);

        assertTrue(cache.itemsSize() == 4);
        assertTrue(cache.resultsSize() == 2);
        assertTrue(cache.getResults().get(0).getCategory()==2);
        assertTrue(cache.getResults().get(1).getCategory()==2);
        */
/*    }


}*/

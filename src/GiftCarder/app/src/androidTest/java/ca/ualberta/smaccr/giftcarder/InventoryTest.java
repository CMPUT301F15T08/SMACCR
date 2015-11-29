package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/4/15.
 */
public class InventoryTest extends android.test.ActivityInstrumentationTestCase2 {

    public InventoryTest(){
        super(Inventory.class);
    }

    public void testAddCard() throws Exception {
        User user = new User();
        GiftCard gc  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        user.getInv().addGiftCard(gc);

        assertTrue(user.getInv().getSize() == 1);
    }

    public void testRemoveCard() throws Exception {
        User user = new User();
        GiftCard gc  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);
        GiftCard gc2 = new GiftCard(10, "Cineplex", 1, 3, 9, "I might keep these", Boolean.FALSE);
        user.getInv().addGiftCard(gc);
        user.getInv().addGiftCard(gc2);

        assertTrue(user.getInv().getSize() == 2);
        user.getInv().deleteGiftCard(0);
        // Since new cards are inserted at the beginning of the inventory, deleting gc at inv[0]
        // would delete cineplex card, leaving starbucks.
        assertTrue(user.getInv().getInvList().get(0) == gc);
    }
}

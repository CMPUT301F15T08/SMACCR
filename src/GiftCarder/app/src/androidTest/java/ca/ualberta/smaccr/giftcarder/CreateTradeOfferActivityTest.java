package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/**
 * Created by ali on 15-12-05.
 */
public class CreateTradeOfferActivityTest extends ActivityInstrumentationTestCase2 {

    public CreateTradeOfferActivityTest() {
        super(ItemActivity.class);
    }

    public void testCreateTrade() {
        User owner = new User();
        Inventory ownerInv = new Inventory();
        ownerInv.addGiftCard(new GiftCard("owner", 1.0, "ownerMerchant", 1, 1, 1, "", true));
        ownerInv.addGiftCard(new GiftCard("owner", 1.0, "ownerMerchant2", 1, 1, 1, "", true));
        owner.setUsername("owner");
        owner.setInv(ownerInv);
        owner.setEmail("a@a.c");

        User borrower = new User();
        Inventory borrowerInv = new Inventory();
        borrowerInv.addGiftCard(new GiftCard("borrower", 1.0, "borrowerMerchant", 1, 1, 1, "", true));
        borrower.setUsername("borrower");
        borrower.setInv(borrowerInv);
        borrower.setEmail("b@bc");

        Trade trade = new Trade(owner.getUsername(), borrower.getUsername(), owner.getEmail(), borrower.getEmail(), owner.getInv(), borrower.getInv().getGiftCard(0));
        owner.getTradesList().put("1", trade);
        borrower.getTradesList().put("1", trade);

        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }


}

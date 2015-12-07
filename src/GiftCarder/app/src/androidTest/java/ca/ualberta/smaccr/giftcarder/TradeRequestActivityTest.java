package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by ali on 15-12-05.
 */
public class TradeRequestActivityTest extends ActivityInstrumentationTestCase2 {

    public TradeRequestActivityTest() {
        super(TradeRequestActivity.class);
    }

    // Test UC 4.3
    public void testAcceptTrade() {
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

        owner.getTradesList().get("1").setStatus(Trade.ACCEPTED);
        borrower.getTradesList().get("1").setStatus(Trade.ACCEPTED);

        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }

    // Test UC 4.3
    public void testDeclineTrade() {
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

        owner.getTradesList().get("1").setStatus(Trade.DECLINED);
        borrower.getTradesList().get("1").setStatus(Trade.DECLINED);

        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }

    // Test UC 4.3
    public void testCounterTrade() {
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


        owner.getTradesList().get("1").setStatus(Trade.IN_PROGRESS);
        borrower.getTradesList().get("1").setStatus(Trade.IN_PROGRESS);

        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }

    // Test UC 4.4
    public void testEditTrade() {
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

        // edit trade
        ownerInv.addGiftCard(new GiftCard("owner", 1.0, "ownerMerchant3", 1, 1, 1, "", true));
        trade.setOwnerItems(ownerInv);

        owner.getTradesList().get("1").setStatus(Trade.IN_PROGRESS);
        borrower.getTradesList().get("1").setStatus(Trade.IN_PROGRESS);



        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());

    }

    // Test UC 4.4
    public void testDeleteTrade() {
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

        // delete trade
        owner.getTradesList().remove("1");
        borrower.getTradesList().remove("1");

        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }

    // Test UC 4.1
    public void testTradesHistory() {
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

        // trades exist in the lists so they can be browsed later
        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());

    }

    // Test UC 4.1 and 4.5
    public void testTradeCompleted() {
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


        // trade can be set to completed
        owner.getTradesList().get("1").setStatus(Trade.COMPLETED);
        borrower.getTradesList().get("1").setStatus(Trade.COMPLETED);



        assertEquals(owner.getTradesList().toString(), borrower.getTradesList().toString());
    }


}

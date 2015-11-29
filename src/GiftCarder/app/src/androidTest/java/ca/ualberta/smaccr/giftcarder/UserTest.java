package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by Carin on 10/25/2015.
 */
public class UserTest extends android.test.ActivityInstrumentationTestCase2{

    public UserTest(){
        super(ca.ualberta.smaccr.giftcarder.User.class);
    }

    public void testSetUsername() throws Exception {
        User user = new User();
        user.setUsername("Link");
        assertTrue(user.getUsername().equals("Link"));
    }

    public void testSetCity() throws Exception {
        User user = new User();
        user.setCity("Skyloft");
        assertTrue(user.getCity().equals("Skyloft"));
    }

    public void testSetPhone() throws Exception {
        User user = new User();
        user.setPhone("555-555-5555");
        assertTrue(user.getPhone().equals("555-555-5555"));
    }

    public void testSetEmail() throws Exception {
        User user = new User();
        user.setEmail("hero@hyrule.com");
        assertTrue(user.getEmail().equals("hero@hyrule.com"));
    }

    public void testSetFriendList() throws Exception {
        User user = new User();
        FriendList friendList = new FriendList();
        friendList.addNewFriend("Zelda");

        user.setFl(friendList);
        assertTrue(user.getFl().getFriendList().get(0).equals("Zelda"));
    }

    public void testSetInventory() throws Exception {
        User user = new User();
        Inventory inv = new Inventory();
        GiftCard giftCard = new GiftCard("Link", 50, "Beedle's Air Shop", 1, 1, 10, "Pristine", true);
        inv.addGiftCard(giftCard);

        user.setInv(inv);
        assertTrue(user.getInv().getInvList().get(0).getValue() == 50);
    }

    public void testIsOwner() throws Exception {
        User user = new User();
        Inventory inv = new Inventory();
        GiftCard giftCard = new GiftCard("Link", 50, "Beedle's Air Shop", 1, 1, 10, "Pristine", true);
        inv.addGiftCard(giftCard);
        user.setInv(inv);

        assertTrue(user.isOwner(giftCard));
    }
}

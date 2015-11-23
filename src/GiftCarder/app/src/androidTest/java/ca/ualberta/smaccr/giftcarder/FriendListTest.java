package ca.ualberta.smaccr.giftcarder;

import junit.framework.TestCase;

/**
 * Created by rdhe on 11/22/15.
 */
public class FriendListTest extends TestCase {

    public void testAddNewFriend() throws Exception {
        FriendList fl = new FriendList();

        fl.addNewFriend("myfriend");
        assertTrue(fl.containsFriend("myfriend"));
    }

    public void testDeleteOldFriendName() throws Exception {
        FriendList fl = new FriendList();

        fl.addNewFriend("myfriend");
        fl.deleteOldFriendName("myfriend");
        assertFalse(fl.containsFriend("myfriend"));
    }

    public void testDeleteOldFriendIndex() throws Exception {
        FriendList fl = new FriendList();

        fl.addNewFriend("myfriend");
        fl.deleteOldFriendIndex(0);
        assertFalse(fl.containsFriend("myfriend"));
    }

    public void testContainsFriend() throws Exception {
        FriendList fl = new FriendList();

        fl.addNewFriend("myfriend");
        boolean bool = fl.containsFriend("myfriend");
        assertTrue(bool);
    }
}
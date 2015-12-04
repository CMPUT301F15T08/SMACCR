package ca.ualberta.smaccr.giftcarder;


import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;

public class TopTraderTest extends ActivityInstrumentationTestCase2 {

    public TopTraderTest() {
        super(ca.ualberta.smaccr.giftcarder.InventoryActivity.class);
    }

    /**
     * Tests that InventoryActivity starts
     */
    public void testStart() throws Exception {
        InventoryActivity activity = (InventoryActivity) getActivity();
    }

    // UC 12.1
    public void testOneTopTrader() throws Exception {

        //test users

        //Bob with 1 trade
        User bob = new User();
        bob.setUsername("bob");
        bob.setTradeCount(1);

        //Joe with 2 trades
        User joe = new User();
        joe.setUsername("joe");
        joe.setTradeCount(2);


        //Sue no trades
        User sue = new User();
        sue.setUsername("sue");
        sue.setTradeCount(0);


        FriendList fl = new FriendList();

        fl.addNewFriend(bob.getUsername());
        fl.addNewFriend(joe.getUsername());
        fl.addNewFriend(sue.getUsername());


        //Create an arraylist of our test users
        ArrayList<User> ul = new ArrayList<User>();

        ul.add(bob);
        ul.add(joe);
        ul.add(sue);

        Cache mycache = new Cache();
        mycache.setFriends(ul);

        CalcTopTrader ctt = new CalcTopTrader();

        //This generates new friend list with top trader indicated in user's username, which displayed only in arraylist of strings
        ArrayList<String> topTradeList =  ctt.generateFriendListWithTopTrader(fl, mycache);


        //We know joe is in index 1 and he should be top trader
        assertTrue(topTradeList.get(1) + "is not top trader", topTradeList.get(1).equals("joe *TOP TRADER*"));

    }


    //test when we have two top traders (UC 12.1)
    public void testTwoTopTrader() throws Exception {

        //test users

        //Bob with 1 trade
        User bob = new User();
        bob.setUsername("bob");
        bob.setTradeCount(2);

        //Joe with 2 trades
        User joe = new User();
        joe.setUsername("joe");
        joe.setTradeCount(2);


        //Sue no trades
        User sue = new User();
        sue.setUsername("sue");
        sue.setTradeCount(0);


        FriendList fl = new FriendList();

        fl.addNewFriend(bob.getUsername());
        fl.addNewFriend(joe.getUsername());
        fl.addNewFriend(sue.getUsername());


        //Create an arraylist of our test users
        ArrayList<User> ul = new ArrayList<User>();

        ul.add(bob);
        ul.add(joe);
        ul.add(sue);

        Cache mycache = new Cache();
        mycache.setFriends(ul);

        CalcTopTrader ctt = new CalcTopTrader();

        //This generates new friend list with top trader indicated in user's username, which displayed only in arraylist of strings
        ArrayList<String> topTradeList =  ctt.generateFriendListWithTopTrader(fl, mycache);


        //We know joe is in index 1 and he should be top trader
        assertTrue("Something went wrong", topTradeList.get(1).equals("joe *TOP TRADER*") && topTradeList.get(2).equals("bob *TOP TRADER*"));

    }


    //All friends have same number of trades, so they are all top trader (UC 12.1)
    public void testNoTopTrader() throws Exception {

        //test users

        //Bob with 1 trade
        User bob = new User();
        bob.setUsername("bob");
        bob.setTradeCount(1);

        //Joe with 2 trades
        User joe = new User();
        joe.setUsername("joe");
        joe.setTradeCount(1);


        //Sue no trades
        User sue = new User();
        sue.setUsername("sue");
        sue.setTradeCount(1);


        FriendList fl = new FriendList();

        fl.addNewFriend(bob.getUsername());
        fl.addNewFriend(joe.getUsername());
        fl.addNewFriend(sue.getUsername());


        //Create an arraylist of our test users
        ArrayList<User> ul = new ArrayList<User>();

        ul.add(bob);
        ul.add(joe);
        ul.add(sue);

        Cache mycache = new Cache();
        mycache.setFriends(ul);

        CalcTopTrader ctt = new CalcTopTrader();

        //This generates new friend list with top trader indicated in user's username, which displayed only in arraylist of strings
        ArrayList<String> topTradeList1 =  ctt.generateFriendListWithTopTrader(fl, mycache);

        //We know joe is in index 1 and he should be top trader also with sue and bob
        assertTrue(topTradeList1.get(2) + "is not top trader", topTradeList1.get(2).equals("bob *TOP TRADER*"));
        assertTrue(topTradeList1.get(1) + "is not top trader", topTradeList1.get(1).equals("joe *TOP TRADER*"));
        assertTrue(topTradeList1.get(0) + "is not top trader", topTradeList1.get(0).equals("sue *TOP TRADER*"));


    }


}

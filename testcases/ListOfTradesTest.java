package com.example.richard.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by Richard on 2015-10-08.
 */
public class ListOfTradesTest extends ActivityInstrumentationTestCase2 {

    public ListOfTradesTest(){
        super(com.example.richard.myapplication.MainActivity.class);
    }

    /*
    Test for "BrowseMyTrades"
    we are just checking if user will have past/current trades history, if not we cannot show them their trades, only if they do have trades.
     */
    public void testContainsTrades(){
        ListOfTrades myTrades = new ListOfTrades();

        assertFalse(myTrades.containsTrades());

        myTrades.addTrade("example of a trade for now");

        assertTrue(myTrades.containsTrades());

    }
}
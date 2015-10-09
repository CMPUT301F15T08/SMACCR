package com.example.richard.myapplication;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-08.
 */
public class ListOfTrades {
    //for mock testing purposes the arraylist of strings are suppose to be arraylist of trades but only testing existing trades
    ArrayList<String> myTrades = new ArrayList<String>();

    public void addTrade(String trade){
        myTrades.add(trade);
    }

    public Boolean containsTrades(){
        return myTrades.size() > 0;
    }

}

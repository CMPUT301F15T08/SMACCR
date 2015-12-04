package ca.ualberta.smaccr.giftcarder;


import android.util.Log;

import java.util.ArrayList;

public class CalcTopTrader {


    public ArrayList<String> generateFriendListWithTopTrader(FriendList fl, Cache myCache){

        int topTrades = 0;
        int topTraderIndex = -1;

        int pasttopTrades = -1;


        ArrayList<String> fl2 = new ArrayList<String>(fl.getFriendList());

        ArrayList<Integer> tietradesindex = new ArrayList<Integer>();

        //Find out who as top trades
        for (int i=0; i<fl.getFriendList().size();i++){

            int currentCount = myCache.getUser(fl.getFriendList().get(i)).getTradeCount();//getSuccessfulTradesCount();

            if (topTrades <= currentCount) {
                topTrades = currentCount;

                tietradesindex.add(i);

                if (pasttopTrades < topTrades) {
                    //There is new record so now reset it
                    tietradesindex.clear();
                    tietradesindex.add(i);

                    pasttopTrades = topTrades;
                }

                //pasttopTrades = topTrades;

                topTraderIndex = i;
            }


        }

        Log.d("String", tietradesindex.toString());


        for (int i = 0; i<tietradesindex.size(); i++){
            fl2.set(tietradesindex.get(i), fl2.get(tietradesindex.get(i)) + " *TOP TRADER*");
        }


        return fl2;

    }

}

package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ali on 15-11-22.
 */
public class TradesList extends HashMap<Long, Trade> {

    public TradesList() {
        GiftCard ownerItem = new GiftCard(1.0,"BestBuy", 2, 2, 2,"cooment", true);
        GiftCard borrowerItem = new GiftCard(1.0,"Apple", 2, 2, 2,"cooment", true);

        this.put(Long.valueOf("1"),new Trade("owner", "borrower", ownerItem, borrowerItem, false, new Date()));
    }

}

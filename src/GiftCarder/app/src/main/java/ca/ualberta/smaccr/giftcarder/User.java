/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by Carin on 10/25/2015.
 */

/* User model class holds user personal information, inventory of gift cards, list of friends, and
 * list of trades
 */
public class User {
    private String username;
    private String city;
    private String phone;
    private String email;
    private Inventory inv;
    private FriendList fl;
    private TradesList tradesList;
    private int tradeCount;
    private boolean downloadsEnabled;

    /**
     * Constructor: on creation of User, creates new inventory
     */
    public User() {
        this.inv = new Inventory();
        this.fl = new FriendList();
        this.tradesList = new TradesList();
        this.tradeCount = 0;
        this.downloadsEnabled = true;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    /**
     * Checks to see if user owns gift card
     *
     * @param giftCard GiftCard
     * @return boolean
     */
    public boolean isOwner(GiftCard giftCard) {
        ArrayList<GiftCard> giftCards = getInv().getInvList();
        return giftCards.contains(giftCard);
    }

    /**
     * Checks to see if user has enabled downloads
     * @return boolean
     */
    public boolean isDownloadsEnabled() {
        return this.downloadsEnabled;
    }

    // Getters
    /********************************************************************************************/

    /**
     * Gets username
     *
     * @return String
     */
    public String getUsername() {
        return (String) username;
    }

    /**
     * Gets city
     *
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets phone
     *
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets email
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets inventory
     *
     * @return String
     */
    public Inventory getInv() {
        return this.inv;
    }

    /**
     * Gets FriendList
     *
     * @return FriendList
     */
    public FriendList getFl() {
        return fl;
    }

    /**
     * Gets TradesList
     *
     * @return TradesList
     */
    public TradesList getTradesList() {
        return tradesList;
    }


    // Setters
    /********************************************************************************************/

    /**
     * Sets username
     *
     * @param username String
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * Sets city
     *
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets phone
     *
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets email
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets inventory
     *
     * @param inventory Inventory of user's items
     */
    public void setInv(Inventory inventory) {
        this.inv = inventory;
    }

    /**
     * Sets FriendList
     *
     * @param fl FriendList
     */
    public void setFl(FriendList fl) {
        this.fl = fl;
    }

    /**
     * Sets FriendList
     *
     * @param tradesList TradesList
     */
    public void setTradesList(TradesList tradesList) {
        this.tradesList = tradesList;
    }

    /**
     * gets trade list size
     *
     * @return
     */
    public int getSuccessfulTradesCount() {
        int successfulTradesCount = 0;


        Iterator entries = tradesList.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Trade tradeObj = (Trade) thisEntry.getValue();
            if (!(tradeObj.getStatus().equals("DECLINED"))){
                successfulTradesCount++;
            }
        }
        return successfulTradesCount;
    }


    /**
     * Sets downloadsEnabled boolean
     * @param  downloadsEnabled Boolean
     */
    public void setDownloadsEnabled(Boolean downloadsEnabled) {
        this.downloadsEnabled = downloadsEnabled;
    }
}

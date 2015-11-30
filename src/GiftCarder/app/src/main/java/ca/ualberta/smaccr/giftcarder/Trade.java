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

/**
 * Created by ali on 15-11-25.
 */
public class Trade {

    public static String DECLINED = "Trade Declined";
    public static String IN_PROGRESS = "Trade In Progress";
    public static String COMPLETED = "Trade Completed";
    public static String ACCEPTED = "Trade Accepted";

    private String owner;
    private String borrower;
    private String ownerEmail;
    private String borrowerEmail;
    private Inventory ownerItems;
    private GiftCard borrowerItem;
    private String status;

    public Trade(String owner, String borrower, String ownerEmail, String borrowerEmail, Inventory ownerItems, GiftCard borrowerItem) {
        this.owner = owner;
        this.borrower = borrower;
        this.ownerEmail = ownerEmail;
        this.borrowerEmail = borrowerEmail;
        this.ownerItems = ownerItems;
        this.borrowerItem = borrowerItem;
        this.status = Trade.IN_PROGRESS;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Inventory getOwnerItems() {
        return ownerItems;
    }

    public void setOwnerItems(Inventory ownerItem) {
        this.ownerItems = ownerItems;
    }

    public GiftCard getBorrowerItem() {
        return borrowerItem;
    }

    public void setBorrowerItem(GiftCard borrowerItem) {
        this.borrowerItem = borrowerItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String borrowerEmail) {
        this.ownerEmail = borrowerEmail;
    }


}

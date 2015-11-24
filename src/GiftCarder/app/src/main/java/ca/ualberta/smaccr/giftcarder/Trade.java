package ca.ualberta.smaccr.giftcarder;

import java.util.Date;

/**
 * Created by ali on 15-11-17.
 */
public class Trade {
    private String owner;
    private String borrower;
    private GiftCard ownerItem;
    private GiftCard borrowerItem;
    private boolean status;
    private Date date;

    public Trade(String owner, String borrower, GiftCard ownerItem, GiftCard borrowerItem, boolean status, Date date) {
        this.owner = owner;
        this.borrower = borrower;
        this.ownerItem = ownerItem;
        this.borrowerItem = borrowerItem;
        this.status = status;
        this.date = date;
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

    public GiftCard getOwnerItem() {
        return ownerItem;
    }

    public void setOwnerItem(GiftCard ownerItem) {
        this.ownerItem = ownerItem;
    }

    public GiftCard getBorrowerItem() {
        return borrowerItem;
    }

    public void setBorrowerItem(GiftCard borrowerItem) {
        this.borrowerItem = borrowerItem;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




}

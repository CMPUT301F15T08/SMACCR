package ca.ualberta.smaccr.giftcarder;

/**
 * Created by ali on 15-11-25.
 */
public class Trade {

    private String owner;
    private String borrower;
    private GiftCard ownerItem;
    private GiftCard borrowerItem;

    public Trade(String owner, String borrower, GiftCard ownerItem, GiftCard borrowerItem) {
        this.owner = owner;
        this.borrower = borrower;
        this.ownerItem = ownerItem;
        this.borrowerItem = borrowerItem;
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
}

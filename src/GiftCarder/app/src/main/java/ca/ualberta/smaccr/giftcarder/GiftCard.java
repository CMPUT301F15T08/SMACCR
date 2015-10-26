package ca.ualberta.smaccr.giftcarder;

/**
 * Created by Spencer on 10/25/2015.
 */
public class GiftCard {
    private String merchant;
    private double value;
    private int quantity;
    private int quality;
    private int category;
    private Boolean shared;
    private String comment;

    public GiftCard() {
        this.merchant = "";
        this.value = 0;
        this.quantity = 0;
        this.quality = 0;
        this.category = 0;
        this.shared = false;
        this.comment = "";
    }

    public String getMerchant() {
        return merchant;
    }

    public double getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuality() {
        return quality;
    }

    public int getCategory() {
        return category;
    }

    public Boolean getShared() {
        return shared;
    }

    public String getComment() {
        return comment;
    }

    public void setCard(String merchant, double value, int quantity,
                        int quality, int category, Boolean shared, String comment) {
        this.merchant = merchant;
        this.value = value;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.shared = shared;
        this.comment = comment;
    }
}

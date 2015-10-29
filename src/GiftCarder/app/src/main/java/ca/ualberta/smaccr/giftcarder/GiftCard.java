package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;

/**
 * Created by Richard on 2015-10-24.
 */
public class GiftCard implements Serializable {

    //This includes value as string
    private String merchant = "New GiftCard";
    private int quantity = 0;
    //3 = poor, 2 = okay, 1= good, 0 = excellent
    private int quality;
    // 0 = Food-Beverage, ..., 9 = other
    private int category;
    private String comments = "No comments";
    // 1 = shared, 0 = not shared
    private Boolean shared = Boolean.FALSE;

    public GiftCard() {
    }

    public GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared) {
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.comments = comments;
        this.shared = shared;
    }

    //Getters and Setters
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        if ((category <= 10) && (category >= 0)){
            this.category = category;
        }
        //set to -1 if invalid
        else {
            this.category = -1;
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        if ((quality <= 3) && (quality >= 0)){
            this.quality = category;
        }
        //set to -1 if invalid
        else {
            this.quality = -1;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}

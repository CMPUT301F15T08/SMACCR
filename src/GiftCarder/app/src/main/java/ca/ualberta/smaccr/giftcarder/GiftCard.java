package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;

/**
 * Created by Richard on 2015-10-24.
 */
public class GiftCard implements Serializable {
    //This includes value as string
    private String merchant = "New GiftCard";

    private int quantity;

    //0 = poor, 1 = okay, 2= good, 3 = excellent
    private int quality;

    // 0 = Food-Beverage, ..., 10 = other
    private int category;
    private String comments;

    // 1 = shared, 0 = not shared
    private Boolean shared;


    //Getters and Setters
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
        this.quality = quality;
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
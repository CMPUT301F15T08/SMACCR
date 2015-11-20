package ca.ualberta.smaccr.giftcarder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 2015-10-24.
 */
public class GiftCard implements Serializable {

    // the monetary value of the giftcard
    private double value;

    // Merchant includes only the name of the card
    private String merchant = "";
    private int quantity = 0;
    // 3 = poor, 2 = okay, 1= good, 0 = excellent
    private int quality = 0;
    // 0 = Food-Beverage, ..., 9 = other
    private int category = 0;
    private String comments = "";
    // 1 = shared, 0 = not shared
    private Boolean shared = Boolean.TRUE;

    private ArrayList<ItemImage> itemImagesList = new ArrayList<ItemImage>();

    public GiftCard() {
    }

    public GiftCard(double value, String merchant, int quantity, int quality, int category, String comments, Boolean shared) {
        this.value = value;
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.comments = comments;
        this.shared = shared;
    }

    // Check valid category and quality
    public boolean checkCategory(){
        if ((this.getCategory() >= 0) && (this.getCategory() < 11)) {
            return true;
        }
        return false;
    }

    public boolean checkQuality(int index){
        if ((this.getQuality() >= 0) && (this.getQuality() < 4)) {
            return true;
        }
        return false;
    }

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public void addItemImage(ItemImage itemImage){
        this.itemImagesList.add(0, itemImage);
    }

    public void deleteItemImage(int imageIndex){
        this.itemImagesList.remove(imageIndex);
    }

    public int getSize(){
        return this.itemImagesList.size();
    }

    public ArrayList<ItemImage> getItemImagesList() {
        return this.itemImagesList;
    }

    public void setItemImagesList(ArrayList<ItemImage> itemImagesList) {
        this.itemImagesList = itemImagesList;
    }

}

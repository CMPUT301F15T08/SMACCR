package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by Richard on 2015-10-24.
 */

public class GiftCard implements Serializable {

    private String owner;                   // Owner's name
    private double value;                   // the monetary value of the giftcard
    private String merchant = "";           // Merchant includes only the name of the card
    private int quantity = 0;
    private int quality = 0;                // 3 = poor, 2 = okay, 1= good, 0 = excellent
    private int category = 0;               // 0 = All, ..., 10 = other
    private String comments = "";
    private Boolean shared = Boolean.TRUE;  // 1 = shared, 0 = not shared
    private Date lastModified = new Date(); // For sorting the order in which items display

    private ArrayList<ItemImage> itemImagesList;

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    private String belongsTo;

    public GiftCard() {
        itemImagesList = new ArrayList<ItemImage>();
    }

    public GiftCard(String owner, double value, String merchant, int quantity, int quality, int category, String comments, Boolean shared) {
        this.owner = owner;
        this.value = value;
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.comments = comments;
        this.shared = shared;
    }


    public GiftCard(String owner, double value, String merchant, int quantity, int quality, int category, String comments) {
        this.owner = owner;
        this.value = value;
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.comments = comments;
    }

    public GiftCard(String owner, double value, String merchant, int quantity, int quality, int category, Boolean shared) {
        this.owner = owner;
        this.value = value;
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
        this.shared = shared;
    }

    public GiftCard(String owner, double value, String merchant, int quantity, int quality, int category) {
        this.owner = owner;
        this.value = value;
        this.merchant = merchant;
        this.quantity = quantity;
        this.quality = quality;
        this.category = category;
    }

    // For AllActivity UI Test
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

    public String getCategoryString() {
        switch (this.category) {
            case 1:
                return "Food & Beverage";
            case 2:
                return "Clothing";
            case 3:
                return "Home & Garden";
            case 4:
                return "Electronics";
            case 5:
                return "Department Store";
            case 6:
                return "Services";
            case 7:
                return "Entertainment";
            case 8:
                return "Online Retailers";
            case 9:
                return "Health & Beauty";
            case 10:
                return "Other";
        }
        return ""; // shouldn't execute
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return lastModified;
    }

    public void updateDate(){
        this.lastModified = new Date();
    }

    public int getSize(){
        return this.itemImagesList.size();
    }

    public ArrayList<ItemImage> getItemImagesList() {
        return itemImagesList;
    }

    public void setItemImagesList(ArrayList<ItemImage> itemImagesList) {
        this.itemImagesList = itemImagesList;
    }
}

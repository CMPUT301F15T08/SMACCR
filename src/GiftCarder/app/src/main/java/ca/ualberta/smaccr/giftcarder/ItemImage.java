/* Modified from Nilanchala, retrieved 11/18/15,
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

package ca.ualberta.smaccr.giftcarder;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Carin on 11/19/2015.
 */
public class ItemImage implements Serializable{
    private String bitmapString;
    private boolean featured;

    public ItemImage(String bitmapString) {
        super();
        this.bitmapString = bitmapString;
        this.featured = false;
    }

    public String getBitmapString() {
        return bitmapString;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

}

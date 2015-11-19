/* Modified from Nilanchala, retrieved 11/18/15,
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

package ca.ualberta.smaccr.giftcarder;

import android.graphics.Bitmap;

/**
 * Created by Carin on 11/19/2015.
 */
public class ItemImage {
    private Bitmap image;
    private boolean featured;

    public ItemImage(Bitmap image, String title) {
        super();
        this.image = image;
        this.featured = false;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}

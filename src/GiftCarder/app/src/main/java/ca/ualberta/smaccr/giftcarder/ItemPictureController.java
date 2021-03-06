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

/* Modified from Tejas Jasani, retrieved 11/18/15,
 * http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
 *
 * Encode/decode bitmap
 * Modified from Roman Truba, retrieved 11/20/15,
 * http://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
 *
 * Resize bitmap
 * Modified from Alex, retrieved 11/22/15
 * http://stackoverflow.com/questions/3331527/android-resize-a-large-bitmap-file-to-scaled-output-file/8497703#8497703
 */

package ca.ualberta.smaccr.giftcarder;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Carin on 11/19/2015.
 */

/* ItemPictureController is responsible for processing images for storage */
public class ItemPictureController {
    int maxByteSize = 65536;
    double scale = 0.5; // scale image by 1/2

    /**
     * Resizes bitmap to less than maxByteSize then converts it to a string
     * @param image Bitmap
     * @return String
     */
    public String processImageResult(Bitmap image) {

        // resize image until less than maxByteSize
        while ((image != null) && (image.getByteCount() >= maxByteSize)) {
            // resize Bitmap
            image = Bitmap.createScaledBitmap(image, (int) (scale * image.getWidth()),
                    (int)(scale * image.getHeight()), false);
        }

        // return Bitmap as string
        return encodeToBase64(image);
    }

    /**
     * Converts bitmap to string
     * @param bitmap Bitmap
     * @return String
     */
    public String encodeToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        return Base64.encodeToString(data, Base64.DEFAULT); // returns encoded image as String
    }

    /**
     * Converts string to Bitmap
     * @param input String
     * @return Bitmap
     */
    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * Displays featured image (first image in itemImagesList)
     * @param itemImagesList ArrayList
     */
    public void displayFeaturedImage(ArrayList<ItemImage> itemImagesList, ImageView imageView) {
        String bitmapString = itemImagesList.get(0).getBitmapString();
        imageView.setImageBitmap(decodeBase64(bitmapString));
    }

}

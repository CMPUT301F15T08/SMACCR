/* Encode/decode bitmap
 * Modified from Roman Truba, retrieved 11/20/15,
 * http://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
 */

package ca.ualberta.smaccr.giftcarder;

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

/**
 * Created by Carin on 11/19/2015.
 */
public class ItemPictureController {
    int maxByteSize = 65536;
    double scale = 0.8;

    public String onCaptureImageResult(Intent data) {
        Bitmap image = (Bitmap) data.getExtras().get("data");

        System.out.println("Byte Size: " + image.getByteCount());
        image = resizeBitmap(image);
        System.out.println("Byte Size: " + image.getByteCount());
        /*
        while ((image != null) && (image.getByteCount() >= maxByteSize)) {
            System.out.println("Byte Size: " + image.getByteCount());
            image = resizeBitmap(image);
        }
        */

        return encodeToBase64(image);
    }

    public String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT); // returns encoded image as String
    }

    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double newWidth = (scale * width);
        double newHeight = (scale * height);
        /*
        //Bitmap.createScaledBitmap(bitmap, (int)(newWidth + 0.5d), (int)(newHeight + 0.5d), false);

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();

        // RESIZE THE BITMAP
        matrix.postScale((float)newWidth, (loat) newHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int)(newWidth + 0.5d), (int)(newWidth + 0.5d), matrix, false);
        bitmap.recycle();

        */
        return bitmap;
    }


    @SuppressWarnings("deprecation")
    public void onSelectFromGalleryResult(Intent data) {
        /*
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
        */
    }

}

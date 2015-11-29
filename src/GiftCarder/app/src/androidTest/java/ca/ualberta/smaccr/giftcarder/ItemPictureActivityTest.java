package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by cbli on 11/29/15.
 */
public class ItemPictureActivityTest extends ActivityInstrumentationTestCase2 {

    public ItemPictureActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.ItemPictureActivity.class);
    }

    /**
     * Tests that ItemPictureActivity starts
     */

    public void testStart() throws Exception {
        ItemPictureActivity activity = (ItemPictureActivity) getActivity();
    }

    /**
     * Tests that large images can be resized down to under 65536 bytes (UC 6.1)
     */
    public void testImageResize() throws Exception {
        ItemPictureActivity activity = (ItemPictureActivity) getActivity();
        ItemPictureController ipc = new ItemPictureController();

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.itunescard);
        assertTrue(bitmap.getByteCount() > 65536);

        String bitmapString = ipc.processImageResult(bitmap);
        assertTrue(ipc.decodeBase64(bitmapString).getByteCount() < 65536);
    }

}


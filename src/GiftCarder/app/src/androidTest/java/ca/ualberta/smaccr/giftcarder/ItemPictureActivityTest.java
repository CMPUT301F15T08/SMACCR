package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.content.Intent;
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
     * Tests that ItemPictureActivity and ItemDetailsActiviy starts
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

    /**
     * Tests that images can be added (UC 6.1)
     */
    public void testAddPhoto() throws Exception {
        ItemPictureActivity activity = (ItemPictureActivity) getActivity();
        ItemPictureController ipc = new ItemPictureController();

        GiftCard gcard  = new GiftCard();
        assertTrue(gcard.getSize() == 0);

        ArrayList<ItemImage> itemImagesList = new ArrayList<ItemImage>();
        assertTrue(itemImagesList.size() == 0);

        // Get image
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.itunescard);
        assertTrue(bitmap.getByteCount() > 65536);
        String bitmapString = ipc.processImageResult(bitmap);
        assertTrue(ipc.decodeBase64(bitmapString).getByteCount() < 65536);

        ItemImage itemImage = new ItemImage(bitmapString);
        itemImagesList.add(itemImage);
        assertFalse(itemImagesList.size() == 0);
    }

    /**
     * Tests that images can be viewed (UC 6.2)
     */
    public void testViewPhoto() throws Exception {
        final String EXTRA_BITMAP_STRING = "ca.ualberta.smaccr.giftcarder.BITMAPSTRING";
        final ItemPictureActivity pictureActivity = (ItemPictureActivity) getActivity();
        ItemPictureController ipc = new ItemPictureController();

        GiftCard gcard  = new GiftCard();
        assertTrue(gcard.getSize() == 0);

        ArrayList<ItemImage> itemImagesList = new ArrayList<ItemImage>();
        assertTrue(itemImagesList.size() == 0);

        // Get image
        Bitmap bitmap = BitmapFactory.decodeResource(pictureActivity.getResources(), R.drawable.itunescard);
        assertTrue(bitmap.getByteCount() > 65536);
        String bitmapString = ipc.processImageResult(bitmap);
        assertTrue(ipc.decodeBase64(bitmapString).getByteCount() < 65536);

        final ItemImage itemImage = new ItemImage(bitmapString);
        itemImagesList.add(itemImage);
        assertFalse(itemImagesList.size() == 0);


        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemDetailsActivity.class.getName(),
                        null, false);

        pictureActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(pictureActivity, ItemDetailsActivity.class);
                intent.putExtra(EXTRA_BITMAP_STRING, itemImage.getBitmapString());

                //Start details activity
                pictureActivity.startActivity(intent);
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        ItemDetailsActivity receiverActivity = (ItemDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ItemDetailsActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    /**
     * Tests that images can be deleted (UC 6.3)
     */
    public void testDeletePhoto() throws Exception {
        ItemPictureActivity activity = (ItemPictureActivity) getActivity();
        ItemPictureController ipc = new ItemPictureController();

        GiftCard gcard  = new GiftCard();
        assertTrue(gcard.getSize() == 0);

        ArrayList<ItemImage> itemImagesList = new ArrayList<ItemImage>();
        assertTrue(itemImagesList.size() == 0);

        // Get image
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.itunescard);
        assertTrue(bitmap.getByteCount() > 65536);
        String bitmapString = ipc.processImageResult(bitmap);
        assertTrue(ipc.decodeBase64(bitmapString).getByteCount() < 65536);

        ItemImage itemImage = new ItemImage(bitmapString);
        itemImagesList.add(itemImage);
        assertFalse(itemImagesList.size() == 0);

        // Remove image
        itemImagesList.remove(0);
        assertTrue(itemImagesList.size() == 0);
    }

}


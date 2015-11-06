package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by splant on 11/5/15.
 */
public class InventoryActivityTest extends ActivityInstrumentationTestCase2 {

    public InventoryActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.InventoryActivity.class);
    }

    /*
     *  Test that InventoryActivity starts
     */
    public void testStart() throws Exception {
        InventoryActivity activity = (InventoryActivity) getActivity();
    }

    /*
     * Test that clicking the Inventory Details button starts the InvDetailsActivity
     */
    public void testInvDetButton() {
        InventoryActivity activity = (InventoryActivity) getActivity();

        final Button invDetButton = (Button) activity.findViewById(R.id.invDetailsButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                invDetButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        RegisterActivity receiverActivity = (RegisterActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                RegisterActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    /*
     * Test that clicking the Inventory Details button starts the InvDetailsActivity
     */
    public void testAddGiftCardButton() {
        InventoryActivity activity = (InventoryActivity) getActivity();

        final Button addGCButton = (Button) activity.findViewById(R.id.addGiftCardButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                addGCButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        RegisterActivity receiverActivity = (RegisterActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                RegisterActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    public void testInventoryClick() {
        InventoryActivity activity = (InventoryActivity) getActivity();

        User user = new User();
        Inventory inv = new Inventory();
        GiftCard gc  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);

        inv.addGiftCard(gc);
        user.setInv(inv);

        activity.updateInvList(inv);
        final ListView lv = (ListView) activity.findViewById(R.id.inventoryListViewID);

        // Set up an ActivityMonitor
        // Code from: https://developer.android.com/training/activity-testing/activity-functional-testing.html
        // Date: 2015-10-16
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemActivity.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = lv.getChildAt(0);
                lv.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        ItemActivity receiverActivity = (ItemActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ItemActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }

    public void testInventoryLongClick() {
        InventoryActivity activity = (InventoryActivity) getActivity();

        User user = new User();
        Inventory inv = new Inventory();
        GiftCard gc  = new GiftCard(10, "Starbucks", 2, 1, 1, "Will get you half a coffee", Boolean.TRUE);

        inv.addGiftCard(gc);
        user.setInv(inv);

        activity.updateInvList(inv);
        final ListView lv = (ListView) activity.findViewById(R.id.inventoryListViewID);

        // Set up an ActivityMonitor
        // Code from: https://developer.android.com/training/activity-testing/activity-functional-testing.html
        // Date: 2015-10-16
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ItemActivity.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = lv.getChildAt(0);
                v.performLongClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // assertTrue(Pop up menu created)
        // popup.click(delete)
        // assertTrue(inv.getSize() == 0)
    }
}
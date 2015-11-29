package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by cbli on 11/29/15.
 */
public class InventoryActivityTest extends ActivityInstrumentationTestCase2 {

    public InventoryActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.InventoryActivity.class);
    }

    /**
     * Tests that InventoryActivity starts
     */

    public void testStart() throws Exception {
        InventoryActivity activity = (InventoryActivity) getActivity();
    }


    /**
     * Tests that friend's profile can be viewed (UC 2.5)
     */

    public void testFriendProfile() throws Exception {
        InventoryActivity activity = (InventoryActivity) getActivity();
        final Button friendProfileButton = (Button) activity.findViewById(R.id.friendProfileButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(UserProfileActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                friendProfileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        UserProfileActivity receiverActivity = (UserProfileActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                UserProfileActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    /**
     * Tests that friend's inventory details can be viewed
     */

    public void testInvDetails() throws Exception {
        InventoryActivity activity = (InventoryActivity) getActivity();
        final Button getInvDetailsButton = (Button) activity.findViewById(R.id.invDetailsButtonInventory);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(InvDetailsActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                getInvDetailsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InvDetailsActivity receiverActivity = (InvDetailsActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                InvDetailsActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

}

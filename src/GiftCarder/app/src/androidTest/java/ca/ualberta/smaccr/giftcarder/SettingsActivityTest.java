package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by ConnorSheremeta on 2015-11-06.
 */
public class SettingsActivityTest extends ActivityInstrumentationTestCase2 {

    public SettingsActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.SettingsActivity.class);
    }

    /**
     * Tests that SettingsActivity starts
     */
    public void testStart() throws Exception {
        SettingsActivity activity = (SettingsActivity) getActivity();
    }


    /**
     * Tests the log out button in settings
     */
    public void testLogOutButton() {
        SettingsActivity activity = (SettingsActivity) getActivity();
        final Button logoutButton = (Button) activity.findViewById(R.id.logoutButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(MainActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                logoutButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        MainActivity receiverActivity = (MainActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MainActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    /**
     * Tests disabling downloads checkbox
     * Needs "friend" to exist on server (with a giftcard with images)
     */
    public void testPhotosNotDownloaded() throws Exception {
        SettingsActivity activity = (SettingsActivity) getActivity();
        ItemPictureController ipc = new ItemPictureController();

        UserRegistrationController urc = new UserRegistrationController();
        User user = new User();
        user.setUsername("user");
        FriendList fl = new FriendList();
        fl.addNewFriend("friend");
        user.setFl(fl);
        urc.addUser(user);
        user.setDownloadsEnabled(true); // default: enable downloads
        urc.editUser("user", user);

        assertTrue(user.isDownloadsEnabled()); // downloads enabled

        // Need users for tests: "user" and "friend"
        Cache cache = new Cache(activity, "user");
        cache.updateFriends();
        User friendUser = cache.getUser("friend");
        Inventory friendInv = friendUser.getInv();

        assertTrue(friendInv.getGiftCard(0).getItemImagesList().size() == 0); // no images downloaded
    }
}

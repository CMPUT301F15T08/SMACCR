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
     * Tests the log out button in settings
     */
    public void testRegisterButton() {
        MainActivity activity = (MainActivity) getActivity();
        final Button logOutButton = (Button) activity.findViewById(R.id.logOutButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                logOutButton.performClick();
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
}

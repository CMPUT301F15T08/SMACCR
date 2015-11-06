/* Android Developers, https://developer.android.com/training/activity-testing/activity-
 * functional-testing.html, retrieved 11/04/15
 */

package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Carin on 11/4/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {
    private EditText etUsername;

    public MainActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.MainActivity.class);
    }

    /**
     * Tests that MainActivity starts
     */
    public void testStart() throws Exception {
        MainActivity activity = (MainActivity) getActivity();
    }

    /**
     * Tests that clicking the register button starts the RegisterActivity
     */
    public void testRegisterButton() {
        MainActivity activity = (MainActivity) getActivity();
        final Button registerButton = (Button) activity.findViewById(R.id.registerButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                registerButton.performClick();
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

    /**
     * Tests that entering the username of a registered user starts the InventoryActivity
     */
    public void testLoginRegisteredUser() {
        MainActivity activity = (MainActivity) getActivity();
        final Button loginButton = (Button) activity.findViewById(R.id.loginButton);
        etUsername = activity.getEtUsername();

        // Creates user
        User user = new User();
        user.addUsername("Link");
        user.addCity("Skyloft");
        user.addPhone("555-555-5555");
        user.addEmail("hero@hyrule.com");

        UserRegistrationController urc = new UserRegistrationController();
        urc.getUserList().addUser(user);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etUsername.setText("Link");
            }
        });
        getInstrumentation().waitForIdleSync();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                loginButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        InventoryActivity receiverActivity = (InventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();

    }

}

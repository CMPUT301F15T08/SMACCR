package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by cbli on 11/6/15.
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2 {
    private EditText etUsername;
    private EditText etCity;
    private EditText etPhone;
    private EditText etEmail;

    public RegisterActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.RegisterActivity.class);
    }

    /**
     * Tests that RegisterActivity starts
     */
    public void testStart() throws Exception {
        RegisterActivity activity = (RegisterActivity) getActivity();
    }

    /**
     * Tests that clicking the Sign Up button without filling in all fields does not start
     * the AllActivity
     */
    public void testSignUpButtonWithEmptyFields() {
        RegisterActivity activity = (RegisterActivity) getActivity();
        final Button signUpButton = (Button) activity.findViewById(R.id.signUpButton);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                signUpButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity did not start
        AllActivity receiverActivity = (AllActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNull("ReceiverActivity is not null", receiverActivity);

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }

    /**
     * Tests that clicking the Sign Up button with valid input starts the AllActivity
     */
    public void testSignUpButtonWithValidFields() {
        RegisterActivity activity = (RegisterActivity) getActivity();
        final Button signUpButton = (Button) activity.findViewById(R.id.signUpButton);

        etUsername = activity.getEtUsername();
        etCity = activity.getEtCity();
        etPhone = activity.getEtPhone();
        etEmail = activity.getEtEmail();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AllActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                etUsername.setText("Link");
                etCity.setText("Skyloft");
                etPhone.setText("555-555-5555");
                etEmail.setText("hero@hyrule.com");
                signUpButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity has started
        AllActivity receiverActivity = (AllActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AllActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }


}

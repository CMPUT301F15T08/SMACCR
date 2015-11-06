package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by cbli on 11/6/15.
 */
public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private EditText etUsername;
    private EditText etCity;
    private EditText etPhone;
    private EditText etEmail;

    public UserProfileActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.UserProfileActivity.class);
    }

    /**
     * Tests that UserProfileActivity starts
     */
    public void testStart() throws Exception {
        UserProfileActivity activity = (UserProfileActivity) getActivity();
    }

    /**
     * Tests editing user profile
     */
    public void testEditUserProfile() {
        UserProfileActivity activity = (UserProfileActivity) getActivity();
        final Button saveButton = (Button) activity.findViewById(R.id.saveButton);
        etUsername = activity.getEtUsername();
        etCity = activity.getEtCity();
        etPhone = activity.getEtPhone();
        etEmail = activity.getEtEmail();

        // Creates user
        User user = new User();
        user.setUsername("Link");
        user.setCity("Skyloft");
        user.setPhone("555-555-5555");
        user.setEmail("hero@hyrule.com");

        final UserRegistrationController urc = new UserRegistrationController();
        urc.clearUsers();
        urc.getUserList().addUser(user);


        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                User user = urc.getUser("Link");
                saveButton.setVisibility(View.VISIBLE);

                etUsername.setText("Link");
                String username = "Link";
                etCity.setText("Skyloft");
                etPhone.setText("555-555-5555");
                etEmail.setText("hero@hyrule.com");

                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

    }

}

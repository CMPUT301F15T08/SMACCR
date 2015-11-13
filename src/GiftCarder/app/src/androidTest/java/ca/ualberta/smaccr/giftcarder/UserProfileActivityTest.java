package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by cbli on 11/6/15.
 */
public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
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
        etCity = activity.getEtCity();
        etPhone = activity.getEtPhone();
        etEmail = activity.getEtEmail();
        String username = "Link";
        final UserRegistrationController urc = new UserRegistrationController();

        // Creates user
        User user = new User();
        user.setUsername(username);
        user.setCity("Skyloft");
        user.setPhone("555-555-5555");
        user.setEmail("hero@hyrule.com");
        urc.getUserList().addUser(user);

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(RegisterActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                etCity.setText("Skyloft");
                etPhone.setText("555-555-5556");
                etEmail.setText("hero@hyrule.com");
            }
        });
        getInstrumentation().waitForIdleSync();

        if (urc.validateEditedFields(etCity, etPhone, etEmail)) {
            urc.editUser(username, etCity, etPhone, etEmail);
        }

        assertTrue(urc.getUser("Link").getCity().equals("Skyloft"));
        assertTrue(urc.getUser("Link").getPhone().equals("555-555-5556"));
    }

}

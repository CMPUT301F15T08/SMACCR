/* Hussain Akhtar Wahid 'Ghouri',
 * http://stackoverflow.com/questions/19452269/android-set-text-to-textview, retrieved 11/02/15
 *
 * Android Developers, http://developer.android.com/training/basics/firstapp/starting-activity.html,
 * retrieved 11/02/2015
 *
 * MSI, http://stackoverflow.com/questions/4127725/how-can-i-remove-a-button-or-make-it-
 * invisible-in-android, retrieved 11/02/15
 *
 * Sean Android, http://stackoverflow.com/questions/4297763/disabling-of-edittext-in-android,
 * retrieved 11/02/15
 */

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    private EditText etCity;
    private EditText etPhone;
    private EditText etEmail;
    private String username;
    private UserRegistrationController urc = new UserRegistrationController();
    private UserProfileController upc = new UserProfileController();
    private int profileState;
    private Button multiButton;
    private Button saveButton;

    //getters for UI testing
    public EditText getEtCity() {return (EditText) findViewById(R.id.cityTextView);}
    public EditText getEtPhone() {return (EditText) findViewById(R.id.phoneTextView);}
    public EditText getEtEmail() {return (EditText) findViewById(R.id.emailTextView);}

    // Constants
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public static final int OWNER_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_STATE = 1; // edit own profile (has save button)
    public static final int STRANGER_STATE = 2; // send friend request to stranger (has send friend request button)
    public static final int FRIEND_STATE = 3; // view friend's profile (no button)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView tvUsername = (TextView) findViewById(R.id.usernameTextView);
        etCity = (EditText) findViewById(R.id.cityTextView);
        etPhone = (EditText) findViewById(R.id.phoneTextView);
        etEmail = (EditText) findViewById(R.id.emailTextView);
        multiButton = (Button) findViewById(R.id.multiButton);
        saveButton = (Button) findViewById(R.id.saveProfileButton);

        Intent intent = getIntent();
        username = intent.getStringExtra(RegisterActivity.EXTRA_USERNAME);
        profileState = (int) getIntent().getIntExtra(EXTRA_STATE, OWNER_STATE);

        User user = urc.getUser(username);

        // Set text fields
        if (username != null) {
            tvUsername.setText(username);
            etCity.setText(user.getCity());
            etPhone.setText(user.getPhone());
            etEmail.setText(user.getEmail());
            upc.setViewMode(profileState, etCity, etPhone, etEmail, multiButton, saveButton);
        }
    }

    public void onMultiButtonClick(View view){

        // Edit button -> owner state changes to edit state
        if (profileState == OWNER_STATE) {
            upc.setViewMode(EDIT_STATE, etCity, etPhone, etEmail, multiButton, saveButton);

        // if user clicks Send Friend Request button
        }else if (profileState == STRANGER_STATE) {

            Toast.makeText(this, "Friend request sent", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void onSaveButtonClick(View view) {
        // valid input
        if (urc.validateEditedFields(etCity, etPhone, etEmail)) {
            urc.editUser(username, etCity, etPhone, etEmail);
            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();
            upc.setViewMode(OWNER_STATE, etCity, etPhone, etEmail, multiButton, saveButton);
        }

        // invalid input
        else
            Toast.makeText(this, "Form contains error", Toast.LENGTH_LONG).show();
    }

}

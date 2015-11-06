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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends ActionBarActivity {

    private EditText etCity;
    private EditText etPhone;
    private EditText etEmail;
    private String username;
    private UserRegistrationController urc = new UserRegistrationController();
    private MenuItem item;

    //getters for UI testing
    public EditText getEtUsername() {return (EditText) findViewById(R.id.usernameTextView);}
    public EditText getEtCity() {return (EditText) findViewById(R.id.cityTextView);}
    public EditText getEtPhone() {return (EditText) findViewById(R.id.phoneTextView);}
    public EditText getEtEmail() {return (EditText) findViewById(R.id.emailTextView);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        EditText etUsername = (EditText) findViewById(R.id.usernameTextView);
        etCity = (EditText) findViewById(R.id.cityTextView);
        etPhone = (EditText) findViewById(R.id.phoneTextView);
        etEmail = (EditText) findViewById(R.id.emailTextView);

        Intent intent = getIntent();
        username = intent.getStringExtra(RegisterActivity.EXTRA_USERNAME);

        User user = urc.getUser(username);

        // Set text fields
        if (!(user == null)) {
            etUsername.setText(username);
            etCity.setText(user.getCity());
            etPhone.setText(user.getPhone());
            etEmail.setText(user.getEmail());

            // Disable editing
            etUsername.setFocusable(false);
            etCity.setFocusable(false);
            etPhone.setFocusable(false);
            etEmail.setFocusable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile) {
            // Make menu (edit option) invisible
            this.item = item;
            this.item.setVisible(false);

            User user = urc.getUser(username);

            Button saveButton = (Button)findViewById(R.id.saveButton);
            saveButton.setVisibility(View.VISIBLE);

            // Make editable
            etCity.setFocusableInTouchMode(true);
            etPhone.setFocusableInTouchMode(true);
            etEmail.setFocusableInTouchMode(true);

            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveButtonClick(View view){

        if (urc.validateEditedFields(etCity, etPhone, etEmail)) {

            urc.editUser(username, etCity, etPhone, etEmail);

            etCity.setFocusable(false);
            etPhone.setFocusable(false);
            etEmail.setFocusable(false);

            // Make menu (edit option) visible
            this.item.setVisible(true);

            Button saveButton = (Button)findViewById(R.id.saveButton);
            saveButton.setVisibility(View.INVISIBLE);

            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Form contains error", Toast.LENGTH_LONG).show();
    }
}

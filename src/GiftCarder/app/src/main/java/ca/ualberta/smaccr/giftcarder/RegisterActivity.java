/* References:
 *
 * Email regular expression:
 * Lokesh Gupta, http://howtodoinjava.com/2014/11/11/java-regex-validate-email-address/, retrieved
 * 26/10/15
 *
 * Phone regular expression:
 * Steven Smith, http://regexlib.com/Search.aspx?k=phone+number, retrieved 26/10/15
 *
 */

package ca.ualberta.smaccr.giftcarder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {

    protected EditText etUsername = (EditText) findViewById(R.id.registerUsername);
    protected EditText etCity = (EditText) findViewById(R.id.registerCity);
    protected EditText etPhone = (EditText) findViewById(R.id.registerPhone);
    protected EditText etEmail = (EditText) findViewById(R.id.registerEmail);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public void registerUser() {
        UserRegistrationController urc = new UserRegistrationController();

        if (validateFields())
            urc.addUser(username, city, phone, email);
        else
            Toast.makeText(RegisterActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
    }

    public boolean validateFields() {
        boolean valid = true;

        if (!Validation.hasText(etUsername)) {
            valid = false;
        }

        if (!Validation.hasText(etCity)) {
            valid = false;
        }

        if (!Validation.hasText(etPhone)) {
            valid = false;
        }

        if (!Validation.hasText(etEmailAddress)) {
            valid = false;
        }

        if (!Validation.isPhoneNumber(etPhone)) {
            valid = false;
        }

        if (!Validation.isEmailAddress(etEmail)) {
            valid = false;
        }

        return valid;
    }
}

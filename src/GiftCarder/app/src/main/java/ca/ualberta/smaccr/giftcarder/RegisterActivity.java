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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";

    //getters for UI testing
    public EditText getEtUsername() {return (EditText) findViewById(R.id.registerUsername);}
    public EditText getEtCity() {return (EditText) findViewById(R.id.registerCity);}
    public EditText getEtPhone() {return (EditText) findViewById(R.id.registerPhone);}
    public EditText getEtEmail() {return (EditText) findViewById(R.id.registerEmail);}

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

    /**
     * Called when user presses Sign Up button.
     * <p>
     * Checks to see if user-entered fields match required format.  Checks to make sure username
     * is not already taken.  If not, it creates a new user account and opens the user's inventory.
     *
     * @param  view  view that is clicked
     */
    public void signUpUser(View view) {
        EditText etUsername = (EditText) findViewById(R.id.registerUsername);
        EditText etCity = (EditText) findViewById(R.id.registerCity);
        EditText etPhone = (EditText) findViewById(R.id.registerPhone);
        EditText etEmail = (EditText) findViewById(R.id.registerEmail);

        UserRegistrationController urc = new UserRegistrationController(this);

        if (urc.validateFields(etUsername, etCity, etPhone, etEmail)) {
            Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_LONG).show();

            urc.addUser(etUsername,etCity, etPhone, etEmail);

            Intent intent = new Intent(this, AllActivity.class);
            String username = etUsername.getText().toString();
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        }
        else
            Toast.makeText(RegisterActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
    }

}

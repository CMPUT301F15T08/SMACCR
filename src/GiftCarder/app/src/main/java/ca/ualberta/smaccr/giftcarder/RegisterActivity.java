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

import android.content.Intent;
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

        if (urc.validateFields(etUsername, etCity, etPhone, etEmail)) {
            urc.addUser(etUsername, etCity, etPhone, etEmail);
            Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(this, Inventory.class);
            //startActivity(intent);
        }
        else
            Toast.makeText(RegisterActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
    }

}

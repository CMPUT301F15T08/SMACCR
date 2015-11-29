/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

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

/* RegisterActivity is responsible for adding the user to the server and to the singleton */
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

        // Try to register user. If no internet, toast message
        if (!NetworkChecker.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection.  Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
        } else {
            if (urc.validateFields(etUsername, etCity, etPhone, etEmail)) {
                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_LONG).show();

                //Add the new user to singleton
                urc.addUser(etUsername,etCity, etPhone, etEmail);

                //Start "all activity"
                Toast.makeText(getApplicationContext(), "Tip: Long click to delete gift card or friend", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AllActivity.class);
                String username = etUsername.getText().toString();
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        }

    }

}

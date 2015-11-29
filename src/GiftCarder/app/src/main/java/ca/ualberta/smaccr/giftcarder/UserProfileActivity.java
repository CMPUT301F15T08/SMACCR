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

/* UserProfileActivity displays user profile based on retrieved profileState from other activities
 * Allows editing of own user profile
 */

/* References:
 * Hussain Akhtar Wahid 'Ghouri',
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
    private String friendUsername;
    private UserRegistrationController urc = new UserRegistrationController();
    private UserProfileController upc = new UserProfileController();
    private int profileState;

    private EditText etCity;
    private EditText etPhone;
    private EditText etEmail;
    private String username;
    private Button editButton;
    private Button saveButton;

    //getters for UI testing
    public EditText getEtCity() {return (EditText) findViewById(R.id.cityTextView);}
    public EditText getEtPhone() {return (EditText) findViewById(R.id.phoneTextView);}
    public EditText getEtEmail() {return (EditText) findViewById(R.id.emailTextView);}

    // Constants
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public static final int OWNER_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_STATE = 1; // edit own profile (has save button)
    public static final int FRIEND_STATE = 3; // view friend's profile (no button)

    private UserListController ulc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Get UI references
        TextView tvUsername = (TextView) findViewById(R.id.usernameTextView);
        etCity = (EditText) findViewById(R.id.cityTextView);
        etPhone = (EditText) findViewById(R.id.phoneTextView);
        etEmail = (EditText) findViewById(R.id.emailTextView);
        editButton = (Button) findViewById(R.id.editButton);
        saveButton = (Button) findViewById(R.id.saveProfileButton);

        // Retrieve data from other activities
        Intent intent = getIntent();
        username = intent.getStringExtra(EXTRA_USERNAME);
        friendUsername = intent.getStringExtra("FRIENDUSERNAME");
        profileState = (int) getIntent().getIntExtra(EXTRA_STATE, OWNER_STATE);

        // First check if showing friend's profile
        // If not, show current logged-in user's profile
        if (friendUsername != null) {
            Cache cache = new Cache(this, username);
            User cacheFriend = cache.getUser(friendUsername);

            // Set text fields
            tvUsername.setText(cacheFriend.getUsername());
            etCity.setText(cacheFriend.getCity());
            etPhone.setText(cacheFriend.getPhone());
            etEmail.setText(cacheFriend.getEmail());
        } else if (username != null) {
            User user = urc.getUser(username);

            // Set text fields
            tvUsername.setText(username);
            etCity.setText(user.getCity());
            etPhone.setText(user.getPhone());
            etEmail.setText(user.getEmail());

        }
        upc.setViewMode(profileState, etCity, etPhone, etEmail, editButton, saveButton);

    }

    /**
     * Upon Edit Button click, displays profile view mode as Edit State
     *
     * @param  view View
     */
    public void onEditButtonClick(View view){
        upc.setViewMode(EDIT_STATE, etCity, etPhone, etEmail, editButton, saveButton);
    }


    /**
     * Upon Save Button click, validates EditText fields
     * If validated, edits user in singletondis and displays profile view mode as Owner State
     *
     * @param  view View
     */
    public void onSaveButtonClick(View view) {
        // valid input
        if (urc.validateEditedFields(etCity, etPhone, etEmail)) {
            urc.editUser(username, etCity, etPhone, etEmail);
            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();
            upc.setViewMode(OWNER_STATE, etCity, etPhone, etEmail, editButton, saveButton);
            updateUserOnServer();
        }

        // invalid input
        else
            Toast.makeText(this, "Form contains error", Toast.LENGTH_LONG).show();
    }

    /**updateUserOnServer
     *This function updates the entire user on server
     */
    public void updateUserOnServer (){
        ulc = new UserListController(urc.getUserList());
        Thread thread = new updateThread2(urc.getUser(username));
        thread.start();

    }

    // Deletes user on server, and write back new modified user
    class updateThread2 extends Thread {
        private User userthread;
        // UserRegistrationController uc= new UserRegistrationController();

        public updateThread2(User user) {
            this.userthread = user;
        }

        @Override
        public void run() {
            // delete from server
            ulc.deleteUser(userthread.getUsername());
            // delete from userlist
            // uc.getUserList().deleteUser(user);

            // Add the new one back
            ulc.addUser(userthread);
            // uc.getUserList().addUser(user);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

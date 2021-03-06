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

package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

/*
Settings Activity contains the option to log out, and see logged in user's profile
 */
public class SettingsActivity extends ActionBarActivity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    private String username = null;
    private Inventory inv;
    private Boolean downloadsEnabled;
    private UserRegistrationController urc;
    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        urc = new UserRegistrationController();
        Intent intent = getIntent();
        username = intent.getStringExtra(AllActivity.EXTRA_USERNAME);

        if (username != null) {
            downloadsEnabled = urc.getUser(username).isDownloadsEnabled();
            cache = new Cache(this, username);

            TextView tvLoggedInAs = (TextView) findViewById(R.id.loggedInAsTextView);
            final CheckBox checkBox = ( CheckBox ) findViewById( R.id.downloadCheckBox );
            checkBox.setChecked(downloadsEnabled);

            tvLoggedInAs.setText("Logged in as: " + username);

            // Update user when checkbox is changed
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    User editedUser = urc.getUser(username);
                    editedUser.setDownloadsEnabled(isChecked);
                    urc.editUser(username, editedUser);

                    // only update if checking the box
                    if (isChecked) {
                        if (!NetworkChecker.isNetworkAvailable(SettingsActivity.this)) {
                            Toast.makeText(SettingsActivity.this, "Warning: no internet connection currently.", Toast.LENGTH_LONG).show();
                        } else {
                            cache.updateFriends();
                        }

                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when user presses Logout button.
     * <p>
     * Exits to MainActivity
     *
     * @param  view  view that is clicked
     */
    public void onLogoutButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Called when user presses Edit Profile button.
     * <p>
     * Syncs everything with server
     *
     * @param  view  view that is clicked
     */
    public void onEditProfileButtonClick(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    /**
     * Called when user presses Sync All button
     * <p>
     * Brings up UserProfileActivity
     *
     * @param  view  view that is clicked
     */
    public void onSyncAllButtonClick(View view) {
        if (!NetworkChecker.isNetworkAvailable(SettingsActivity.this)) {
            Toast.makeText(SettingsActivity.this, "No internet connection.  Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
        } else {
            cache.updateFriends();

            // update user's inventory
            User editedUser = urc.getUser(username);
            urc.editUser(username, editedUser);
            Toast.makeText(SettingsActivity.this, "Sync complete.", Toast.LENGTH_SHORT).show();
        }
    }

}

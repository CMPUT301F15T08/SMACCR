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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class InvDetailsActivity extends Activity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    private InvDetailsController idc;
    private String username = null;
    private String friendUsername;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_details);
        setTitle("Inventory Details");

        // Get user using the app
        Intent intent = getIntent();
        /*
        try {
            username = intent.getStringExtra(InventoryActivity.EXTRA_USERNAME);
        } catch (Exception e) {
            username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        }
        */

        username = intent.getStringExtra(EXTRA_USERNAME);
        friendUsername = intent.getStringExtra("FRIENDUSERNAME");

        // First check if we are showing friend profile. If not, we show currently logged in user's profile
        if (friendUsername != null) {
            Cache cache = new Cache(this, username);
            User cacheFriend = cache.getUser(friendUsername);
            this.idc = new InvDetailsController(cacheFriend, cacheFriend.getInv(), this);
        } else if (username != null){
            UserRegistrationController urc = new UserRegistrationController();
            this.user = urc.getUser(username);
            // Create Controller with the user and this activity
            this.idc = new InvDetailsController(user, user.getInv(), this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((username != null) || (friendUsername != null)){
            idc.updateDetails();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_details, menu);
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
}

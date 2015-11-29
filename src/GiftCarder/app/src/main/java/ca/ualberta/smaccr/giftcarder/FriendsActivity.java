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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class FriendsActivity extends ActionBarActivity {

    // Constants
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public static final int OWNER_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_STATE = 1; // edit own profile (has save button)
    public static final int STRANGER_STATE = 2; // send friend request to stranger (has send friend request button)
    public static final int FRIEND_STATE = 3; // view friend's profile (no button)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Enter their username:");

            //textbox for user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    // Check if its a valid user, and send request
                    UserRegistrationController URC = new UserRegistrationController();
                    if (URC.checkForUser(value)){
                        Log.d("y","y");
                        Intent intent1 = new Intent(FriendsActivity.this, UserProfileActivity.class);
                        intent1.putExtra(EXTRA_STATE, STRANGER_STATE);
                        startActivity(intent1);
                    }
                    else{
                        Log.d("n","n");
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    return;
                }
            });

            alert.show();
            return true;
        }

        if (id == R.id.action_delete) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

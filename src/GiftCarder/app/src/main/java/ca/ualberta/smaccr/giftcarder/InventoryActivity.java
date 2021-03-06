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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/* InventoryActivity handles viewing of friend's inventories (public items only) */
public class InventoryActivity extends Activity {

    // Constants
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";

    public static final int FRIEND_ITEM_STATE = 4; // view other's item
    public static final int FRIEND_PROFILE_STATE = 3; // view friend's profile (no button)

    protected String username;
    protected String friendUsername;
    private Inventory ownerInv;
    protected Inventory inv;

    private Inventory selectedItems;

    public Inventory getOwnerInv() {
        return ownerInv;
    }

    public void setOwnerInv(Inventory ownerInv) {
        this.ownerInv = ownerInv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ListView inventorylistID = (ListView) findViewById(R.id.friendInventoryListViewID);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsernameInventory);

        final Intent intent = getIntent();
        UserRegistrationController urc = new UserRegistrationController();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        // User user = urc.getUser(username);
        friendUsername = intent.getStringExtra("FRIENDUSERNAME");
        Toast.makeText(getApplicationContext(), friendUsername, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), friendUsername, Toast.LENGTH_SHORT).show();

        selectedItems = new Inventory();

        if (username != null) {
            ownerInv = urc.getUser(username).getInv();
            Cache cache = new Cache(this, username);

            try {
                User friendUser = cache.getUser(friendUsername);
                inv = friendUser.getInv();

                urc = new UserRegistrationController();
                User user = urc.getUser(username);
                setOwnerInv(user.getInv());

            } catch (NullPointerException e) {
                inv = ownerInv;
                Log.e("1", "nulllllllllll");
            }
            updateInvList(inv);
            tvUsername.setText(friendUsername);

            inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (getIntent().getBooleanExtra("PICKING_ITEMS", false)) {
                        System.out.println("PICKING ITEMS");
                        TextView textView = (TextView) view.findViewById(R.id.invListTitleTextView);
                        if (selectedItems.getInvList().contains(inv.getGiftCard(position))) {
                            selectedItems.removeGiftCard(inv.getGiftCard(position));
                            textView.setTextColor(Color.BLACK);
                        }else {
                            selectedItems.addGiftCard(inv.getGiftCard(position));
                            textView.setTextColor(Color.GREEN);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("SELECTED_ITEMS", selectedItems);
                        setResult(RESULT_OK, intent);
                    }else {

                        // Switch to item activity and send inventory and position of gift card to change
                        Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
                        // intent.putExtra("GiftCard", inv.getInvList().get(position));
                        intent.putExtra("position", position);
                        intent.putExtra("inventory", inv);
                        intent.putExtra("ownerInventory", ownerInv);
                        intent.putExtra("gc", inv.getGiftCard(position));
                        intent.putExtra(EXTRA_USERNAME, username);
                        intent.putExtra(EXTRA_STATE, FRIEND_ITEM_STATE); // view item
                        // startActivity(intent);
                        startActivityForResult(intent, 1);
                    }
                }
            });

            updateInvList(inv);
        }
    }

    @Override
    public void onBackPressed() {
        //setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
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

    /*
    Retrieved Oct 28 2015
    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
     */

    /**
     * updateInvList
     * update the display of the inventory listview
     * @param inv
     * return
     */
    public void updateInvList(Inventory inv) {
        // Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = inv.getInvList();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){

            DecimalFormat df = new DecimalFormat("#.00");
            GiftCardNames.add("$ "+df.format(tempArray.get(index).getValue()) + " " + tempArray.get(index).getMerchant());
        }

        // Display list of names of giftcards
        ListView inventorylistID = (ListView) findViewById(R.id.friendInventoryListViewID);
        FriendInvListAdapter customAdapter = new FriendInvListAdapter(this, R.layout.adapter_inv_list, tempArray);
        // displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(customAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ownerInv = (Inventory) data.getSerializableExtra("ClonedInventory");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ClonedInventory", ownerInv);
                setResult(RESULT_OK, returnIntent);

            }
        }
    }

    public void getUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("FRIENDUSERNAME", friendUsername);
        intent.putExtra(EXTRA_STATE, FRIEND_PROFILE_STATE);
        startActivity(intent);
    }

    public void inventoryDetailsButton(View view) {
        Intent intent = new Intent(this, InvDetailsActivity.class);
        //intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra("FRIENDUSERNAME", friendUsername);
        startActivity(intent);
    }
}

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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateTradeOfferActivity extends ActionBarActivity {

    private String TRADE_OWNER;

    private UserRegistrationController userRegistrationController;
    private UserListController userListController;
    private ESUserManager esUserManager;

    private User tradeOwner;
    private User tradeBorrower;
    private GiftCard tradeOwnerItem;
    private GiftCard tradeBorrowerItem;

    private Inventory selectedItems;

    private TextView ownerTextView;
    private TextView borrowerTextView;
    private TextView borrowerItemTextView;
    private Button selectItemsButton;
    private Button makeOfferButton;


    /**
     +     * onCreate
     +     * Creates a new Trade request
     +     * @param savedInstanceState
     +     * return
     +     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade_offer);

        userRegistrationController = new UserRegistrationController();
        userListController = new UserListController(userRegistrationController.getUserList());
        esUserManager = new ESUserManager("");

        ownerTextView = (TextView) findViewById(R.id.activity_create_trade_offer_username1);
        borrowerTextView = (TextView) findViewById(R.id.activity_create_trade_offer_username2);
        borrowerItemTextView = (TextView) findViewById(R.id.activity_create_trade_offer_itemName2);
        selectItemsButton = (Button) findViewById(R.id.activity_create_trade_offer_selectItemsButton);
        makeOfferButton = (Button) findViewById(R.id.activity_create_trade_offer_makeOfferButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tradeOwner = userRegistrationController.getUser(extras.getString("TRADE_OWNER"));
            tradeBorrowerItem = (GiftCard) getIntent().getSerializableExtra("TRADE_BORROWER_ITEM");

            ownerTextView.setText(tradeOwner.getUsername());
            borrowerTextView.setText(tradeBorrowerItem.getBelongsTo());
            borrowerItemTextView.setText(tradeBorrowerItem.getMerchant());

            selectItemsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateTradeOfferActivity.this, InventoryActivity.class);
                    intent.putExtra(AllActivity.EXTRA_STATE, AllActivity.FRIEND_PROFILE_STATE);
                    intent.putExtra(AllActivity.EXTRA_USERNAME, tradeOwner.getUsername());
                    intent.putExtra("FRIENDUSERNAME", tradeOwner.getUsername());
                    intent.putExtra("PICKING_ITEMS", true);

                    startActivityForResult(intent, 1);
                }
            });



        }

        makeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                long  tradeId = rand.nextInt(128);
                if (selectedItems.getInvList().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select item(s) from your inventory", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new updateThread(tradeOwner.getUsername(), tradeBorrowerItem.getBelongsTo(), selectedItems, tradeBorrowerItem);
                    thread.start();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                System.out.println("MADE IT HERE");
                selectedItems = (Inventory) data.getSerializableExtra("SELECTED_ITEMS");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_trade_offer, menu);
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

    //Deletes user on server, and write back new modified user
    class updateThread extends Thread {
        private String ownerUsername;
        private String borrowerUsername;
        private Inventory ownerItems;
        private GiftCard borrowerItem;


        public updateThread(String ownerUsername, String borrowerUsername, Inventory ownerItems, GiftCard borrowerItem) {
            this.ownerUsername = ownerUsername;
            this.borrowerUsername = borrowerUsername;
            this.ownerItems = ownerItems;
            this.borrowerItem = borrowerItem;
        }

        @Override
        public void run() {
            User owner = tradeOwner;
            User borrower = esUserManager.getUser(borrowerUsername);

            owner.getTradesList().put("a", new Trade(owner.getUsername(), borrower.getUsername(),owner.getEmail(), borrower.getEmail(), ownerItems, borrowerItem));
            borrower.getTradesList().put("a", new Trade(owner.getUsername(), borrower.getUsername(),owner.getEmail(), borrower.getEmail(), ownerItems, borrowerItem));

            userRegistrationController.editUserTradeList(owner.getUsername(), owner.getTradesList());
            userRegistrationController.editUserTradeList(borrower.getUsername(), borrower.getTradesList());


            userListController.addUser(owner);
            userListController.addUser(borrower);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
                setResult(RESULT_OK);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

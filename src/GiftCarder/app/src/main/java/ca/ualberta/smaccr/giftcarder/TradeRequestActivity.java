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
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class TradeRequestActivity extends ActionBarActivity {
    private ESUserManager esUserManager;
    private Button acceptTradeButton;
    private Button declineTradeButton;
    private Button counterTradeButton;

    private String tradeId;
    private UserRegistrationController userRegistrationController;
    private UserListController userListController;
    private Trade trade;
    private User owner;

    private TextView ownerTextView;
    private TextView borrowerTextView;
    private TextView ownerItemTextView;
    private TextView borrowerItemTextView;


    /**
     * +     * onCreate
     * +     * Responses to a trade offer
     * +     * @param savedInstanceState
     * +     * return
     * +
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);

        acceptTradeButton = (Button) findViewById(R.id.activity_trade_request_acceptTradeButton);
        declineTradeButton = (Button) findViewById(R.id.activity_trade_request_declineTradeButton);
        counterTradeButton = (Button) findViewById(R.id.activity_trade_request_counterTradeButton);
        ownerTextView = (TextView) findViewById(R.id.activity_trade_request_username1);
        borrowerTextView = (TextView) findViewById(R.id.activity_trade_request_username2);
        ownerItemTextView = (TextView) findViewById(R.id.activity_trade_request_itemName1);
        borrowerItemTextView = (TextView) findViewById(R.id.activity_trade_request_itemName2);

        userRegistrationController = new UserRegistrationController();
        userListController = new UserListController(userRegistrationController.getUserList());
        esUserManager = new ESUserManager("");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tradeId = extras.getString("TRADE_ID");
            owner = userRegistrationController.getUser(extras.getString("CURRENT_USERNAME"));
            trade = owner.getTradesList().get(tradeId);

            ownerTextView.setText(trade.getOwner());
            ownerItemTextView.setText(trade.getOwnerItem().getMerchant());
            borrowerTextView.setText(trade.getBorrower());
            borrowerItemTextView.setText(trade.getBorrowerItem().getMerchant());

        }



        if (trade.getStatus().equals(Trade.COMPLETED) || trade.getStatus().equals(Trade.COMPLETED)){
            acceptTradeButton.setVisibility(View.INVISIBLE);
            declineTradeButton.setVisibility(View.INVISIBLE);
            counterTradeButton.setVisibility(View.INVISIBLE);
        }
        else if (trade.getStatus().equals(Trade.IN_PROGRESS)){
            acceptTradeButton.setVisibility(View.VISIBLE);
            declineTradeButton.setVisibility(View.VISIBLE);
            counterTradeButton.setVisibility(View.VISIBLE);

        }

        if (owner.getUsername().equals(trade.getOwner())) {
            acceptTradeButton.setVisibility(View.INVISIBLE);
            counterTradeButton.setVisibility(View.INVISIBLE);
        }

        acceptTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeRequestActivity.this, AcceptTradeActivity.class);
                intent.putExtra("TRADE_ID", tradeId);
                intent.putExtra("CURRENT_USERNAME", owner.getUsername());
                startActivityForResult(intent, 1);
            }
        });

        declineTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new updateThread(tradeId);
                thread.start();

            }
        });

        counterTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeRequestActivity.this, CreateTradeOfferActivity.class);
                intent.putExtra("TRADE_OWNER", trade.getBorrower());
                intent.putExtra("TRADE_BORROWER_ITEM", trade.getOwnerItem());
                startActivityForResult(intent, 2);

            }
        });



    }



    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("ModifiedInventory", (Inventory) data.getSerializableExtra("ModifiedInventory"));
                setResult(RESULT_OK, intent);
                finish();

            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_request, menu);
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

    class updateThread extends Thread {
        private String tradeId;


        public updateThread(String tradeId) {
            this.tradeId = tradeId;
        }

        @Override
        public void run() {
            User owner = esUserManager.getUser(trade.getOwner());
            User borrower = esUserManager.getUser(trade.getBorrower());

            owner.getTradesList().get(tradeId).setStatus(Trade.DECLINED);
            borrower.getTradesList().get(tradeId).setStatus(Trade.DECLINED);

            userRegistrationController.editUserTradeList(owner.getUsername(), owner.getTradesList());
            userRegistrationController.editUserTradeList(borrower.getUsername(), borrower.getTradesList());

            userListController.addUser(owner);
            userListController.addUser(borrower);

            // Give some time to get updated info
            try {
                Thread.sleep(200);
                setResult(RESULT_CANCELED);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

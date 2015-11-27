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

    private List<String> ownersInventoryListItems;

    private TextView ownerTextView;
    private TextView borrowerTextView;
    private TextView borrowerItemTextView;
    private Spinner spinner;
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
        ownersInventoryListItems = new ArrayList<>();

        ownerTextView = (TextView) findViewById(R.id.activity_create_trade_offer_username1);
        borrowerTextView = (TextView) findViewById(R.id.activity_create_trade_offer_username2);
        borrowerItemTextView = (TextView) findViewById(R.id.activity_create_trade_offer_itemName2);
        spinner = (Spinner) findViewById(R.id.activity_create_trade_offer_spinner);
        makeOfferButton = (Button) findViewById(R.id.activity_create_trade_offer_makeOfferButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tradeOwner = userRegistrationController.getUser(extras.getString("TRADE_OWNER"));
            tradeBorrowerItem = (GiftCard) getIntent().getSerializableExtra("TRADE_BORROWER_ITEM");

            ownerTextView.setText(tradeOwner.getUsername());
            borrowerTextView.setText(tradeBorrowerItem.getOwner());
            borrowerItemTextView.setText(tradeBorrowerItem.getMerchant());

            for (int i = 0; i < tradeOwner.getInv().getInvList().size(); i++) {
                ownersInventoryListItems.add(tradeOwner.getInv().getInvList().get(i).getMerchant());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_create_trade_offer_spinner_item,R.id.activity_create_trade_offer_item_textView, ownersInventoryListItems);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tradeOwnerItem = tradeOwner.getInv().getGiftCard(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    tradeOwnerItem = null;

                }
            });

        }

        makeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                long  tradeId = rand.nextInt(128);

                Thread thread = new updateThread(tradeOwner.getUsername(), tradeBorrowerItem.getOwner(), tradeOwnerItem, tradeBorrowerItem);
                thread.start();
            }
        });
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
        private GiftCard ownerItem;
        private GiftCard borrowerItem;


        public updateThread(String ownerUsername, String borrowerUsername, GiftCard ownerItem, GiftCard borrowerItem) {
            this.ownerUsername = ownerUsername;
            this.borrowerUsername = borrowerUsername;
            this.ownerItem = ownerItem;
            this.borrowerItem = borrowerItem;
        }

        @Override
        public void run() {
            User owner = tradeOwner;
            User borrower = esUserManager.getUser(borrowerUsername);

            owner.getTradesList().put("a", new Trade(owner.getUsername(), borrower.getUsername(), ownerItem, borrowerItem));
            borrower.getTradesList().put("a", new Trade(owner.getUsername(), borrower.getUsername(), ownerItem, borrowerItem));

            userRegistrationController.editUserTradeList(owner.getUsername(), owner.getTradesList());
            userRegistrationController.editUserTradeList(borrower.getUsername(), borrower.getTradesList());


            userListController.addUser(owner);
            userListController.addUser(borrower);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

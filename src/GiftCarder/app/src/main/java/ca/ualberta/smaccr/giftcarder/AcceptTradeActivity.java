package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AcceptTradeActivity extends ActionBarActivity {

    private UserRegistrationController userRegistrationController;
    private UserListController userListController;
    private ESUserManager esUserManager;
    private User currentUser;
    private Trade trade;

    private String tradeId;
    private String currentUsername;

    private Button sendEmailButton;
    private EditText emailText;

    /**
     +     * onCreate
     +     * Allows user accepting trade to provide meeting arrangements and send an email to both parties.
     +     * @param savedInstanceState
     +     * return
     +     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_trade);

        sendEmailButton = (Button) findViewById(R.id.activity_accept_trade_sendEmailButton);
        emailText = (EditText) findViewById(R.id.activity_accept_trade_emailText);

        userRegistrationController = new UserRegistrationController();
        userListController = new UserListController(userRegistrationController.getUserList());
        esUserManager = new ESUserManager("");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tradeId = extras.getString("TRADE_ID");
            currentUsername = extras.getString("CURRENT_USERNAME");
            currentUser = userRegistrationController.getUser(currentUsername);

            trade = currentUser.getTradesList().get(tradeId);


        }

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new updateThread(trade.getOwner(), trade.getBorrower(), trade.getOwnerItem(), trade.getBorrowerItem());
                thread.start();


                /*String to = "dummy@email.com";
                String subject = "New Trade Offer";
                String message = emailText.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accept_trade, menu);
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
            User owner = esUserManager.getUser(ownerUsername);
            User borrower = esUserManager.getUser(borrowerUsername);

            owner.getInv().addGiftCard(trade.getBorrowerItem());
            borrower.getInv().addGiftCard(trade.getOwnerItem());

            userRegistrationController.editUserInventory(owner.getUsername(), owner.getInv());
            userRegistrationController.editUserInventory(borrower.getUsername(), borrower.getInv());


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

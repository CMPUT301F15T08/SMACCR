package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AcceptTradeActivity extends ActionBarActivity {
    private long TRADE_ID;
    private String CURRENT_USERNAME;
    private User user;
    private Trade trade;

    private UserRegistrationController userRegistrationController;

    private TextView username1;
    private TextView username2;
    private TextView itemname1;
    private TextView itemname2;
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

        username1 = (TextView) findViewById(R.id.username1);
        username2 = (TextView) findViewById(R.id.username2);
        itemname1 = (TextView) findViewById(R.id.itemName1);
        itemname2 = (TextView) findViewById(R.id.itemName2);
        sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        emailText = (EditText) findViewById(R.id.emailText);

        userRegistrationController = new UserRegistrationController();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CURRENT_USERNAME = extras.getString("CURRENT_USERNAME");
            TRADE_ID = extras.getLong("TRADE_ID");

            user = userRegistrationController.getUser(CURRENT_USERNAME);
            trade = user.getTradesList().get(TRADE_ID);

            username1.setText(trade.getOwner());
            username2.setText(trade.getBorrower());
            itemname1.setText(trade.getOwnerItem().getMerchant());
            itemname2.setText(trade.getBorrowerItem().getMerchant());
        }



        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CURRENT_USERNAME.equals(trade.getOwner())){
                    // get borrowers item
                    user.getInv().addGiftCard(trade.getBorrowerItem());
                    userRegistrationController.editUserInventory(CURRENT_USERNAME, user.getInv());
                }
                else if (CURRENT_USERNAME.equals(trade.getBorrower())) {
                    // get owners item
                    user.getInv().addGiftCard(trade.getOwnerItem());
                    userRegistrationController.editUserInventory(CURRENT_USERNAME, user.getInv());
                }

                String to = "dummy@email.com";
                String subject = "New Trade Offer";
                String message = emailText.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
}

package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TradeRequestActivity extends ActionBarActivity {
    private Button acceptTradeButton;

    private String tradeId;
    private UserRegistrationController userRegistrationController;
    private Trade trade;
    private User owner;

    private TextView ownerTextView;
    private TextView borrowerTextView;
    private TextView ownerItemTextView;
    private TextView borrowerItemTextView;


    /**
     +     * onCreate
     +     * Responses to a trade offer
     +     * @param savedInstanceState
     +     * return
     +     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request);

        acceptTradeButton = (Button) findViewById(R.id.activity_trade_request_acceptTradeButton);
        ownerTextView = (TextView) findViewById(R.id.activity_trade_request_username1);
        borrowerTextView = (TextView) findViewById(R.id.activity_trade_request_username2);
        ownerItemTextView = (TextView) findViewById(R.id.activity_trade_request_itemName1);
        borrowerItemTextView = (TextView) findViewById(R.id.activity_trade_request_itemName2);

        userRegistrationController = new UserRegistrationController();

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

        if (owner.getUsername().equals(trade.getOwner())) {
            acceptTradeButton.setVisibility(View.INVISIBLE);
        }

        acceptTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeRequestActivity.this, AcceptTradeActivity.class);
                intent.putExtra("TRADE_ID", tradeId);
                intent.putExtra("CURRENT_USERNAME", owner.getUsername());
                startActivity(intent);
            }
        });

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
}

package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TradeRequestActivity extends ActionBarActivity {
    private long TRADE_ID;
    private String CURRENT_USERNAME;
    private User user;
    private Trade trade;

    private UserRegistrationController userRegistrationController;
    private UserListController userListController;
    private Button acceptTradeButton;
    private Button declineTradeButton;
    private TextView username1;
    private TextView username2;
    private TextView itemname1;
    private TextView itemname2;

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

        Bundle extras = getIntent().getExtras();

        userRegistrationController = new UserRegistrationController();
        userListController = new UserListController(userRegistrationController.getUserList());

        acceptTradeButton = (Button) findViewById(R.id.trade_request_acceptTradeButton);
        declineTradeButton = (Button) findViewById(R.id.trade_request_declineTradeButton);
        username1 = (TextView) findViewById(R.id.trade_request_username1);
        username2 = (TextView) findViewById(R.id.trade_request_username2);
        itemname1 = (TextView) findViewById(R.id.trade_request_itemName1);
        itemname2 = (TextView) findViewById(R.id.trade_request_itemName2);


        if (extras != null) {
            CURRENT_USERNAME = extras.getString("CURRENT_USERNAME");
            user = userRegistrationController.getUser(CURRENT_USERNAME);

            TRADE_ID = extras.getLong("TRADE_ID");
            trade = user.getTradesList().get(TRADE_ID);

            username1.setText(trade.getOwner());
            username2.setText(trade.getBorrower());
            itemname1.setText(trade.getOwnerItem().getMerchant());
            itemname2.setText(trade.getBorrowerItem().getMerchant());
        }

        acceptTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TradeRequestActivity.this, AcceptTradeActivity.class);
                intent.putExtra("TRADE_ID", TRADE_ID);
                intent.putExtra("CURRENT_USERNAME", CURRENT_USERNAME);
                startActivity(intent);
            }
        });

        declineTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setTradesList(new TradesList());
                userRegistrationController.editUserTradeList(CURRENT_USERNAME, user.getTradesList());
                //Thread thread = new updateThread(user);
                //thread.start();


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

    //Deletes user on server, and write back new modified user
    class updateThread extends Thread {
        private User userthread;
        //UserRegistrationController uc= new UserRegistrationController();

        public updateThread(User user) {
            this.userthread = user;
        }

        @Override
        public void run() {
            //delete from server
            userListController.deleteUser(userthread.getUsername());
            //delete from userlist
            //uc.getUserList().deleteUser(user);

            //Add the new one back
            userListController.addUser(userthread);
            //uc.getUserList().addUser(user);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}

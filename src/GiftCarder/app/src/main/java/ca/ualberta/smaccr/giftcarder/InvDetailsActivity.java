package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InvDetailsActivity extends Activity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    private InvDetailsController idc;
    private String username;
    private String friendusername;
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

        friendusername = intent.getStringExtra("FRIENDUSERNAME");


        //First check if we showing friend profile , if not we show current logged in user's profile
        if (friendusername != null) {
            Cache cache = new Cache(this, username);
            User cacheFriend = cache.getUser(friendusername);
            this.idc = new InvDetailsController(cacheFriend, cacheFriend.getInv(), this);
        }
        else{
            UserRegistrationController urc = new UserRegistrationController();
            this.user = urc.getUser(username);
            // Create Controller with the user and this activity
            this.idc = new InvDetailsController(user, user.getInv(), this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        idc.updateDetails();
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

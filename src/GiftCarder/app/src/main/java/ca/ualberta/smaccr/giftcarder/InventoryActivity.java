package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class InventoryActivity extends Activity {

    // Constants
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";

    public static final int BROWSER_ITEM_STATE = 2; // view other's item
    public static final int FRIEND_PROFILE_STATE = 3; // view friend's profile (no button)

    String username;
    Inventory inv;
    ArrayAdapter<String> displayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ListView inventorylistID = (ListView) findViewById(R.id.friendInventoryListViewID);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsernameInventory);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        UserRegistrationController urc = new UserRegistrationController();
        User user = urc.getUser(username);

        inv = user.getInv();
        updateInvList(inv);
        tvUsername.setText(username);

        inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();

                // Switch to item activity and send inventory and position of gift card to change
                Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
                //intent.putExtra("GiftCard", inv.getInvList().get(position));
                intent.putExtra("position", position);
                intent.putExtra("inventory", inv);
               // intent.putExtra("ownerInventory", ownerInv);
                intent.putExtra(EXTRA_STATE, BROWSER_ITEM_STATE); // view item
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        updateInvList(inv);
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
        ArrayList<GiftCard> tempArray = null;

        for (int i=1; i<inv.getSize(); i++) {
            if (inv.getInvList().get(i).getShared()) {
                tempArray.add(inv.getInvList().get(i));
            }
        }

        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){

            DecimalFormat df = new DecimalFormat("#.00");
            GiftCardNames.add("$ "+df.format(tempArray.get(index).getValue()) + " " + tempArray.get(index).getMerchant());
        }

        // Display list of names of giftcards
        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        InvListAdapter customAdapter = new InvListAdapter(this, R.layout.adapter_inv_list, tempArray);
        // displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(customAdapter);
    }

    public void getUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_STATE, FRIEND_PROFILE_STATE);
        startActivity(intent);
    }

    public void inventoryDetailsButton(View view) {
        Intent intent = new Intent(this, InvDetailsActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }
}

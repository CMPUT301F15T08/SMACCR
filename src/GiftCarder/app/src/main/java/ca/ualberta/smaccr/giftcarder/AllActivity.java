package ca.ualberta.smaccr.giftcarder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AllActivity extends ActionBarActivity {

    // Constants
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";

    public static final int ADD_ITEM_STATE = 0; // add item
    public static final int OWNER_ITEM_STATE = 1; // view own item
    public static final int BROWSER_STATE = 2; // view other's item

    public static final int OWNER_PROFILE_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_PROFILE_STATE = 1; // edit own profile (has save button)
    public static final int STRANGER_PROFILE_STATE = 2; // send friend request to stranger (has send friend request button)
    public static final int FRIEND_PROFILE_STATE = 3; // view friend's profile (no button)

    private UserListController ulc;

    String username;
    Inventory inv;
    ArrayAdapter<String> displayAdapter;
    ArrayList<String> friendsList;
    UserRegistrationController urc = new UserRegistrationController();


    /**
     * onCreate
     * Create the inventory activity - initialize tabs and inventory list view with logged-in
     * user's inventory
     * @param savedInstanceState
     * return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        // Manage the tabs between inventory, friends, and trades pages.
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Inventory");
        tabSpec.setContent(R.id.tabInventory);
        // Text on first tab:
        tabSpec.setIndicator("Inventory");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Trades");
        tabSpec.setContent(R.id.tabTrades);
        // Text on second tab:
        tabSpec.setIndicator("Trades");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Friends");
        tabSpec.setContent(R.id.tabFriends);
        // Text on third tab:
        tabSpec.setIndicator("Friends");
        tabHost.addTab(tabSpec);

        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        ListView tradesListView = (ListView) findViewById(R.id.tradesListView);
        final ListView friendsListView = (ListView) findViewById(R.id.friendListView);

        tradesListView.setAdapter(new TradesTabAdapter(this));
        tradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AllActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //Create a new intent and pass in the position of the trade
                // The position should match the index in the database
                // This way the trade offer can be retrieved
                Intent intent = new Intent(AllActivity.this, TradeRequestActivity.class);
                startActivity(intent);
            }
        });
        Toast.makeText(getApplicationContext(), "Long click to delete gift card or friend", Toast.LENGTH_LONG).show();

        //###########################################################################################################################
        //Only modify part of user
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        //UserRegistrationController urc = new UserRegistrationController();
        User user = urc.getUser(username);
        inv = user.getInv();

        friendsList = user.getFriendsList();
        //updateInvList(inv);

        //###########################################################################################################################



        //##################################################################################################################################
        //CLick listeners for FRIENDLIST
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFriend = (String) friendsListView.getItemAtPosition(position);
                Intent intent = new Intent(AllActivity.this, UserProfileActivity.class);
                intent.putExtra(EXTRA_STATE, FRIEND_PROFILE_STATE);
                intent.putExtra(EXTRA_USERNAME, selectedFriend);
                startActivity(intent);
            }
        });

        // Long click to delete listener
        friendsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position;

                Toast.makeText(getApplicationContext(), "Delete " + Integer.toString(position), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder deletedialog = new AlertDialog.Builder(AllActivity.this);
                deletedialog.setMessage("Are you sure?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedFriend = (String) friendsListView.getItemAtPosition(pos);

                        //User use = urc.getUser(username);
                        //use.deleteFriend(selectedFriend);
                        //updateFriendsList(use.getFriendsList());
                        //friendsListView.deferNotifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                deletedialog.create().show();
                return true;
            }
        });

        //END of CLick listeners for Inventory
        //##################################################################################################################################



        //##################################################################################################################################
        //CLick listeners for Inventory
        inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();

                // Switch to item activity and send inventory and position of gift card to change
                Intent intent = new Intent(AllActivity.this, ItemActivity.class);
                //intent.putExtra("GiftCard", inv.getInvList().get(position));
                intent.putExtra("position", position);
                intent.putExtra("inventory", inv);
                intent.putExtra(EXTRA_STATE, OWNER_ITEM_STATE); // view item
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        // Long click to delete listener
        inventorylistID.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                Toast.makeText(getApplicationContext(), "Delete " + Integer.toString(position), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder deletedialog = new AlertDialog.Builder(AllActivity.this);
                deletedialog.setMessage("Are you sure?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // http://stackoverflow.com/questions/7073577/how-to-get-object-from-listview-in-setonitemclicklistener-in-android
                        //Deleting giftcard and updating to server
                        inv.deleteGiftCard(pos);
                        updateInvList(inv);
                        updateUserOnServer();

                        dialog.dismiss();}});
                deletedialog.create().show();
                return true;
            }
        });
        //END of CLick listeners for Inventory
        //##################################################################################################################################

        //###############################################################################################################################################################
        //Calling update functions
        updateInvList(inv);
        updateUserOnServer();

        //###############################################################################################################################################################
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //commented out, might put back in if group wants the three dots

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar ItemActivity clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //Pass the  inventory to settings, so settings activity will send it back to main to update the inventory in singleton
            Intent intent1 = new Intent(AllActivity.this, SettingsActivity.class);
            intent1.putExtra(EXTRA_USERNAME, username);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);

    }

    //added to remove three dots (might remove later)
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    //######################################################################################################
    //ADDING something to user
    /**
     * AddNewGiftCard
     * create a new giftcard and place in inventory, then switch to ItemActivity to edit that giftcard
     * @param menu
     * return
     */
    public void AddNewGiftCard(View menu){

        // Add new giftcard
        GiftCard gc = new GiftCard();
        inv.addGiftCard(gc);

        // Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = inv.getInvList();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){
            GiftCardNames.add(0, tempArray.get(index).getMerchant());
        }

        // Switch to item activity and send selected gift card data
        Intent intent = new Intent(AllActivity.this, ItemActivity.class);
        intent.putExtra("position", 0);
        intent.putExtra("inventory", inv);
        intent.putExtra(EXTRA_STATE, ADD_ITEM_STATE); // add item
        startActivityForResult(intent, 1);
    }

    public void addFriend(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Enter their username:");

        //textbox for user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                // Check if its a valid user, and send request

                if (urc.checkForUser(value)) {
                    //urc.getUser(username).addFriend(username);
                    //updateFriendsList(urc.getUser(username).getFriendsList());
                    Toast.makeText(getApplicationContext(), "Friend request sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
    //ADDING something to user
    //############################################################################################################




    //#############################################################################################################
    //UPDATING part of user HERE!
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
        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(displayAdapter);

        //Updates the user's inventory in userList in UserRegisteration controller
        //UserRegistrationController uc= new UserRegistrationController(this);
        urc.editUserInventory(username, inv);

    }

    public void updateFriendsList(ArrayList<String> friendsList) {
        ArrayAdapter<String> displayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsList);
        ListView friendsListView = (ListView) findViewById(R.id.friendListView);
        friendsListView.setAdapter(displayAdapter1);
    }

    //END OF updating user here
    //#############################################################################################################



    //###############################################################################################################
    // DANGER THIS SERVER STUFF
    public void updateUserOnServer (){
        ulc = new UserListController(urc.getUserList());
        Thread thread = new updateThread(urc.getUser(username));
        thread.start();

    }

    //Deletes user on server, and write back new modified user
    class updateThread extends Thread {
        private User userthread;
        UserRegistrationController uc= new UserRegistrationController();

        public updateThread(User user) {
            this.userthread = user;
        }

        @Override
        public void run() {
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //delete from server
            ulc.deleteUser(userthread.getUsername());
            //delete from userlist
            //uc.getUserList().deleteUser(user);

            //Add the new one back
            ulc.addUser(userthread);
            //uc.getUserList().addUser(user);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        /*
    Retrieved Oct 28 2015
    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
     */

    /**
     * onActivityResult
     * Grab modified inventory back from giftcard, and reset the array adapter
     * @param requestCode, resultCode, data
     * return
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                inv = (Inventory) data.getSerializableExtra("ModifiedInventory");
                updateInvList(inv);
                updateUserOnServer();
            }
        }
    }

    //send of server stuff
    //###############################################################################################################


    @Override
    public void onBackPressed() {
        // Back button disabled
    }


    //###############################################################################################################
    //SWITCHING TO OTHER ACTIVITIES
    public void getUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    public void inventoryDetailsButton(View view) {
        Intent intent = new Intent(this, InvDetailsActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    public void browseClick(MenuItem v) {
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    public void settingsClick(MenuItem v){
        Intent intent1 = new Intent(AllActivity.this, SettingsActivity.class);
        intent1.putExtra(EXTRA_USERNAME, username);
        startActivity(intent1);
    }

    //END OF SWITCHING TO OTHER ACTIVITIES
    //###############################################################################################################

}

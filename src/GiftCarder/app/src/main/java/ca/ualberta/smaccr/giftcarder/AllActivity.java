package ca.ualberta.smaccr.giftcarder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {

    // Constants
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";

    public static final int ADD_STATE = 0; // add item
    public static final int OWNER_STATE = 1; // view own item
    public static final int FRIEND_STATE = 3; // view own item
    //////
    public static final int FRIEND_PROFILE_STATE = 3; // view friend's profile (no button)

    private UserListController ulc;

    String username;
    Inventory inv;
    ArrayAdapter<String> displayAdapter;

    // friendlist contains an arraylist of strings
    FriendList fl;
    protected Cache myCache;

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
        // ActionBar actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF8833")));

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
        // END OF Manage the tabs between inventory, friends, and trades pages.

        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        ListView tradesListView = (ListView) findViewById(R.id.tradesListView);
        final ListView friendsListView = (ListView) findViewById(R.id.friendListView);

        tradesListView.setAdapter(new TradesTabAdapter(this, urc.getUser(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME))));
        tradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(AllActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                // Create a new intent and pass in the position of the trade
                // The position should match the index in the database
                // This way the trade offer can be retrieved
                Intent intent = new Intent(AllActivity.this, TradeRequestActivity.class);
                intent.putExtra("TRADE_ID", Long.toHexString(id));
                intent.putExtra("CURRENT_USERNAME", getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));
                startActivity(intent);
            }
        });
        // Toast.makeText(getApplicationContext(), "Long click to delete gift card or friend", Toast.LENGTH_LONG).show();

        //###########################################################################################################################
        // Only modify part of user
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        // Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        // UserRegistrationController urc = new UserRegistrationController();
        User user = urc.getUser(username);
        inv = user.getInv();

        myCache = new Cache(this, username);
        myCache.updateFriends();

        // FriendList class type
        fl = user.getFl();
        Toast.makeText(getApplicationContext(), "Tip: Long click to delete gift card or friend", Toast.LENGTH_LONG).show();

        //###########################################################################################################################


        //##################################################################################################################################
        // CLick listeners for FRIENDLIST

        // click individual friend, disabled cause we need cache or what to save it, friend stuff


        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedFriend = (String) friendsListView.getItemAtPosition(position);

                Intent intent = new Intent(AllActivity.this, InventoryActivity.class);
                intent.putExtra(EXTRA_STATE, FRIEND_PROFILE_STATE);
                intent.putExtra(EXTRA_USERNAME, username);
                intent.putExtra("FRIENDUSERNAME", selectedFriend);

                startActivityForResult(intent, 1);
            }
        });


        // Long click to delete friend
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

                        fl.deleteOldFriendIndex(pos);
                        updateFriendsList(fl);
                        updateUserOnServer();

                        dialog.dismiss();
                    }
                });
                deletedialog.create().show();
                return true;
            }
        });

        // END of CLick listeners for Friendlist
        //##################################################################################################################################


        //##################################################################################################################################
        // CLick listeners for Inventory

        // Click on individual item
        inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Switch to item activity and send inventory and position of gift card to change
                Intent intent = new Intent(AllActivity.this, ItemActivity.class);
                //intent.putExtra("GiftCard", inv.getInvList().get(position));
                intent.putExtra(EXTRA_USERNAME, username);
                intent.putExtra("position", position);
                intent.putExtra("inventory", inv);
                intent.putExtra(EXTRA_STATE, OWNER_STATE); // view item
                // startActivity(intent);
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
                        // Deleting giftcard and updating to server
                        inv.deleteGiftCard(pos);
                        updateInvList(inv);
                        updateUserOnServer();

                        dialog.dismiss();}});
                deletedialog.create().show();
                return true;
            }
        });
        // END of CLick listeners for Inventory
        //##########################################################################################

        //##########################################################################################
        // Calling update functions
        updateInvList(inv);
        updateFriendsList(fl);
        updateUserOnServer();

        //##########################################################################################
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // commented out, might put back in if group wants the three dots

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar ItemActivity clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            // Pass the  inventory to settings, so settings activity will send it back to main to update the inventory in singleton
            Intent intent1 = new Intent(AllActivity.this, SettingsActivity.class);
            intent1.putExtra(EXTRA_USERNAME, username);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);

    }

    // added to remove three dots (might remove later)
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    //##############################################################################################
    // ADDING something to user
    /**
     * AddNewGiftCard
     * create a new giftcard and place in inventory, then switch to ItemActivity to edit that giftcard
     * @param menu
     * return
     */
    public void AddNewGiftCard(View menu){

        // Add new giftcard
        GiftCard gc = new GiftCard();
        gc.setBelongsTo(username);
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
        intent.putExtra(EXTRA_STATE, ADD_STATE); // add item
        startActivityForResult(intent, 1);
    }

    public void addFriend(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Enter their username:");

        // textbox for user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String friendUserName = input.getText().toString();

                // Start thread to search server for friend
                Thread thread = new GetFriendThread(friendUserName);
                thread.start();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();

    }

    // Parameter for potential Friend on server
    User potentialFriendUser = new User();

    // Check friend is on server or not, if is send friend request, for now add to friendlist
    class GetFriendThread extends Thread {
        private String id;

        public GetFriendThread(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            ESUserManager userManager = new ESUserManager("");

            potentialFriendUser = userManager.getUser(id);


            runOnUiThread(checkUserOnServerFriend);
        }
    }

    private Runnable checkUserOnServerFriend = new Runnable() {
        public void run() {
            checkForUserOnServerFriendList(potentialFriendUser);
        }
    };

    public void checkForUserOnServerFriendList(User user){
        UserRegistrationController urc = new UserRegistrationController(this);

        if (user != null) {
            // Check if friend already on friend list
            if (urc.getUser(username).getFl().containsFriend(user.getUsername())){
                Toast.makeText(this, "User is already on your friend list", Toast.LENGTH_SHORT).show();
                return;
            }

            // If you try to add yourself
            if (potentialFriendUser.getUsername().equals(username)){
                Toast.makeText(this, "You can't be your own friend :P", Toast.LENGTH_SHORT).show();
                return;
            }

            // add user to friend list and update server
            fl.addNewFriend(potentialFriendUser.getUsername());
            updateFriendsList(fl);
            Toast.makeText(getApplicationContext(),  "Friend Request sent to , [added to friendlist for now]", Toast.LENGTH_SHORT).show();

            //!!!!!!!!!!!!!
            // add friend to userList singleton
            urc.addUser(potentialFriendUser);
            myCache.updateFriends();
            //!!!!!!!!!!!!

            // update server
            updateFriendsList(fl);
            updateUserOnServer();


        } else {
            // User does not exist on server
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
        }

    }
    // ADDING something to user
    //##############################################################################################




    //##############################################################################################
    // UPDATING part of user HERE!
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
        InvListAdapter customAdapter = new InvListAdapter(this, R.layout.adapter_inv_list, tempArray);
        // displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(customAdapter);

        // Updates the user's inventory in userList in UserRegisteration controller
        // UserRegistrationController uc= new UserRegistrationController(this);
        urc.editUserInventory(username, inv);

    }

    public void updateFriendsList(FriendList fl) {
        ArrayAdapter<String> displayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fl.getFriendList());
        ListView friendsListView = (ListView) findViewById(R.id.friendListView);
        friendsListView.setAdapter(displayAdapter1);

        urc.editUserFriendList(username, fl);
    }

    // END OF updating user here
    //##############################################################################################



    //##############################################################################################
    // DANGER THIS SERVER STUFF
    public void updateUserOnServer (){
        ulc = new UserListController(urc.getUserList());
        Thread thread = new updateThread(urc.getUser(username));
        thread.start();

    }

    // Deletes user on server, and write back new modified user
    class updateThread extends Thread {
        private User userthread;
        // UserRegistrationController uc= new UserRegistrationController();

        public updateThread(User user) {
            this.userthread = user;
        }

        @Override
        public void run() {
            // delete from server
            ulc.deleteUser(userthread.getUsername());
            // delete from userlist
            // uc.getUserList().deleteUser(user);

            // Add the new one back
            ulc.addUser(userthread);
            // uc.getUserList().addUser(user);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Retrieved Oct 28 2015
     * http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
     */

    /**
     * onActivityResult
     * Grab modified inventory back from giftcard, and reset the array adapter
     * @param requestCode, resultCode, data
     * return
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                inv = (Inventory) data.getSerializableExtra("ModifiedInventory");
                updateInvList(inv);
                updateUserOnServer();
            }
        }if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    // send of server stuff
    //###############################################################################################################



    @Override
    public void onBackPressed() {
        // Asks to exit on back button press
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to exit?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // http://stackoverflow.com/questions/11643224/how-to-exit-android-application-from-exit-button
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }


    //###############################################################################################################
    // SWITCHING TO OTHER ACTIVITIES


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
        startActivityForResult(intent1, 3);
    }

    // END OF SWITCHING TO OTHER ACTIVITIES
    //###############################################################################################################

}

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class BrowseActivity extends ActionBarActivity {


    public static final int ADD_ITEM_STATE = 0; // add item
    public static final int OWNER_ITEM_STATE = 1; // view own item
    public static final int BROWSER_STATE = 2; // view other's item

    public static final int OWNER_PROFILE_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_PROFILE_STATE = 1; // edit own profile (has save button)
    public static final int STRANGER_PROFILE_STATE = 2; // send friend request to stranger (has send friend request button)
    public static final int FRIEND_PROFILE_STATE = 3; // view friend's profile (no button)

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";
    String username;

    private ArrayAdapter<GiftCard> adapter;

    Cache myCache;

    private ListView browseListID;
    private BrowseActivity activity = this;
    private EditText searchBar;
    private Button goButton;
    private Spinner catSpinner;

    public ListView getbrowseListID() {
        return browseListID;
    }
    public Cache getMyCache() {
        return myCache;
    }
    public Button getGoButton() {
        return goButton;
    }
    public BrowseActivity getActivity() {
        return activity;
    }
    public EditText getSearchBar() {
        return searchBar;
    }
    public void setSearchBar(EditText searchBar) {
        this.searchBar = searchBar;
    }
    public Spinner getCatSpinner() {
        return catSpinner;
    }
    public void setCatSpinner(Spinner catSpinner) {
        this.catSpinner = catSpinner;
    }


    @Override//FIXME get rid of the need for putextra
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        Intent intent = getIntent();
        username = intent.getStringExtra(RegisterActivity.EXTRA_USERNAME);
        myCache= new Cache(this, username);

        final GiftCard giftCard1 = new GiftCard(12.34,"Test",1,1,6,"scratched but usable", Boolean.TRUE);

        searchBar = (EditText) findViewById(R.id.searchEditText);
        goButton = (Button) findViewById(R.id.browseGo);
        catSpinner = (Spinner) findViewById(R.id.browseCatSpinner);
        browseListID = (ListView) findViewById(R.id.browseListView);

        ListView browseListView = (ListView) findViewById(R.id.browseListView);

        browseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

// Switch to item activity and send selected gift card data//FIXME FIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXMEFIXME
                Intent intent = new Intent(BrowseActivity.this, ItemActivity.class);
                intent.putExtra("gc", myCache.getResults().get(position));
                intent.putExtra(EXTRA_STATE, BROWSER_STATE); // add item
                startActivityForResult(intent, 1);
            }
        });

        // Long click to delete listener
        browseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AdapterView<?> par = parent;
                final int pos = position;

                AlertDialog.Builder tradeDialog = new AlertDialog.Builder(BrowseActivity.this);
                tradeDialog.setMessage("Do you want to trade for this item?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // http://stackoverflow.com/questions/7073577/how-to-get-object-from-listview-in-setonitemclicklistener-in-android
                        // inv.deleteGiftCard((GiftCard)par.getAdapter().getItem(pos));
                        dialog.dismiss();
                    }
                });
                tradeDialog.create().show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();

        updateBrowseList();
    }

    public void updateBrowseList(){
        // Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = loadFromCache();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>(tempArray.size());
        for (int i = 0; i <tempArray.size(); i++){
            GiftCardNames.add(tempArray.get(i).getMerchant());
        }

        // Display list of names of giftcards
        ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);//FixME format display to more than string
        //new ArrayAdapter<GiftCard>(this, R.layout.list_gc, (List<GiftCard>)myCache.getItems());

        //displayAdapter.getView()// turn GC into item with image and value
        browseListID.setAdapter(displayAdapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        myCache.updateFriends();
        myCache.browseAll();

        updateBrowseList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *loadFromCache
     * load screen full of
     *
     * @return ArrayList<GiftCard>
     */
    public ArrayList<GiftCard> loadFromCache(){                   //FIXME

        return myCache.getResults();
    };

    /**
     * clickItem
     * view the details of the item
     * @param v
     *//*
    public void clickItem(View v){
        Intent intent = new Intent(this, ItemActivity.class);
        intent.p
        startActivity(intent);
    }*/

    public void clickGo(View v){



        int cat = catSpinner.getSelectedItemPosition();

        myCache.browseCategory(cat);

        if (searchBar.getText().length() > 0) {
            myCache.browseSearch(String.valueOf(searchBar.getText()));
        }

        updateBrowseList();

    }
}

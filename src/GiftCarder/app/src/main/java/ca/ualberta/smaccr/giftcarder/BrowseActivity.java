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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class BrowseActivity extends ActionBarActivity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    String username;

    private ArrayAdapter<GiftCard> adapter;
    Cache myCache = new Cache();

    private ListView inventorylistID;
    private BrowseActivity activity = this;
    private EditText searchBar;
    private Button goButton;
    private Spinner catSpinner;

    public ListView getInventorylistID() {
        return inventorylistID;
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

        final GiftCard giftCard1 = new GiftCard(12.34,"Test",1,1,6,"scratched but usable", Boolean.TRUE);
        myCache.add(giftCard1);

        searchBar = (EditText) findViewById(R.id.searchEditText);
        goButton = (Button) findViewById(R.id.browseGo);
        catSpinner = (Spinner) findViewById(R.id.browseCatSpinner);
        inventorylistID = (ListView) findViewById(R.id.browseListView);

        ListView browseListView = (ListView) findViewById(R.id.browseListView);

        browseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Switch to item activity and send selected giftcard data
                Intent intent = new Intent(BrowseActivity.this, ItemActivity.class);
                intent.putExtra("gc", giftCard1);
                startActivity(intent);
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

        // Get ArrayList of Strings to display in Adapter ListView
        LinkedList<GiftCard> tempArray = loadFromCache();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int i = 0; i <tempArray.size(); i++){
            GiftCardNames.add(0, tempArray.get(i).getMerchant());
        }

        // Display list of names of giftcards
        ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);//FixME format display to more than string
        //new ArrayAdapter<GiftCard>(this, R.layout.list_gc, (List<GiftCard>)myCache.getItems());

        //displayAdapter.getView()// turn GC into item with image and value
        inventorylistID.setAdapter(displayAdapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Toast.makeText(getApplicationContext(), "LongClick to propose trade",Toast.LENGTH_SHORT).show();
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

    /**FIXME to allow one friend's inventory
     *loadFromCache
     * load screen full of
     *
     * @return LinkedList<GiftCard>
     */
    public LinkedList<GiftCard> loadFromCache(){                   //FIXME

        return myCache.getItems();
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
}

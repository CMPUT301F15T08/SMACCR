/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class BrowseActivity extends AllActivity {

    public static final int BROWSER_STATE = 2; // view other's item

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE= "ca.ualberta.smaccr.giftcarder.STATE";
    protected String username;
    protected Inventory inv;
    ArrayAdapter<String> displayAdapter;

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

        UserRegistrationController urc = new UserRegistrationController();
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        inv = urc.getUser(username).getInv(); // owner's inventory

        if (super.myCache!=null) {
            myCache = super.myCache;
        } else {
            myCache = new Cache(BrowseActivity.this, username);
        }

        searchBar = (EditText) findViewById(R.id.searchEditText);
        goButton = (Button) findViewById(R.id.browseGo);
        catSpinner = (Spinner) findViewById(R.id.browseCatSpinner);
        browseListID = (ListView) findViewById(R.id.browseListView);

        ListView browseListView = (ListView) findViewById(R.id.browseListView);

        browseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Switch to item activity and send selected gift card data
                Intent intent = new Intent(BrowseActivity.this, ItemActivity.class);
                intent.putExtra("gc", myCache.getResults().get(position));

                ///intent.putExtra(EXTRA_STATE, BROWSER_STATE); // add item
                ///intent.putExtra("USERNAME", username);
                ///startActivity(intent);

                //intent.putExtra("ownerInventory", inv);
                intent.putExtra(EXTRA_USERNAME, username);
                intent.putExtra(EXTRA_STATE, BROWSER_STATE); // browse item
                startActivity(intent);
                //startActivityForResult(intent, 1);
            }
        });

        /*
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
        */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    /*
    @Override
    protected void onResume(){
        super.onResume();

        updateBrowseList();
    }
    */

    public void updateBrowseList(){
        // Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = myCache.getResults();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){

            DecimalFormat df = new DecimalFormat("#.00");
            GiftCardNames.add("$ "+df.format(tempArray.get(index).getValue()) + " " + tempArray.get(index).getMerchant());
        }

        // Display list of names of giftcards
        InvListAdapter customAdapter = new InvListAdapter(this, R.layout.adapter_inv_list, tempArray);
        // displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        browseListID.setAdapter(customAdapter);


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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
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

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This is for when you return from an activity, passing back data
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                inv = (Inventory) data.getSerializableExtra("ClonedInventory");
                Intent intent = new Intent();
                intent.putExtra("ClonedInventory", inv);
                setResult(RESULT_OK, intent);
            }
        }
    }
    */
}

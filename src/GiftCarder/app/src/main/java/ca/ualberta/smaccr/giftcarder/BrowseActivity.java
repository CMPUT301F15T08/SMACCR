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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class BrowseActivity extends Activity {

    private ArrayAdapter<GiftCard> adapter;


    Cache myCache = new Cache();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);


        GiftCard giftCard1 = new GiftCard();
        giftCard1.setMerchant("Bestbuy");
        giftCard1.setQuantity(1);
        giftCard1.setQuality(3);
        giftCard1.setCategory(1);
        giftCard1.setComments("scratched but usable");
        giftCard1.setShared(Boolean.TRUE);
        myCache.add(giftCard1);


        ListView browseListView = (ListView) findViewById(R.id.browseListView);

        browseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Switch to item activity and send selected giftcard data
                Intent intent = new Intent(BrowseActivity.this, ItemActivity.class);
                startActivity(intent);
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
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


        // Add new giftcard
        GiftCard gc = new GiftCard();

        gc.setMerchant("Bestbuy");
        gc.setQuantity(1);
        gc.setQuality(3);
        gc.setCategory(1);
        gc.setComments("scratched but usable");
        gc.setShared(Boolean.TRUE);

        myCache.add(gc);

        // display size
        // Toast.makeText(getApplicationContext(), Integer.toString(myinventory.getSize()),Toast.LENGTH_SHORT).show();

        // Get ArrayList of Strings to display in Adapter ListView
        LinkedList<GiftCard> tempArray = myCache.getItems();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int i = 0; i <tempArray.size(); i++){
            GiftCardNames.add(0, tempArray.get(i).getMerchant());
        }

        // Display list of names of giftcards
        ListView inventorylistID = (ListView) findViewById(R.id.browseListView);
        ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);//FixME format display to more than string
                    //new ArrayAdapter<GiftCard>(this, R.layout.list_gc, (List<GiftCard>)myCache.getItems());

                    //displayAdapter.getView()// turn GC into item with image and value
        inventorylistID.setAdapter(displayAdapter);
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

    public boolean loadFromCache(){                   //FIXME

        return false;
    };

    public void clickItem(View v){
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}

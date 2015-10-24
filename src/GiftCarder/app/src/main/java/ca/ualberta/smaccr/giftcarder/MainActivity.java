package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/*
Richard's branch! Don't touch my branch >:O
 */
public class MainActivity extends ActionBarActivity {

    Inventory myinventory = new Inventory();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);

        inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_SHORT).show();

                //Switch to item activity and send selected giftcard data
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra("GiftCard", myinventory.getMyinventory().get(position));
                startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar ItemActivity clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void notiButtonClick(MenuItem v){
        Intent intent = new Intent(this, NotiFullActivity.class);
        startActivity(intent);
    }

    //OnClick to add GiftCard
    public void AddNewGiftCard(View menu){
        //Add new giftcard
        GiftCard gc = new GiftCard();
        myinventory.addGiftCard(gc);

        //display size
        //Toast.makeText(getApplicationContext(), Integer.toString(myinventory.getSize()),Toast.LENGTH_SHORT).show();

        //Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = myinventory.getMyinventory();
        //Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){
            GiftCardNames.add(0, tempArray.get(index).getMerchant());
        }

        //Display list of names of giftcards
        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(displayAdapter);

    }

}

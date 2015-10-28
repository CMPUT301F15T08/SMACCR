package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class InventoryActivity extends Activity {

    Inventory inv = new Inventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);

        inventorylistID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();

                // Switch to item activity and send selected giftcard data
                Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
                intent.putExtra("GiftCard", inv.getInvList().get(position));
                startActivity(intent);
            }
        });


        // Long click to delete listener
        inventorylistID.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AdapterView<?> par = parent;
                final int pos = position;

                Toast.makeText(getApplicationContext(), "Delete " + Integer.toString(position), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder deletedialog = new AlertDialog.Builder(InventoryActivity.this);
                deletedialog.setMessage("Are you sure?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // http://stackoverflow.com/questions/7073577/how-to-get-object-from-listview-in-setonitemclicklistener-in-android
                        // inv.deleteGiftCard((GiftCard)par.getAdapter().getItem(pos));
                        dialog.dismiss();
                    }
                });
                deletedialog.create().show();
                return true;
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

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void invClick(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    // OnClick to add GiftCard
    public void AddNewGiftCard(View menu){
        // Add new giftcard
        GiftCard gc = new GiftCard();
        inv.addGiftCard(gc);

        // display size
        // Toast.makeText(getApplicationContext(), Integer.toString(inv.getSize()),Toast.LENGTH_SHORT).show();

        // Get ArrayList of Strings to display in Adapter ListView
        ArrayList<GiftCard> tempArray = inv.getInvList();
        // Toast.makeText(getApplicationContext(), Integer.toString(tempArray.size()),Toast.LENGTH_SHORT).show();

        ArrayList<String> GiftCardNames = new ArrayList<String>();
        for (int index = 0; index <tempArray.size(); index++){
            GiftCardNames.add(0, tempArray.get(index).getMerchant());
        }

        // Display list of names of giftcards
        ListView inventorylistID = (ListView) findViewById(R.id.inventoryListViewID);
        ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, GiftCardNames);
        inventorylistID.setAdapter(displayAdapter);

        // Switch to item activity and send selected giftcard data
        Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
        intent.putExtra("GiftCard", inv.getInvList().get(0));
        startActivity(intent);

    }

}

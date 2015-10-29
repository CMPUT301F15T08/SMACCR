package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ItemActivity extends Activity {

    Inventory inv;
    int position;

    ItemController ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //receive inventory and position of gift card to modify
        position = (int)getIntent().getIntExtra("position", 0);
        inv = (Inventory)getIntent().getSerializableExtra("inventory");

        //Get references to UI
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);

        ic.displayGiftCardInfo(inv, position, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
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

    @Override
    public void onBackPressed() {

        //Get references to UI
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);
        //item controller to set the data into inventory
        inv = ic.setGiftCardInfo(inv,position, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);

        //http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
        //send it back to inventory
        Intent intent = new Intent();
        intent.putExtra("ModifiedInventory", inv);
        setResult(RESULT_OK, intent);
        finish();
    }

}

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends Activity {

    Inventory inv;
    int position;

    ItemController ic = new ItemController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //receive inventory and position of gift card to modify
        position = (int)getIntent().getIntExtra("position", 0);
        inv = (Inventory)getIntent().getSerializableExtra("inventory");

        //Get references to UI
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);

        //itemName.setText(inv.getInvList().get(position).getMerchant());
        GiftCard tempcard = inv.getInvList().get(position);
        ic.displayGiftCardInfo(tempcard, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);

        Toast.makeText(getApplicationContext(), "Save Button at Bottom, and return to inventory, backbuton disabled for now till we can delete a giftcard as if user push backbutton it creates giftcard",Toast.LENGTH_LONG).show();




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

    public void saveGiftCardInfo(View menu){
        //Get references to UI
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);
        //item controller to set the data into inventory
        inv = ic.setGiftCardInfo(inv, position, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);


        //http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
        //send the modified inventory back to inventory activity
        Intent intent = new Intent();
        intent.putExtra("ModifiedInventory", inv);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        //Back button disabled for now as if owner clicks back button, empty giftcard is created and pop up once saved giftcard is created
    }


    //https://www.youtube.com/watch?v=pk-80p2ha_Q retrived oct 30 2015
    //barebones right now
    public void takeGiftCardPic(View menu){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //setResult(RESULT_OK, intent);
        if (intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent, 2);
        }

    }
    //https://www.youtube.com/watch?v=pk-80p2ha_Q retrived oct 30 2015
    //Get picture data and put it in photo of giftcard
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK){
                Bundle bundle = new Bundle();
                bundle = data.getExtras();
                Bitmap BMP;
                BMP = (Bitmap)bundle.get("data");
                ImageView giftcardpic = (ImageView)findViewById(R.id.ID_pictureOfGiftCard);
                giftcardpic.setImageBitmap(BMP);

            }
        }
    }

    public void setViewStatus(View menu){

        //UI references
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);
        Button viewstatus = (Button)findViewById(R.id.ID_viewStatus);
        Button offerbutton = (Button)findViewById(R.id.ID_MakeOfferButton);
        Button savebutton = (Button)findViewById(R.id.ID_savegiftcard);

        //Changing the viewing mode, example user view mode can edit, borrower can only see
        ic.setViewMode(itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox, viewstatus, offerbutton, savebutton);

    }


}

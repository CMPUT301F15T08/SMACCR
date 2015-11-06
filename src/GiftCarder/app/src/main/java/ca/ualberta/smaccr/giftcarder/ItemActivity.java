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

    // For UI testing
    private ItemActivity activity = this;
    private EditText itemName;
    private EditText itemValue;
    private EditText quantity;
    private Spinner qualitySpinner;
    private Spinner categorySpinner;
    private EditText comments;
    private CheckBox checkbox;

    public Inventory inv;
    int position;
    GiftCard gc;

    // getters for UI testing
    public EditText getItemName() {return (EditText) findViewById(R.id.ID_item_value);}
    public EditText getItemValue() {return (EditText)findViewById(R.id.ID_item_Name);}
    public EditText getQuantity() {return (EditText)findViewById(R.id.ID_quantity);}
    public Spinner getQualitySpinner() {return (Spinner) findViewById(R.id.ID_qualitySpin);}
    public Spinner getCategorySpinner() {return (Spinner) findViewById(R.id.ID_categorySpin);}
    public EditText getComments() {return (EditText)findViewById(R.id.ID_comments);}
    public CheckBox getCheckbox() {return (CheckBox)findViewById(R.id.ID_checkbox);}
    public Inventory getInv() {return inv;}
    public int getPosition() {return position;}

    ItemController ic = new ItemController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // receive inventory and position of gift card to modify
        position = (int)getIntent().getIntExtra("position", 0);
        inv = (Inventory)getIntent().getSerializableExtra("inventory");
        gc = (GiftCard)getIntent().getSerializableExtra("gc");

        // Get references to UI
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

        // itemName.setText(inv.getInvList().get(position).getMerchant());
        Toast.makeText(getApplicationContext(), "Click user photofile to take temporary giftcard picture,  need camera settings to be emulated to work on virtual phone", Toast.LENGTH_LONG).show();

        if (inv != null){
            ic.displayGiftCardInfo(inv, position, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);
        }

        if (gc != null){
            ic.displayGiftCardInfo(gc, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);
            ic.setViewModeValue(true);
            ic.setViewMode(itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox, viewstatus, offerbutton, savebutton);
            viewstatus.setVisibility(View.GONE);
        }




        // Toast.makeText(getApplicationContext(), "Save Button at Bottom, and return to inventory, backbuton disabled for now till we can delete a giftcard as if user push backbutton it creates giftcard",Toast.LENGTH_LONG).show();


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

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**saveGiftCardInfo
     * saves the giftcard to inventory
     * @param menu
     */
    public void saveGiftCardInfo(View menu){
        // Get references to UI
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        EditText quantity = (EditText)findViewById(R.id.ID_quantity);
        Spinner qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        Spinner categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        EditText comments = (EditText)findViewById(R.id.ID_comments);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ID_checkbox);

        // item controller to set the data into inventory
        inv = ic.setGiftCardInfo(inv, position, itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox);


        // http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
        // send the modified inventory back to inventory activity
        Intent intent = new Intent();
        intent.putExtra("ModifiedInventory", inv);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        if ((itemName.getText().toString().equals("")) || itemValue.getText().toString().equals("")){
            inv.getInvList().remove(position);
        }

        // inv.getInvList().remove(position);
        Intent intent = new Intent();
        intent.putExtra("ModifiedInventory", inv);
        setResult(RESULT_OK, intent);
        finish();
    }


    // https://www.youtube.com/watch?v=pk-80p2ha_Q retrived oct 30 2015
    // barebones right now

    /**
     * takeGiftCardPic
     * takes a giftcard pic when you click on the profile picture
     * @param menu
     */
    public void takeGiftCardPic(View menu){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //setResult(RESULT_OK, intent);
        if (intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent, 2);
        }

    }
    // https://www.youtube.com/watch?v=pk-80p2ha_Q retrived oct 30 2015
    // Get picture data and put it in photo of giftcard

    /**onActivityResult
     *Saves the picture data into the image parameter
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**setViewStatus
     * Changes the view status when clicked "view as public or owner"
     * @param menu
     */
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

        // Changing the viewing mode, example user view mode can edit, borrower can only see
        ic.setViewMode(itemValue, itemName, quantity, qualitySpinner, categorySpinner, comments, checkbox, viewstatus, offerbutton, savebutton);

    }


}

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

    public Inventory inv;
    private int position;
    private GiftCard gc;
    private EditText etItemValue;
    private EditText etItemName;
    private EditText etQuantity;
    private Spinner qualitySpinner;
    private Spinner categorySpinner;
    private EditText etComments;
    private CheckBox checkbox;
    private Button editAndOfferButton;
    private Button saveButton;
    ImageView featuredImage;
    private int itemState;
    protected ArrayList<ItemImage> itemImagesList;

    // getters for UI testing
    public EditText getEtItemName() {
        return (EditText) findViewById(R.id.ID_item_value);
    }

    public EditText getEtItemValue() {
        return (EditText) findViewById(R.id.ID_item_Name);
    }

    public EditText getEtQuantity() {
        return (EditText) findViewById(R.id.ID_quantity);
    }

    public Spinner getQualitySpinner() {
        return (Spinner) findViewById(R.id.ID_qualitySpin);
    }

    public Spinner getCategorySpinner() {
        return (Spinner) findViewById(R.id.ID_categorySpin);
    }

    public EditText getEtComments() {
        return (EditText) findViewById(R.id.ID_comments);
    }

    public CheckBox getCheckbox() {
        return (CheckBox) findViewById(R.id.ID_checkbox);
    }

    public Inventory getInv() {
        return inv;
    }

    public int getPosition() {
        return position;
    }

    ItemController ic = new ItemController();
    ItemPictureController ipc = new ItemPictureController();

    // Constants
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public final static String EXTRA_PICTURES = "ca.ualberta.smaccr.giftcarder.PICTURES";
    public final static String EXTRA_BITMAP_STRING = "ca.ualberta.smaccr.giftcarder.BITMAPSTRING";
    public static final int ADD_STATE = 0; // add item
    public static final int OWNER_STATE = 1; // view own item
    public static final int BROWSER_STATE = 2; // view other's item
    public static final int EDIT_STATE = 3; // edit item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // receive inventory, position, and state of gift card
        position = (int) getIntent().getIntExtra("position", 0);
        inv = (Inventory) getIntent().getSerializableExtra("inventory");
        //gc = (GiftCard)getIntent().getSerializableExtra("gc");
        itemState = (int) getIntent().getIntExtra(EXTRA_STATE, OWNER_STATE);
        itemImagesList = inv.getInvList().get(position).getItemImagesList();
        featuredImage = (ImageView) findViewById(R.id.ID_pictureOfGiftCard);

        // Get references to UI
        etItemValue = (EditText) findViewById(R.id.ID_item_value);
        etItemName = (EditText) findViewById(R.id.ID_item_Name);
        etQuantity = (EditText) findViewById(R.id.ID_quantity);
        qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        etComments = (EditText) findViewById(R.id.ID_comments);
        checkbox = (CheckBox) findViewById(R.id.ID_checkbox);
        editAndOfferButton = (Button) findViewById(R.id.editAndOfferButton);
        saveButton = (Button) findViewById(R.id.ID_savegiftcard);

        // itemName.setText(inv.getInvList().get(position).getMerchant());
        //Toast.makeText(getApplicationContext(), "Click user photofile to take temporary giftcard picture,  need camera settings to be emulated to work on virtual phone", Toast.LENGTH_LONG).show();

        if (inv != null) {
            ic.displayGiftCardInfo(inv, position, etItemValue, etItemName, etQuantity,
                    qualitySpinner, categorySpinner, etComments, checkbox);
            ic.setViewMode(itemState, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox, editAndOfferButton, saveButton);

            // Display featured image
            if (!itemImagesList.isEmpty()) {
                ipc.displayFeaturedImage(itemImagesList, featuredImage);
            }

            // if user clicks Edit button
            if (itemState == OWNER_STATE) {
                editAndOfferButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        ic.setViewMode(EDIT_STATE, etItemValue, etItemName, etQuantity, qualitySpinner,
                                categorySpinner, etComments, checkbox, editAndOfferButton, saveButton);
                        itemState = EDIT_STATE;
                    }
                });
            // if user clicks Make Offer button
            } else if (itemState == BROWSER_STATE) {
                editAndOfferButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Toast.makeText(getApplicationContext(), "Trade Button Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        /*
        if (gc != null){
            ic.displayGiftCardInfo(gc, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox);
            ic.setViewModeValue(false);
            ic.setViewMode(etItemValue, etItemName, etQuantity, qualitySpinner, categorySpinner,
                    etComments, checkbox, viewStatusButton, offerButton, saveButton);
            viewStatusButton.setVisibility(View.GONE);
        }
        */

        // Toast.makeText(getApplicationContext(), "Save Button at Bottom, and return to inventory, backbutton disabled for now till we can delete a giftcard as if user push backbutton it creates giftcard",Toast.LENGTH_LONG).show();
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

    /**
     * saveGiftCardInfo
     * saves the giftcard to inventory
     *
     * @param menu View
     */
    public void saveGiftCardInfo(View menu) {
        if (ic.validateFields(etItemValue, etItemName, etQuantity)) {
            // item controller to set the data into inventory
            inv = ic.setGiftCardInfo(inv, position, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox, itemImagesList);

            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();

            // http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
            // send the modified inventory back to inventory activity
            Intent intent = new Intent();
            intent.putExtra("ModifiedInventory", inv);
            setResult(RESULT_OK, intent);

            // only return to inventory when ItemActivity is in Add_STATE
            if (itemState == ADD_STATE) {
                finish();
            } else {
                ic.setViewMode(OWNER_STATE, etItemValue, etItemName, etQuantity, qualitySpinner,
                        categorySpinner, etComments, checkbox, editAndOfferButton, saveButton);
                itemState = OWNER_STATE;
            }

        } else {
            Toast.makeText(this, "Form contains error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        /*
        EditText itemValue = (EditText) findViewById(R.id.ID_item_value);
        EditText itemName = (EditText)findViewById(R.id.ID_item_Name);
        if ((itemName.getText().toString().equals("")) || itemValue.getText().toString().equals("")){
            inv.getInvList().remove(position);
        }
        */

        if (itemState == ADD_STATE) {
            inv.getInvList().remove(position);
        }

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
     *
     * @param menu
     */
    public void takeGiftCardPic(View menu) {
        Intent intent = new Intent(ItemActivity.this, ItemPictureActivity.class);
        intent.putExtra(EXTRA_PICTURES, itemImagesList);
        intent.putExtra(EXTRA_STATE, itemState);
        startActivityForResult(intent, 1);

        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //setResult(RESULT_OK, intent);
        //if (intent.resolveActivity(getPackageManager()) != null) {
          //  startActivityForResult(intent, 2);
        //}

    }
    // https://www.youtube.com/watch?v=pk-80p2ha_Q retrived oct 30 2015
    // Get picture data and put it in photo of giftcard

    /**
     * onActivityResult
     * Saves the picture data into the image parameter
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                itemImagesList = (ArrayList<ItemImage>)data.getSerializableExtra(EXTRA_PICTURES);
                String bitmapString = data.getStringExtra(EXTRA_BITMAP_STRING);

                if (bitmapString != null) {
                    featuredImage.setImageBitmap(ipc.decodeBase64(bitmapString));
                }
            }
        }
    }
}

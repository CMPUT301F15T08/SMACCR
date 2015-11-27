package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends Activity {

    public Inventory inv;
    private int position;
    private GiftCard gc;
    private String owner;
    private TextView tvOwnerTitle;
    private TextView tvCategoryTitle;
    private TextView tvQualityTitle;
    private TextView tvCommentsTitle;
    private TextView tvQuantityTitle;
    private TextView tvValueTitle;
    private TextView tvMerchantTitle;
    private EditText etItemValue;
    private EditText etItemName;
    private EditText etQuantity;
    private Spinner qualitySpinner;
    private Spinner categorySpinner;
    private EditText etComments;
    private CheckBox checkbox;
    private Button editButton;
    private Button saveButton;
    private Button makeOfferButton;
    private Button cloneItemButton;
    protected ImageView featuredImage;
    private int itemState;
    protected ArrayList<ItemImage> itemImagesList;

    // for cloning items only
    public Inventory ownerInv;

    // Controllers
    ItemController ic = new ItemController();
    ItemPictureController ipc = new ItemPictureController();

    // Getters for UI testing
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

    // Constants
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public final static String EXTRA_PICTURES = "ca.ualberta.smaccr.giftcarder.PICTURES";
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
        gc = (GiftCard)getIntent().getSerializableExtra("gc");
        itemState = (int) getIntent().getIntExtra(EXTRA_STATE, OWNER_STATE);

        featuredImage = (ImageView) findViewById(R.id.ID_pictureOfGiftCard);


        // Get references to UI
        tvOwnerTitle = (TextView) findViewById(R.id.ID_itemOwner);
        tvCategoryTitle = (TextView) findViewById(R.id.tvCategoryTitle);
        tvQualityTitle = (TextView) findViewById(R.id.tvQualityTitle);
        tvQuantityTitle = (TextView) findViewById(R.id.tvQuantityTitle);
        tvCommentsTitle = (TextView) findViewById(R.id.tvCommentsTitle);
        tvValueTitle = (TextView) findViewById(R.id.tvValueTitle);
        tvMerchantTitle = (TextView) findViewById(R.id.tvMerchantTitle);
        etItemValue = (EditText) findViewById(R.id.ID_item_value);
        etItemName = (EditText) findViewById(R.id.ID_item_Name);
        etQuantity = (EditText) findViewById(R.id.ID_quantity);
        qualitySpinner = (Spinner) findViewById(R.id.ID_qualitySpin);
        categorySpinner = (Spinner) findViewById(R.id.ID_categorySpin);
        etComments = (EditText) findViewById(R.id.ID_comments);
        checkbox = (CheckBox) findViewById(R.id.ID_checkbox);
        editButton = (Button) findViewById(R.id.editButton);
        saveButton = (Button) findViewById(R.id.ID_savegiftcard);
        makeOfferButton = (Button) findViewById(R.id.makeOfferButton);
        cloneItemButton = (Button) findViewById(R.id.cloneItemButton);


        // receive owner's inventory for cloning friend's items into it
        if (itemState == BROWSER_STATE) {
            ownerInv = (Inventory) getIntent().getSerializableExtra("ownerInventory");

        }

        // itemName.setText(inv.getInvList().get(position).getMerchant());
        // Toast.makeText(getApplicationContext(), "Click user photofile to take temporary giftcard picture,  need camera settings to be emulated to work on virtual phone", Toast.LENGTH_LONG).show();

        if (inv != null) {
            itemImagesList = inv.getInvList().get(position).getItemImagesList();
            ic.displayGiftCardInfo(inv, position, tvOwnerTitle, etItemValue, etItemName, etQuantity,
                    qualitySpinner, categorySpinner, etComments, checkbox);
            ic.setViewMode(itemState, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox, editButton, saveButton, makeOfferButton,
                    cloneItemButton);

            // Display featured image
            if (!itemImagesList.isEmpty()) {
                ipc.displayFeaturedImage(itemImagesList, featuredImage);
            }
        }


        if (gc != null) {
            ic.displayGiftCardInfo(gc, tvOwnerTitle, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox);
            itemImagesList = gc.getItemImagesList();
            ic.setViewMode(itemState, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox, editButton, saveButton, makeOfferButton,
                    cloneItemButton);

            // Display featured image
            if (!itemImagesList.isEmpty()) {
                ipc.displayFeaturedImage(itemImagesList, featuredImage);
            }
        }
        // Toast.makeText(getApplicationContext(), "Save Button at Bottom, and return to inventory, backbutton disabled for now till we can delete a giftcard as if user push backbutton it creates giftcard",Toast.LENGTH_LONG).show();

        // receive owner's inventory for cloning friend's items into it
        if (itemState == BROWSER_STATE) {
            //delete make public stuff
            findViewById(R.id.MakePublicTextView).setVisibility(View.GONE);
            checkbox.setVisibility(View.GONE);



            //add the values to the title
            tvQualityTitle.append(": " + qualitySpinner.getSelectedItem().toString());
            tvCategoryTitle.append(": " + categorySpinner.getSelectedItem().toString());
            tvCommentsTitle.append(":");
            tvValueTitle.append(": " + etItemValue.getText());
            tvMerchantTitle.append(": " + etItemName.getText());
            tvQuantityTitle.append(": " + etQuantity.getText());

            //set their padding
            tvQualityTitle.setPadding(10, 10, 10, 10);
            tvCategoryTitle.setPadding(10, 10, 10, 10);
            tvCommentsTitle.setPadding(10, 10, 10, 10);
            tvValueTitle.setPadding(10, 10, 10, 10);
            tvMerchantTitle.setPadding(10, 10, 10, 10);
            tvQuantityTitle.setPadding(10, 10, 10, 10);
            etComments.setPadding(10, 10, 10, 10);

            //set their color
            tvQualityTitle.setBackgroundColor(Color.LTGRAY);
            tvCategoryTitle.setBackgroundColor(Color.LTGRAY);
            tvCommentsTitle.setBackgroundColor(Color.LTGRAY);
            tvValueTitle.setBackgroundColor(Color.LTGRAY);
            tvMerchantTitle.setBackgroundColor(Color.LTGRAY);
            tvQuantityTitle.setBackgroundColor(Color.LTGRAY);
            etComments.setBackgroundColor(Color.LTGRAY);


            //remove the value as it is now in the title
            etItemName.setVisibility(View.GONE);
            etQuantity.setVisibility(View.GONE);
            etItemValue.setVisibility(View.GONE);
            findViewById(R.id.dollarSignEditText).setVisibility(View.GONE);
            etItemName.setVisibility(View.GONE);
            tvQuantityTitle.setVisibility(View.GONE);
            qualitySpinner.setVisibility(View.GONE);
            categorySpinner.setVisibility(View.GONE);

            if (etComments.getText().toString().equals("")){
                etComments.setVisibility(View.GONE);
                tvCommentsTitle.setVisibility(View.GONE);
            }



        }
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

        // Get current username - whoever is editing a card is the owner, and is logged in.
        Intent intent = getIntent();
        owner = intent.getStringExtra(EXTRA_USERNAME);

        if (ic.validateFields(etItemValue, etItemName, etQuantity, categorySpinner)) {
            // item controller to set the data into inventory
            inv = ic.setGiftCardInfo(inv, owner, position, etItemValue, etItemName, etQuantity, qualitySpinner,
                    categorySpinner, etComments, checkbox, itemImagesList);

            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();

            // http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
            // send the modified inventory back to inventory activity
            Intent intent2 = new Intent();
            intent2.putExtra("ModifiedInventory", inv);
            setResult(RESULT_OK, intent2);

            // only return to inventory when ItemActivity is in Add State
            if (itemState == ADD_STATE) {
                finish();
            } else {
                itemState = OWNER_STATE;
                ic.setViewMode(itemState, etItemValue, etItemName, etQuantity, qualitySpinner,
                        categorySpinner, etComments, checkbox, editButton, saveButton,
                        makeOfferButton, cloneItemButton);
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

                if (!itemImagesList.isEmpty()) {
                    ipc.displayFeaturedImage(itemImagesList, featuredImage);
                }

            }
        }
    }

    public void onEditButtonClick(View view) {
        itemState = EDIT_STATE;
        ic.setViewMode(itemState, etItemValue, etItemName, etQuantity, qualitySpinner,
                categorySpinner, etComments, checkbox, editButton, saveButton, makeOfferButton,
                cloneItemButton);
    }

    public void onMakeOfferButtonClick(View view) {
        /*
        // Switch to item activity and send inventory and position of gift card for trading
        Intent intent = new Intent(ItemActivity.this, CreateTradeOfferActivity.class);
        //intent.putExtra("GiftCard", inv.getInvList().get(position));
        intent.putExtra("position", position);
        intent.putExtra("inventory", inv);
        //startActivity(intent);
        */

        Toast.makeText(this, "Make Offer clicked", Toast.LENGTH_SHORT).show();
    }


}

package ca.ualberta.smaccr.giftcarder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Richard on 2015-10-29.  Edited by Carin.
 * // GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
 */
public class ItemController {

    // Constants
    public static final int ADD_STATE = 0; // add item
    public static final int OWNER_STATE = 1; // view own item
    public static final int BROWSER_STATE = 2; // view other's item
    private File imageFile;

    public void takeAPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    }

    // Place the gift card's information into the view

    /**
     * displayGiftCardInfo
     * display giftcard when given inventory and position of the giftcard.
     * @param inv Inventory
     * @param position int
     * @param etItemValue EditText
     * @param etItemName EditText
     * @param etQuantity EditText
     * @param qualitySpinner Spinner
     * @param categorySpinner Spinner
     * @param etComments EditText
     * @param checkbox CheckBox
     */
    public void displayGiftCardInfo(Inventory inv, int position, EditText etItemValue,
                                    EditText etItemName, EditText etQuantity, Spinner qualitySpinner,
                                    Spinner categorySpinner, EditText etComments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);
        DecimalFormat df = new DecimalFormat("#.00");

        // Show hint if value is equal to 0
        if ((tempcard.getValue() == 0.00) || (tempcard.getValue() < 0)){
            // blank string will show hint in edittext widget
            etItemValue.setText("");
        }
        else {etItemValue.setText(df.format(tempcard.getValue()));}

        etItemName.setText(tempcard.getMerchant());

        // Show hint if value is equal to 0
        if ((tempcard.getQuantity() == 0 || (tempcard.getQuantity() < 0))){
            // blank string will show hint in edittext widget
            etQuantity.setText("");
        }
        else {etQuantity.setText(String.valueOf(tempcard.getQuantity()));}

        categorySpinner.setSelection(tempcard.getCategory(), false);
        qualitySpinner.setSelection(tempcard.getQuality(), false);
        etComments.setText(tempcard.getComments());
        checkbox.setChecked(tempcard.getShared());
    }


    /**

     displayGiftCardInfo
     display the giftcard
     GiftCard gc, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox
     void */


    // Place the gift card's information into the view, with given giftcard

    /**
     * displayGiftCardInfo
     * display the giftcard data in the view, with given giftcard
     * @param gc Giftcard
     * @param etItemValue EditText
     * @param etItemName EditText
     * @param etQuantity EditText
     * @param qualitySpinner Spinner
     * @param categorySpinner Spinner
     * @param etComments EditText
     * @param checkbox CheckBox
     */

    public void displayGiftCardInfo(GiftCard gc, EditText etItemValue, EditText etItemName,
                                    EditText etQuantity, Spinner qualitySpinner,
                                    Spinner categorySpinner, EditText etComments,
                                    CheckBox checkbox) {

        // Show hint if value is equal to 0
        etItemValue.setText(String.valueOf(gc.getValue()));
        if ((gc.getValue() == 0.00) || (gc.getValue() < 0)){
            // blank string will show hint in edittext widget
            etItemValue.setText("");
        }
        else {etItemValue.setText(String.valueOf(gc.getValue()));}

        etItemName.setText(gc.getMerchant());

        etQuantity.setText(String.valueOf(gc.getQuantity()));
        // Show hint if value is equal to 0
        if ((gc.getQuantity() == 0 || (gc.getQuantity() < 0))){
            // blank string will show hint in edittext widget
            etQuantity.setText("");
        }
        else {etQuantity.setText(String.valueOf(gc.getQuantity()));}


        categorySpinner.setSelection(gc.getCategory(), false);
        qualitySpinner.setSelection(gc.getQuality(), false);
        etComments.setText(gc.getComments());
        checkbox.setChecked(gc.getShared());
    }


    /**
     setGiftCardInfo
     set the inventory set giftcard info
     Inventory inv, int position, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox
     modified inventory */

    // Set inventory with modifed gift card item

    /**setGiftCardInfo
     * To set the modified gift card into inventory
     * @param inv Inventory
     * @param position int
     * @param etItemValue EditTExt
     * @param etItemName EditText
     * @param etQuantity EditText
     * @param qualitySpinner Spinner
     * @param categorySpinner Spinner
     * @param etComments EditText
     * @param checkbox CheckBox
     * @return inv
     */
    public Inventory setGiftCardInfo(Inventory inv, int position, EditText etItemValue,
                                     EditText etItemName, EditText etQuantity,
                                     Spinner qualitySpinner, Spinner categorySpinner,
                                     EditText etComments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);

        // If invalid dollar, cent amount then set to zero for now!
        try {
            tempcard.setValue(Double.parseDouble(etItemValue.getText().toString().replaceAll("\\s+", "")));
        } catch (NumberFormatException e) {
            tempcard.setValue(0);
        }

        tempcard.setMerchant(etItemName.getText().toString());

        // If invalid input ie not a integer, input zero for now!
        try {
            tempcard.setQuantity(Integer.parseInt(etQuantity.getText().toString().replaceAll("\\s+", "")));
        } catch (NumberFormatException e) {
            tempcard.setQuantity(0);
        }
        tempcard.setComments(etComments.getText().toString());
        tempcard.setShared(checkbox.isChecked());

        // Some weird bug when using spinner; it sets index out of range sometimes
        tempcard.setQuality(qualitySpinner.getSelectedItemPosition());
        tempcard.setCategory(categorySpinner.getSelectedItemPosition());

        inv.getInvList().set(position, tempcard);
        return inv;
    }

    /**
     * setViewMode
     * To set the view of the ItemActivity
     * @param itemState int
     * @param etItemValue EditText
     * @param etItemName EditText
     * @param etQuantity EditText
     * @param qualitySpinner Spinner
     * @param categorySpinner Spinner
     * @param etComments EditText
     * @param checkbox CheckBox
     * @param editAndOfferButton Button
     * @param saveButton Button
     */
    public void setViewMode(int itemState, EditText etItemValue ,EditText etItemName,
                            EditText etQuantity, Spinner qualitySpinner, Spinner categorySpinner,
                            EditText etComments, CheckBox checkbox, Button editAndOfferButton,
                            Button saveButton) {

        if (itemState == ADD_STATE) {
            etItemValue.setFocusableInTouchMode(true);
            etItemName.setFocusableInTouchMode(true);
            qualitySpinner.setClickable(true);
            categorySpinner.setClickable(true);
            etQuantity.setFocusableInTouchMode(true);
            etComments.setFocusableInTouchMode(true);
            checkbox.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);
            editAndOfferButton.setVisibility(View.GONE);

            if (etComments.getText().toString().trim().equals("")) {
                etComments.setHint("Enter comments (optional)");
            }

        } else {
            etItemValue.setFocusable(false);
            etItemName.setFocusable(false);
            qualitySpinner.setClickable(false);
            categorySpinner.setClickable(false);
            etQuantity.setFocusable(false);
            etComments.setFocusable(false);
            checkbox.setEnabled(false);
            saveButton.setVisibility(View.GONE);
            editAndOfferButton.setVisibility(View.VISIBLE);

            if (itemState == OWNER_STATE) {
                editAndOfferButton.setText("Edit");
            } else {
                editAndOfferButton.setText("Trade");
            }
        }
    }

    /**
     * Validates text fields (make sure that content exists and it is the correct format)
     *
     * @param       etItemValue EditText
     * @param       etItemName EditText
     * @param       etQuantity EditText
     * @return      boolean
     */
    public boolean validateFields(EditText etItemValue, EditText etItemName, EditText etQuantity) {
        boolean valid = true;

        if (!Validation.hasText(etItemValue)) {
            valid = false;
        }

        if (!Validation.hasText(etItemName)) {
            valid = false;
        }

        if (!Validation.hasText(etQuantity)) {
            valid = false;
        }

        return valid;
    }
}

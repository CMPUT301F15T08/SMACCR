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

/**
 * Created by Richard on 2015-10-29.
 * // GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
 */
public class ItemController {

    private File imageFile;

    //false = owner's view, true = public view
    private boolean viewMode = false;

    public void setViewModeValue(boolean viewMode) {
        this.viewMode = viewMode;
    }

    /**

     takeAPicture
     picture function

     void */

    public void takeAPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);


    }

    // Place the gift card's information into the view
    public void displayGiftCardInfo(Inventory inv, int position, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);

        // Show hint if value is equal to 0
        itemValue.setText(String.valueOf(tempcard.getValue()));
        if ((tempcard.getValue() == 0.00) || (tempcard.getValue() < 0)){
            // blank string will show hint in edittext widget
            itemValue.setText("");
        }
        else {itemValue.setText(String.valueOf(tempcard.getValue()));}

        itemName.setText(tempcard.getMerchant());

        quantity.setText(String.valueOf(tempcard.getQuantity()));
        // Show hint if value is equal to 0
        if ((tempcard.getQuantity() == 0 || (tempcard.getQuantity() < 0))){
            // blank string will show hint in edittext widget
            quantity.setText("");
        }
        else {quantity.setText(String.valueOf(tempcard.getQuantity()));}


        categorySpinner.setSelection(tempcard.getCategory(), false);
        qualitySpinner.setSelection(tempcard.getQuality(), false);
        comments.setText(tempcard.getComments());
        checkbox.setChecked(tempcard.getShared());
    }


    /**

     displayGiftCardInfo
     display the giftcard
     GiftCard gc, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox
     void */


    // Place the gift card's information into the view, with given giftcard
    public void displayGiftCardInfo(GiftCard gc, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox) {
        GiftCard tempcard = gc;

        // Show hint if value is equal to 0
        itemValue.setText(String.valueOf(tempcard.getValue()));
        if ((tempcard.getValue() == 0.00) || (tempcard.getValue() < 0)){
            // blank string will show hint in edittext widget
            itemValue.setText("");
        }
        else {itemValue.setText(String.valueOf(tempcard.getValue()));}

        itemName.setText(tempcard.getMerchant());

        quantity.setText(String.valueOf(tempcard.getQuantity()));
        // Show hint if value is equal to 0
        if ((tempcard.getQuantity() == 0 || (tempcard.getQuantity() < 0))){
            // blank string will show hint in edittext widget
            quantity.setText("");
        }
        else {quantity.setText(String.valueOf(tempcard.getQuantity()));}


        categorySpinner.setSelection(tempcard.getCategory(), false);
        qualitySpinner.setSelection(tempcard.getQuality(), false);
        comments.setText(tempcard.getComments());
        checkbox.setChecked(tempcard.getShared());
    }

    /**

     setGiftCardInfo
     set the inventory set giftcard info
     Inventory inv, int position, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox
     modified inventory */



    // Set inventory with modifed gift card item
    public Inventory setGiftCardInfo(Inventory inv, int position, EditText itemValue, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);

        // If invalid dollar, cent amount then set to zero for now!
        try {
            tempcard.setValue(Double.parseDouble(itemValue.getText().toString().replaceAll("\\s+", "")));
        } catch (NumberFormatException e) {
            tempcard.setValue(0);
        }

        tempcard.setMerchant(itemName.getText().toString());

        // If invalid input ie not a integer, input zero for now!
        try {
            tempcard.setQuantity(Integer.parseInt(quantity.getText().toString().replaceAll("\\s+", "")));
        } catch (NumberFormatException e) {
            tempcard.setQuantity(0);
        }
        tempcard.setComments(comments.getText().toString());
        tempcard.setShared(checkbox.isChecked());

        // Some weird bug when using spinner; it set index out of range sometimes
        tempcard.setQuality(qualitySpinner.getSelectedItemPosition());
        tempcard.setCategory(categorySpinner.getSelectedItemPosition());


        inv.getInvList().set(position, tempcard);
        return inv;
    }

    /**

     setViewMode
     To change the viewing mode
     EditText itemValue ,EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox, Button viewStatus, Button offerButton, Button savebutton
     void */


    public void setViewMode(EditText itemValue ,EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox, Button viewStatus, Button offerButton, Button savebutton) {

        itemValue.setEnabled(viewMode);
        itemName.setEnabled(viewMode);
        qualitySpinner.setEnabled(viewMode);
        categorySpinner.setEnabled(viewMode);
        quantity.setEnabled(viewMode);
        comments.setEnabled(viewMode);
        checkbox.setEnabled(viewMode);

        if (viewMode){viewMode = false;
        viewStatus.setText("View as Public");
            offerButton.setVisibility(View.GONE);
            savebutton.setVisibility(View.VISIBLE);
            itemName.setTextColor(Color.BLACK);
            itemValue.setTextColor(Color.BLACK);
            quantity.setTextColor(Color.BLACK);
        }
        else {
            viewMode = true;
            viewStatus.setText("View as Owner");
            offerButton.setVisibility(View.VISIBLE);
            savebutton.setVisibility(View.GONE);
            itemName.setTextColor(Color.RED);
            itemValue.setTextColor(Color.RED);
            quantity.setTextColor(Color.RED);
        }
    }


}

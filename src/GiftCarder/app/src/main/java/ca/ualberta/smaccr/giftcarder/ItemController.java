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
 * //GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
 */
public class ItemController {

    private File imageFile;

    //false = owner's view, true = public view
    private boolean viewMode = false;

    public void takeAPicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, 1);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);


    }

    //Place the gift card's information into the view
    public void displayGiftCardInfo(Inventory inv, int position, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);

        itemName.setText(tempcard.getMerchant());
        quantity.setText(String.valueOf(tempcard.getQuantity()));

        categorySpinner.setSelection(tempcard.getCategory(), false);
        qualitySpinner.setSelection(tempcard.getQuality(), false);

        comments.setText(tempcard.getComments());
        checkbox.setChecked(tempcard.getShared());
    }

    //Set inventory with modifed gift card item
    public Inventory setGiftCardInfo(Inventory inv, int position, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox) {
        GiftCard tempcard = inv.getInvList().get(position);

        tempcard.setMerchant(itemName.getText().toString());

        //If invalid input ie not a integer, input zero
        try {
            tempcard.setQuantity(Integer.parseInt(quantity.getText().toString()));
        } catch (NumberFormatException e) {
            tempcard.setQuantity(0);
        }
        tempcard.setComments(comments.getText().toString());
        tempcard.setShared(checkbox.isChecked());

        //SOme weird bug when using spinner it set index out of range sometimes
        tempcard.setQuality(qualitySpinner.getSelectedItemPosition());
        tempcard.setCategory(categorySpinner.getSelectedItemPosition());


        inv.getInvList().set(position, tempcard);
        return inv;
    }

    public void setViewMode(EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox, Button viewStatus, Button offerButton) {

        itemName.setEnabled(viewMode);
        qualitySpinner.setEnabled(viewMode);
        categorySpinner.setEnabled(viewMode);
        quantity.setEnabled(viewMode);
        comments.setEnabled(viewMode);
        checkbox.setEnabled(viewMode);

        if (viewMode){viewMode = false;
        viewStatus.setText("View as Public");
            offerButton.setVisibility(View.GONE);
            itemName.setTextColor(Color.BLACK);
        }
        else {
            viewMode = true;
            viewStatus.setText("View as Owner");
            offerButton.setVisibility(View.VISIBLE);
            itemName.setTextColor(Color.RED);
        }
    }


}

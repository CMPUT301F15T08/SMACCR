package ca.ualberta.smaccr.giftcarder;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Richard on 2015-10-29.
 * //GiftCard(String merchant, int quantity, int quality, int category, String comments, Boolean shared)
 */
public class ItemController {

    public void displayGiftCardInfo(Inventory inv, int position, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox){
        GiftCard tempcard = inv.getInvList().get(position);

        //Do not set any gift card info if the giftcard is new
        if (tempcard.getMerchant() == "New GiftCard"){
            return;
        }
        //set gift card data to view
        itemName.setText(tempcard.getMerchant());
        quantity.setText(tempcard.getQuantity());
        qualitySpinner.setSelection(tempcard.getQuality());
        categorySpinner.setSelection(tempcard.getCategory());
        comments.setText(tempcard.getComments());
        checkbox.setChecked(tempcard.getShared());
    }

    //Set inventory with modifed gift card item
    public Inventory setGiftCardInfo(Inventory inv, int position, EditText itemName, EditText quantity, Spinner qualitySpinner, Spinner categorySpinner, EditText comments, CheckBox checkbox){
        GiftCard tempcard = inv.getInvList().get(position);

        tempcard.setMerchant(itemName.getText().toString());
        tempcard.setQuantity(Integer.parseInt(quantity.getText().toString()));
        tempcard.setComments(comments.getText().toString());
        tempcard.setShared(checkbox.isChecked());
        tempcard.setQuality(qualitySpinner.getSelectedItemPosition());
        tempcard.setCategory(categorySpinner.getSelectedItemPosition());

        inv.getInvList().set(position, tempcard);
        return inv;
    }
}

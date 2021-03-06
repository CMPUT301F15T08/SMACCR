/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-29.  Edited by Carin.
 *
 * Item controller controls the item for setting the states and viewing mode for giftcard
 *
 */
public class ItemController {

    // Constants
    public static final int ADD_STATE = 0; // add item
    public static final int OWNER_STATE = 1; // view own item
    public static final int BROWSER_STATE = 2; // view other's item
    public static final int EDIT_STATE = 3; // add item
    public static final int FRIEND_STATE = 4; // view other's item from Friend
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
    public void displayGiftCardInfo(Inventory inv,
                                    int position,
                                    TextView tvOwnerTitle,
                                    EditText etItemValue,
                                    EditText etItemName,
                                    EditText etQuantity,
                                    Spinner qualitySpinner,
                                    Spinner categorySpinner,
                                    EditText etComments,
                                    CheckBox checkbox) {

        GiftCard tempcard = inv.getInvList().get(position);
        if (tempcard.getOwner() != null) {
            tvOwnerTitle.setText(tempcard.getOwner() + "'s GiftCard");
        }
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
     * @param tvOwnerTitle TextView
     * @param etItemValue EditText
     * @param etItemName EditText
     * @param etQuantity EditText
     * @param qualitySpinner Spinner
     * @param categorySpinner Spinner
     * @param etComments EditText
     * @param checkbox CheckBox
     */

    public void displayGiftCardInfo(GiftCard gc,
                                    TextView tvOwnerTitle,
                                    EditText etItemValue,
                                    EditText etItemName,
                                    EditText etQuantity,
                                    Spinner qualitySpinner,
                                    Spinner categorySpinner,
                                    EditText etComments,
                                    CheckBox checkbox) {

        if (gc.getOwner() != null) {
            tvOwnerTitle.setText(gc.getOwner() + "'s GiftCard");
        }
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
    public Inventory setGiftCardInfo(Inventory inv,
                                     String owner,
                                     int position,
                                     EditText etItemValue,
                                     EditText etItemName,
                                     EditText etQuantity,
                                     Spinner qualitySpinner,
                                     Spinner categorySpinner,
                                     EditText etComments,
                                     CheckBox checkbox,
                                     ArrayList<ItemImage> itemImagesList) {
        GiftCard tempcard = inv.getInvList().get(position);
        tempcard.setOwner(tempcard.getBelongsTo());

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
        tempcard.setItemImagesList(itemImagesList);

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
     * @param editButton Button
     * @param saveButton Button
     */
    public void setViewMode(int itemState,
                            EditText etItemValue,
                            EditText etItemName,
                            EditText etQuantity,
                            Spinner qualitySpinner,
                            Spinner categorySpinner,
                            EditText etComments,
                            CheckBox checkbox,
                            Button editButton,
                            Button saveButton,
                            Button makeOfferButton,
                            Button cloneItemButton) {

        if ((itemState == ADD_STATE) || (itemState == EDIT_STATE)) {
            etItemValue.setFocusableInTouchMode(true);
            etItemName.setFocusableInTouchMode(true);
            qualitySpinner.setClickable(true);
            categorySpinner.setClickable(true);
            etQuantity.setFocusableInTouchMode(true);
            etComments.setFocusableInTouchMode(true);
            checkbox.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            makeOfferButton.setVisibility(View.GONE);
            cloneItemButton.setVisibility(View.GONE);

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

            if (itemState == OWNER_STATE) {
                editButton.setVisibility(View.VISIBLE);
                makeOfferButton.setVisibility(View.GONE);
                cloneItemButton.setVisibility(View.GONE);

            } else if (itemState == FRIEND_STATE){
                editButton.setVisibility(View.GONE);
                makeOfferButton.setVisibility(View.GONE);
                cloneItemButton.setVisibility(View.VISIBLE);
            } else {
                editButton.setVisibility(View.GONE);
                makeOfferButton.setVisibility(View.VISIBLE);
                cloneItemButton.setVisibility(View.GONE);
            }

        }
    }

    /**
     * Clones friend's item into user's (owner's) inventory
     *
     * @param inv friend's inventory
     * @param position position of gift card in friend's inventory
     * @param ownerInv user's (owner's) inventory that item will be cloned to
     * @param ownerUsername user's (owner's) username
     */
    public Inventory cloneItem(Inventory inv, int position, Inventory ownerInv, String ownerUsername) {
        GiftCard giftCard = inv.getInvList().get(position);
        giftCard.setOwner(ownerUsername);
        ownerInv.addGiftCard(giftCard);
        return ownerInv;
    }

    /**
     * Clones friend's item into user's (owner's) item
     *
     * @param giftCard GiftCard
     * @param ownerInv user's (owner's) inventory that item will be cloned to
     * @param ownerUsername user's (owner's) username
     */
    public Inventory cloneItem(GiftCard giftCard, Inventory ownerInv, String ownerUsername) {
        giftCard.setOwner(ownerUsername);
        ownerInv.addGiftCard(giftCard);
        return ownerInv;
    }


    /**
     * Validates text fields (make sure that content exists and it is the correct format)
     *
     * @param       etItemValue EditText
     * @param       etItemName EditText
     * @param       etQuantity EditText
     * @return      boolean
     */
    public boolean validateFields(EditText etItemValue, EditText etItemName, EditText etQuantity,
                                  Spinner categorySpinner) {
        boolean valid = true;
        int quantity = Integer.parseInt(etQuantity.getText().toString().trim());

        if (!Validation.hasText(etItemValue)) {
            valid = false;
        }

        if (!Validation.hasText(etItemName)) {
            valid = false;
        }

        if (!Validation.hasText(etQuantity)) {
            valid = false;
        }

        if (quantity == 0 ) {
            etQuantity.setError("Cannot have zero gift cards");
            valid = false;
        }

        if (categorySpinner.getSelectedItemPosition() == 0) {

            /* Modified from EdmundYeung99,retrieved 11/22/15
             * http://stackoverflow.com/questions/3749971/creating-a-seterror-for-the-spinner
             */
            TextView errorText = (TextView)categorySpinner.getSelectedView();
            errorText.setError("No category selected");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Choose a category");//changes the selected item text to this

            valid = false;
        }

        return valid;
    }
}

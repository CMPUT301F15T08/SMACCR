/* UserProfileController handles different states for UserProfileActivity */

package ca.ualberta.smaccr.giftcarder;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by cbli on 11/16/15.
 */
public class UserProfileController {
    // Constants
    public static final int OWNER_STATE = 0; // view own profile (has edit button)
    public static final int EDIT_STATE = 1; // edit own profile (has save button)
    public static final int FRIEND_STATE = 3; // view friend's profile (no button)


    /**
     * Sets the view mode of UserProfileActivity based on profileState
     * OWNER_STATE = user views own profile (has edit button)
     * EDIT_STATE = user can edit own profile (has save button)
     * FRIEND_STATE = user views a friend's profile (no buttons)
     *
     * @param  profileState int
     * @param  etCity EditText
     * @param  etPhone EditText
     * @param  etEmail EditText
     * @param  editButton Button
     * @param  saveButton Button
     */
    public void setViewMode(int profileState, EditText etCity, EditText etPhone, EditText etEmail,
                            Button editButton, Button saveButton) {

        if (profileState != EDIT_STATE) {
            etCity.setFocusable(false);
            etPhone.setFocusable(false);
            etEmail.setFocusable(false);
            saveButton.setVisibility(View.GONE);

            if (profileState != FRIEND_STATE) {
                editButton.setVisibility(View.VISIBLE);

            } else {  // in FRIEND_STATE
                editButton.setVisibility(View.GONE);
            }

        } else { // in EDIT_STATE
            etCity.setFocusableInTouchMode(true);
            etPhone.setFocusableInTouchMode(true);
            etEmail.setFocusableInTouchMode(true);
            editButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        }
    }
}

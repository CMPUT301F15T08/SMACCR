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
    public static final int STRANGER_STATE = 2; // send friend request to stranger (has send friend request button)
    public static final int FRIEND_STATE = 3; // view friend's profile (no button)

    public void setViewMode(int profileState, EditText etCity, EditText etPhone, EditText etEmail,
                            Button multiButton, Button saveButton) {

        if (profileState != EDIT_STATE) {
            etCity.setFocusable(false);
            etPhone.setFocusable(false);
            etEmail.setFocusable(false);
            saveButton.setVisibility(View.GONE);

            if (profileState != FRIEND_STATE) {
                multiButton.setVisibility(View.VISIBLE);
                if (profileState == OWNER_STATE) {
                    multiButton.setText("Edit");
                } else { // in STRANGER_STATE
                    multiButton.setText("Send Friend Request");
                }

            } else {  // in FRIEND_STATE
                multiButton.setVisibility(View.GONE);
            }

        } else { // in EDIT_STATE
            etCity.setFocusableInTouchMode(true);
            etPhone.setFocusableInTouchMode(true);
            etEmail.setFocusableInTouchMode(true);
            multiButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
        }
    }
}

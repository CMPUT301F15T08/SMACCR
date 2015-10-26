/* References:
 *
 * Email regular expression:
 * Lokesh Gupta, http://howtodoinjava.com/2014/11/11/java-regex-validate-email-address/, retrieved
 * 26/10/15
 *
 * Phone regular expression:
 * Steven Smith, http://regexlib.com/Search.aspx?k=phone+number, retrieved 26/10/15
 *
 * Modified code from on validation:
 * Shahab, https://tausiq.wordpress.com/2013/01/19/android-input-field-validation/, retrieved
 * 26/10/15
 */

package ca.ualberta.smaccr.giftcarder;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Carin on 10/26/2015.
 */
public class Validation {

    // Regular Expression
    private static final String PHONE_REGEX = "^[2-9]\\d{2}-\\d{3}-\\d{4}$";
    private static final String EMAIL_REGEX = " ^(.+)@(.+)$";

    // Error Messages
    private static final String REQUIRED_MSG = "Field cannot be left blank";
    private static final String PHONE_MSG = "###-###-####";
    private static final String EMAIL_MSG = "Invalid email";

    // phone number validation
    public static boolean isPhoneNumber(EditText editText) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG);
    }

    // email validation
    public static boolean isEmailAddress(EditText editText) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // editText is blank, return false
        if (!hasText(editText)) {
            return false;
        }

        // pattern doesn't match, return false
        if (!Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    // checks if input field has text
    // returns true if it contains text, otherwise false
    public static boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();

        // clear error, if it was previously set by some other values
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }

}

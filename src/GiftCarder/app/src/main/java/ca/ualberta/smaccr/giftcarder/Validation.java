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

/* References:
 *
 * Email regular expression:
 * Adam Duvander,
 * http://www.webmonkey.com/2008/08/four_regular_expressions_to_check_email_addresses/,
 * retrieved 11/02/15
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
 * Edited by Spencer on 11/4/2014.
 */
public class Validation {

    // Regular Expression
    // Following code from
    // http://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    private static final String PHONE_REGEX = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
    // private static final String PHONE_REGEX = "^[2-9]\\d{2}-\\d{3}-\\d{4}$";
    private static final String EMAIL_REGEX = ".+\\@.+\\..+"; // only checks for xxxx@yyyy.zzz

    // Error Messages
    private static final String REQUIRED_MSG = "Field cannot be left blank";
    private static final String USERNAME_MSG = "Username already taken";
    private static final String PHONE_MSG = "Invalid phone number";
    private static final String EMAIL_MSG = "Invalid email";

    // phone number validation
    public static boolean isPhoneNumber(EditText editText) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG);
    }

    // email validation
    public static boolean isEmailAddress(EditText editText) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG);
    }

    /**
     * Returns true if the input field is valid, based on the parameter passed
     * @param editText input field
     * @param regex String regular expression
     * @param errMsg String error message to display
     * @return boolean
     */
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

    /**
     * Returns true if username is unique
     * @param editText input field with username
     * @param userList user list to search through
     * @return boolean
     */
    public static boolean uniqueUsername(EditText editText, UserList userList) {
        String username = editText.getText().toString().trim();
        editText.setError(null);

        if (!hasText(editText)) {
            return false;
        }

        if (userList.isEmpty()) {
            return true;
        }

        for (int i = 0; i < userList.getSize(); i+=1) {
            if (userList.getUsername(i).equals(username)) {
                editText.setError(USERNAME_MSG);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if input field has text.  Returns true if it contains text, otherwise false.
     * @param editText input field to check
     * @return boolean
     */
    //
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

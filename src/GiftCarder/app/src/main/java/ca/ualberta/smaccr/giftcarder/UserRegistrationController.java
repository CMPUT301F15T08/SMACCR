package ca.ualberta.smaccr.giftcarder;

import android.widget.EditText;

/**
 * Created by Carin on 10/26/2015.
 */
public class UserRegistrationController {

    // Lazy singleton
    private static UserList userList = null;
    static public UserList getUserList() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    // adds user to UserList
    public void addUser(EditText etUsername, EditText etCity, EditText etPhone,
                        EditText etEmail) {
        User user = new User();

        String username = convertToString(etUsername);
        String city = convertToString(etCity);
        String phone = convertToString(etPhone);
        String email = convertToString(etEmail);

        user.addUsername(username);
        user.addCity(city);
        user.addPhone(phone);
        user.addEmail(email);

        getUserList().addUser(user);
    }

    // gets user with given username
    public User getUser(EditText editText) {
        String username = convertToString(editText);

        return getUserList().getUser(username);
    }
    
    // converts EditText to String
    public String convertToString(EditText editText) {
        return editText.getText().toString().trim();
    }

    // validates text fields (make sure that content exists and it is the correct format
    public boolean validateFields(EditText etUsername, EditText etCity, EditText etPhone,
                                  EditText etEmail) {
        boolean valid = true;

        if (!Validation.hasText(etUsername)) {
            valid = false;
        }

        if (!Validation.hasText(etCity)) {
            valid = false;
        }

        if (!Validation.hasText(etPhone)) {
            valid = false;
        }

        if (!Validation.hasText(etEmail)) {
            valid = false;
        }

        if (!Validation.uniqueUsername(etUsername, getUserList())) {
            valid = false;
        }

        if (!Validation.isPhoneNumber(etPhone)) {
            valid = false;
        }

        if (!Validation.isEmailAddress(etEmail)) {
            valid = false;
        }

        return valid;

    }
}

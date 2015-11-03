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

    // checks to see if user is registered in system
    public boolean checkForUser(String username) {
        if (getUserList().isEmpty()) {
            return false;
        }


        for (int i = 0; i < userList.getSize(); i+=1) {
            if (userList.getUsername(i).equals(username)) {
                return true;
            }
        }

        return false;
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

    // edits user in UserList
    public void editUser(String username, EditText etCity, EditText etPhone, EditText etEmail) {
        User user = getUser(username);

        String city = convertToString(etCity);
        String phone = convertToString(etPhone);
        String email = convertToString(etEmail);

        user.addCity(city);
        user.addPhone(phone);
        user.addEmail(email);
    }

    // gets user with given username
    public User getUser(String username) {
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

    // validates edited text fields (make sure that content exists and it is the correct format
    public boolean validateEditedFields(EditText etCity, EditText etPhone, EditText etEmail) {
        boolean valid = true;

        if (!Validation.hasText(etCity)) {
            valid = false;
        }

        if (!Validation.hasText(etPhone)) {
            valid = false;
        }

        if (!Validation.hasText(etEmail)) {
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

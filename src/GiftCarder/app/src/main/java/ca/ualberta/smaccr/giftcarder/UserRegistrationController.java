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

    /**
     * Checks to see if user is registered in the system
     *
     * @param       username String
     * @return      boolean
     */
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

    /**
     * Adds user to UserList
     *
     * @param       etUsername EditText field for entering username
     * @param       etCity EditText field for entering city
     * @param       etPhone EditText field for entering phone
     * @param       etEmail EditText field for entering email
     */
    public void addUser(EditText etUsername, EditText etCity, EditText etPhone,
                        EditText etEmail) {
        User user = new User();

        String username = convertToString(etUsername);
        String city = convertToString(etCity);
        String phone = convertToString(etPhone);
        String email = convertToString(etEmail);


        user.setUsername(username);
        user.setCity(city);
        user.setPhone(phone);
        user.setEmail(email);

        getUserList().addUser(user);
    }

    /**
     * Edits user in UserList
     *
     * @param       username String
     * @param       etCity EditText field for entering city
     * @param       etPhone EditText field for entering phone
     * @param       etEmail EditText field for entering email
     */
    public void editUser(String username, EditText etCity, EditText etPhone, EditText etEmail) {
        User user = getUser(username);

        String city = convertToString(etCity);
        String phone = convertToString(etPhone);
        String email = convertToString(etEmail);

        user.setCity(city);
        user.setPhone(phone);
        user.setEmail(email);
    }

    /**
     * Returns user with given username
     *
     * @param       username String
     * @return      User
     */
    public User getUser(String username) {
        return getUserList().getUser(username);
    }

    /**
     * Converts EditText to String
     *
     * @param       editText EditText field
     * @return      String
     */
    public String convertToString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Clears user list (for testing)
     */
    public void clearUsers() {
        getUserList().clearList();
    }

    /**
     * Validates text fields (make sure that content exists and it is the correct format)
     *
     * @param       etUsername EditText field for entering username
     * @param       etCity EditText field for entering city
     * @param       etPhone EditText field for entering phone
     * @param       etEmail EditText field for entering email
     * @return      boolean
     */
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

    /**
     * Validates edited text fields (make sure that content exists and it is the correct format
     *
     * @param       etCity EditText field for entering city
     * @param       etPhone EditText field for entering phone
     * @param       etEmail EditText field for entering email
     * @return      boolean
     */
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

package ca.ualberta.smaccr.giftcarder;

import android.util.Log;
import android.widget.EditText;

/**
 * Created by Carin on 10/26/2015.
 */
public class UserRegistrationController {

    private UserListController ulc;

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

    ///////////////////////////////////////////////////////////////////////////////
    // Added by Richard, to get position of user in userlist
    public int returnUserPosition(String username) {

        for (int i = 0; i < getUserList().getSize(); i+=1) {
            if (getUserList().getUsername(i).equals(username)) {
                return i;
            }
        }

        // No user, so return invalid index
        return -1;
    }

    // Added by Richard to modify the user in userlist
    public void editUserInventory(String username, Inventory inv){
        User user = getUser(username);
        int userIndex = returnUserPosition(username);

        // if user index is valid
        if (userIndex != -1) {
            user.setInv(inv);
            getUserList().editUser(userIndex, user);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

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

        ulc = new UserListController(userList);
        Thread thread = new AddThread(user);
        thread.start();
    }

    class AddThread extends Thread {
        private User user;

        public AddThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            ulc.addUser(user);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        int userIndex = returnUserPosition(username);

        // if user index is valid
        if (userIndex != -1) {
            String city = convertToString(etCity);
            String phone = convertToString(etPhone);
            String email = convertToString(etEmail);

            user.setCity(city);
            user.setPhone(phone);
            user.setEmail(email);

            getUserList().editUser(userIndex, user);
        }
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

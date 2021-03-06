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

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by Carin on 10/26/2015.
 */

/* UserRegistrationController handles adding and editing users to the server and to the cache */
public class UserRegistrationController {

    private UserListController ulc;
    private Activity parentActivity;

    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            parentActivity.finish();
        }
    };

    public UserRegistrationController() {
        this.ulc = ulc;
    }

    public UserRegistrationController(Activity parentActivity) {
        this.ulc = ulc;
        this.parentActivity = parentActivity;
    }

    public static void setUserList(UserList userList) {
        UserRegistrationController.userList = userList;

    }

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

    public int returnUserPosition(String username) {

        for (int i = 0; i < getUserList().getSize(); i+=1) {
            if (getUserList().getUsername(i).equals(username)) {
                return i;
            }
        }

        // No user, so return invalid index
        return -1;
    }

    public void editUserInventory(String username, Inventory inv){
        User user = getUser(username);
        int userIndex = returnUserPosition(username);

        // if user index is valid
        if (userIndex != -1) {
            user.setInv(inv);
            getUserList().editUser(userIndex, user);
        }
    }

    //This edits the user friendlist in the singleton
    public void editUserFriendList(String username, FriendList fl){
        User user = getUser(username);
        int userIndex = returnUserPosition(username);

        // if user index is valid
        if (userIndex != -1) {
            user.setFl(fl);
            getUserList().editUser(userIndex, user);
        }
    }

    public void editUserTradeList(String username, TradesList tradesList) {
        User user = getUser(username);
        int userIndex = returnUserPosition(username);

        if (userIndex != -1) {
            user.setTradesList(tradesList);
            getUserList().editUser(userIndex, user);
        }
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

        ulc = new UserListController(userList);
        Thread thread = new AddThread(user);
        thread.start();
    }

    public void addUser(User user) {
        getUserList().addUser(user);
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

            parentActivity.runOnUiThread(doFinishAdd);
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

            ulc = new UserListController(userList);
            Thread thread = new UpdateThread(user);
            thread.start();
        }
    }

    // Deletes user on server, and write back new modified user
    class UpdateThread extends Thread {
        private User userthread;
        // UserRegistrationController uc= new UserRegistrationController();

        public UpdateThread(User user) {
            this.userthread = user;
        }

        @Override
        public void run() {
            // delete from server
            ulc.deleteUser(userthread.getUsername());
            // delete from userlist
            // uc.getUserList().deleteUser(user);

            // Add the new one back
            ulc.addUser(userthread);
            // uc.getUserList().addUser(user);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Edits user in UserList taking User as parameter
     *
     * @param       username String
     */
    public void editUser(String username, User editedUser) {
        int userIndex = returnUserPosition(username);

        // if user index is valid
        if (userIndex != -1) {
            getUserList().editUser(userIndex, editedUser);
        }

        ulc = new UserListController(userList);
        Thread thread = new UpdateThread(editedUser);
        thread.start();
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

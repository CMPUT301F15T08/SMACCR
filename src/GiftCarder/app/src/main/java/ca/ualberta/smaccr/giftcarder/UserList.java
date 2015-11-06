package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carin on 10/26/2015.
 */
public class UserList {
    protected List<User> userList = new ArrayList<User>();

    /**
     * Adds new user
     * @param user new user
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Gets user given a username
     * @param username String
     */
    public User getUser(String username) {

        for (User user : userList ) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Gets username of User at given index
     * @param index integer
     */
    public String getUsername(int index) {
        return userList.get(index).getUsername();
    }

    /**
     * Checks to see if the list is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return userList.isEmpty();
    }

    /**
     * Returns size of list
     * @return int
     */
    public int getSize() {
        return userList.size();
    }

    /**
     * Clears user list (for UI testing)
     */
    public void clearList() {
        if (!userList.isEmpty()) {
            userList.clear();
        }
    }

}

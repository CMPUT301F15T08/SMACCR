package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carin on 10/26/2015.
 */
public class UserList {

    protected List<User> userList = new ArrayList<User>();

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUser(String userName) {

        for (User user : userList ) {
            if (user.getUsername() == userName) {
                return user;
            }
        }

        return null;
    }

    // gets username of User at given index
    public String getUsername(int index) {
        return userList.get(index).getUsername();
    }

    public boolean isEmpty() {
        return userList.isEmpty();
    }

    public int getSize() {
        return userList.size();
    }

    public void clearList() {
        if (!userList.isEmpty()) {
            userList.clear();
        }
    }

}

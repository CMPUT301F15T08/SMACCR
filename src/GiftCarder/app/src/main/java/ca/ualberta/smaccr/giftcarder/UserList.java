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

    public boolean isEmpty(){
        return userList.isEmpty();
    }

}

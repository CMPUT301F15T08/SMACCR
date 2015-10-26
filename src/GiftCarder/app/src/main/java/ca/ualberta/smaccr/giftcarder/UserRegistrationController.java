package ca.ualberta.smaccr.giftcarder;

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

    public void addUser(String username, String city, String phone, String email) {
        User user = new User();
        user.addUsername(username);
        user.addCity(city);
        user.addPhone(phone);
        user.addEmail(email);

        getUserList().addUser(user);
    }
}

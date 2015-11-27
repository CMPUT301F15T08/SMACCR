package ca.ualberta.smaccr.giftcarder;

import java.util.HashMap;

/**
 * Created by ali on 15-11-25.
 */
public class TradesList extends HashMap<String, Trade> {

    public TradesList(User user) {
        UserRegistrationController userRegistrationController = new UserRegistrationController();
        userRegistrationController.getUser(user.getUsername());
    }

    // I (Spencer) created this default constructor to deal with an error User.java
    // Full disclosure, I don't know what the purpose of this class is so this is a band-aid fix.
    public TradesList() {

    }

}

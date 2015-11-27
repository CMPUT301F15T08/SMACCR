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

}

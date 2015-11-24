package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carin on 10/25/2015.
 */
public class User {
    private String username;
    private String city;
    private String phone;
    private String email;
    private Inventory inv;
    private FriendList fl;
    private List<FriendRequest> friendRequests;
    private TradesList tradesList;



    /**
     * Constructor: on creation of User, creates new inventory
     */

    public User() {
        this.inv = new Inventory();

        this.fl = new FriendList();
        this.tradesList = new TradesList();

        this.friendRequests = new ArrayList<FriendRequest>();
    }


    /**
     * Sets username
     * @param  username String
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * Sets city
     * @param  city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets phone
     * @param  phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets email
     * @param  email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets city
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets phone
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets inventory
     * @return String
     */
    public Inventory getInv() {return this.inv;}

    /**
     * Sets inventory
     * @param inventory Inventory of user's items
     */
    public void setInv(Inventory inventory) {this.inv = inventory;}

    public FriendList getFl() {return fl;}

    public void setFl(FriendList fl) {this.fl = fl;}

    /**
     * Add friend request
     * @param  sender String, reciever String
     */
    public void addFriendRequest(String sender, String reciever) {
        FriendRequest request = new FriendRequest(sender, reciever);
        this.friendRequests.add(request);
    }

    public TradesList getTradesList() {
        return tradesList;
    }

    public void setTradesList(TradesList tradesList) {
        this.tradesList = tradesList;
    }

}

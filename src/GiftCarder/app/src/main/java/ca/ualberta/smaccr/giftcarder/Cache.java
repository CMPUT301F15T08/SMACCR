package ca.ualberta.smaccr.giftcarder;
/*
collection of inventories
display order based on date
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by mrijlaar on 10/27/15.
 */
public class Cache {
    private UserRegistrationController urc;
    private UserListController ulc;
    private LinkedList<GiftCard> items;
    private Date lastUpdated;
    private UserList friends;
    private User friend;
    private ESUserManager esm;

    public Cache(){
        items = new LinkedList();
        lastUpdated = new Date();
        friends = new UserList();
        esm = new ESUserManager("");
    }

    public Cache(Collection<GiftCard> col){
        this.items = new LinkedList<GiftCard>(col);
        lastUpdated = new Date();
    }

    public LinkedList<GiftCard> getItems() {
        return items;
    }

    public void setItems(LinkedList<GiftCard> giftCards) {
        this.items = giftCards;
    }

    /*
    * add
    * add one giftcard to the cache
    * @Giftcard
    */
    public void add(GiftCard giftCard){
        this.items.addFirst(giftCard);
    }

    /*
    * add
    * add a collection of of Giftcards to the cache
    * @Collection<Giftcard>
    */
    public void add(Collection<GiftCard> col){
        Iterator<GiftCard> iterator = col.iterator();
        GiftCard gc;
        while (iterator.hasNext()){
            gc = iterator.next();
            if(!items.contains(gc)){
                items.add(gc);
            }
        }
        updateDate();
    }

    /*
    * add
    * add all the giftcards in an inventory to a cache
    * @Inventory
    * void
    * */
    public void add(Inventory inv){
        add(inv.getInvList());
    }

    private void updateDate(){
        this.lastUpdated = new Date();
    }

    /*
    * size
    * returns the number of giftcards in the cache
    * none
    * return int
    * */
    public int size(){
        return items.size();
    }

    public void updateFriends(ArrayList<String> friendList) {
        urc = new UserRegistrationController();
        ulc = new UserListController(urc.getUserList());
        for (int i = 0; i < friendList.size(); i++) {
            updateFriendThread uft = new updateFriendThread(friendList.get(i));
            new Thread(uft).start();
            if (uft.userFriend != null) {
                friends.addUser(uft.getFriend());
                urc.addUser(uft.getFriend());
            }
        }
    }

    public UserList getFriends() {
        return friends;
    }

    // gets friends info
    class updateFriendThread extends Thread {
        private String tfriend;
        private User userFriend;


        public updateFriendThread(String friend) {
            this.tfriend = friend;
        }

        @Override
        public void run() {

            userFriend = esm.getUser(tfriend);
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public User getFriend(){
            return userFriend;
        }
    }

}

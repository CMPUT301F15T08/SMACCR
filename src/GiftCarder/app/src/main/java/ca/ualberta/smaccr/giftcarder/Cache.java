package ca.ualberta.smaccr.giftcarder;
/*
collection of inventories
display order based on date
 */

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mrijlaar on 10/27/15.
 *
 * Holds the inventories of all Friends             Maybe hold all friend objects of type User
 * pulls Items out of inventories in chronological order so most recent is diplayed on the top
 * copies that list into another list to be further refined
 * iterates through items
 */
public class Cache {


    private static ArrayList<User> friends = null;
    private ArrayList<GiftCard> items;// all the items owned by all friends, in order by most recent date
    private ArrayList<GiftCard> results;// results of searches
    private Date lastUpdated;

    UserRegistrationController urc;
    String username;

    public Cache(Activity parentActivity, String username) {
        this.parentActivity = parentActivity;
        this.urc = new UserRegistrationController();
        this.username = username;

        getFriends();
        //this.friends = new ArrayList<User>();
        this.lastUpdated = new Date();
    }

    //lazy singleton
    static public ArrayList<User> getFriends() {
        if (friends==null){
            friends = new ArrayList<User>();
        }
        return friends;
    }

    // Lazy singleton
    private static UserList userList = null;
    static public UserList getUserList() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public User getUser(String username) {
        for (User user : getFriends() ) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<GiftCard> getItems() {
        return items;
    }

    public void loadItems() {

        ArrayList<ArrayList<GiftCard>> fInvItems = assemble();

        this.items = mergeSort(fInvItems);
    }

    /**
     * turns finv from arraylist of inventories to arraylist of arraylists of items
     *
     * @return
     */
    private ArrayList<ArrayList<GiftCard>> assemble() {

        ArrayList<ArrayList<GiftCard>> fInvItems = new ArrayList<ArrayList<GiftCard>>();

        Iterator<User> itr = getFriends().listIterator();
        User user;

        while (itr.hasNext()) {
            user = itr.next();
            fInvItems.add(hidePrivate(user.getInv().getInvList()));
        }

        return fInvItems;
    }

    /**
     * Returns all public giftcards and no private ones
     *
     * @param giftCards
     * @return publicGiftcards
     */
    private ArrayList<GiftCard> hidePrivate(ArrayList<GiftCard> giftCards) {
        ArrayList<GiftCard> ret = new ArrayList<GiftCard>();

        Iterator<GiftCard> iterator = giftCards.iterator();
        GiftCard giftCard;
        while (iterator.hasNext()) {
            giftCard = iterator.next();
            if (giftCard.getShared()) {
                ret.add(giftCard);
            }
        }

        return ret;
    }

    private ArrayList<GiftCard> mergeSort(ArrayList<ArrayList<GiftCard>> alAlGc) {

        if (alAlGc.size() == 1) return alAlGc.get(0);
        if (alAlGc.size() < 1) return new ArrayList<GiftCard>();

        ArrayList<ArrayList<GiftCard>> alAlGc2 = new ArrayList<ArrayList<GiftCard>>();

        Iterator itr = alAlGc.listIterator();
        ArrayList<GiftCard> p1, p2;

        while (itr.hasNext()) {
            p1 = (ArrayList<GiftCard>) itr.next();

            if (itr.hasNext()) {
                p2 = (ArrayList<GiftCard>) itr.next();
                alAlGc2.add(merge(p1, p2));
            } else {
                alAlGc2.add(p1);
            }
        }

        return mergeSort(alAlGc2);
    }

    private ArrayList<GiftCard> merge(ArrayList<GiftCard> al1, ArrayList<GiftCard> al2) {

        ArrayList<GiftCard> retAl = new ArrayList<GiftCard>();

        int s1 = 0, s2 = 0;
        Boolean end1 = Boolean.FALSE;
        Boolean end2 = Boolean.FALSE;
        GiftCard gc1, gc2;

        for (int i = 0; i < (al1.size() + al2.size()); i++) {
            if (s1 >= al1.size()) {
                end1 = Boolean.TRUE;
                break;
            }

            if (s2 >= al2.size()) {
                end2 = Boolean.TRUE;
                break;
            }

            gc1 = al1.get(s1);
            gc2 = al2.get(s2);

            if (gc1.getDate().before(gc2.getDate())) {
                retAl.add(gc1);
                s1++;
            } else {
                retAl.add(gc2);
                s2++;
            }
        }

        if (end1 || end2) {

            Iterator itr;
            if (end2) {
                itr = al1.listIterator(s1);
            } else {
                itr = al2.listIterator(s2);
            }

            while (itr.hasNext()) {
                retAl.add((GiftCard) itr.next());
            }
        }

        return retAl;
    }

    public void add(User user){
        getFriends().add(user);
    }

    public void setItems(ArrayList<GiftCard> giftCards) {
        this.items = giftCards;
    }

    private void updateDate() {
        this.lastUpdated = new Date();
    }

    /*
    * size
    * returns the number of giftcards in the cache()
    * none
    * return int
    * */
    public int itemsSize() {
        try {
            return items.size();
        } catch (NullPointerException e) {
            loadItems();
            return items.size();
        }
    }

    public int resultsSize() {
        try {
            return results.size();
        } catch (NullPointerException e) {
            browseAll();
            return results.size();
        }
    }


    public ArrayList<GiftCard> getResults() {
        return results;
    }

    public void browseAll() {
        loadItems();
        results = new ArrayList<GiftCard>(items);
    }

    public void browseCategory(Integer cat) {
        if (cat < 0 || cat > 10) {
            throw new IndexOutOfBoundsException();
        }

        if (results == null) browseAll();

        if (cat == 0) {
            browseAll();
            return;
        }

        Iterator iterator = results.listIterator();
        GiftCard giftCard;
        while (iterator.hasNext()) {
            giftCard = (GiftCard) iterator.next();
            if (giftCard.getCategory() != cat) iterator.remove();
        }
    }

    public void browseSearch(String query) {
        GiftCard giftCard;
        Iterator<GiftCard> iterator = results.iterator();
        while (iterator.hasNext()) {
            giftCard = iterator.next();
            if (!gCContains(giftCard, query)) {
                iterator.remove();
            }
        }
    }

    private Boolean gCContains(GiftCard giftCard, String query) {
        Boolean ret = Boolean.FALSE;
        double val, lb, ub;
        boolean parsable = true;

        String[] terms = query.split(" ");

        for (int i = 0; i < terms.length; i++) {

            if (giftCard.getMerchant().matches("(.*)" + terms[i] + "(.*)")) {
                return Boolean.TRUE;
            }

            val = -10;// will be less than lower bounds
            try {
                val = Double.valueOf(terms[i]);
            } catch (NumberFormatException e) {
                parsable = false;
            }
            if (parsable) {
                lb = giftCard.getValue() / 2;
                ub = giftCard.getValue() * 2;
                if (val < ub && val > lb) {
                    return Boolean.TRUE;
                }
            }
            parsable = true;

        }
        return Boolean.FALSE;
    }

    public void updateFriends() {
        if (urc==null)Log.e("1------------", "urc is null");
        if (urc.getUser(username)==null)Log.e("2------------", "urc.getUser(username) is null");
        if (urc.getUser(username).getFl()==null)Log.e("3------------", "urc.getUser(username).getFl() is null");
        if (urc.getUser(username).getFl().getFriendList()==null)Log.e("4------------", "urc.getUser(username).getFl().getFriendList() is null");

        ArrayList<String> friendsNames = urc.getUser(username).getFl().getFriendList();
        ArrayBlockingQueue<User> queue = new ArrayBlockingQueue<User>(friendsNames.size());
        User temp = null;

        getFriends().clear();

        String friendUserName;
        Iterator<String> iterator = friendsNames.iterator();

        while (iterator.hasNext()) {
            friendUserName = iterator.next();

            Thread thread = new GetFriendThread(friendUserName, queue);
            thread.start();

            try {
                temp = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getFriends().add(temp);
        }

    }

    /*public User getOwner(GiftCard giftCard){
        User potOwner;
        Iterator<User> iterator = getFriends().iterator();
        while (iterator.hasNext()){
            potOwner = iterator.next();
            if (potOwner.isOwner(giftCard)){
                return potOwner;
            }
        }

        Log.e("Cache.getOner error: ", "Could not Find owner");
        return null;
    }*/

    //Richards evil code below

    private UserListController ulc;
    private Activity parentActivity;

    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            parentActivity.finish();
        }
    };

    //Parameter for potietnial Friend on server
    User friendUser;

    public Cache() {
    }

    //Check friend is on server or not, if is send friend request, for now add to friendlist
    class GetFriendThread extends Thread {
        private String id;
        private ArrayBlockingQueue<User> userArrayBlockingQueue;

        public GetFriendThread(String id, ArrayBlockingQueue<User> arrayBlockingQueue) {
            this.id = id;
            this.userArrayBlockingQueue = arrayBlockingQueue;
        }

        @Override
        public void run() {
            ESUserManager userManager = new ESUserManager("");


            friendUser = userManager.getUser(id);
            if (friendUser != null) {
                try {
                    userArrayBlockingQueue.put(friendUser);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
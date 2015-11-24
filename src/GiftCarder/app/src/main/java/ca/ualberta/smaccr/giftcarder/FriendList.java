package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-11-21.
 */
public class FriendList {
    private ArrayList<String> friendList;
    //Cache myCache;

    public FriendList() {
        //Array list of string of friends
        this.friendList = new ArrayList<String>();
        //this.myCache = new Cache();

    }

    //Add friend to top of list, so we can use position on onclick to delete position
    public void addNewFriend(String friendusername){
        this.friendList.add(0, friendusername);
    }

    //Delete friend by name
    public void deleteOldFriendName(String friendusername){this.friendList.remove(friendusername);}


    //Delete friend by index
    public void deleteOldFriendIndex(int i){
        this.friendList.remove(i);
    }

    public boolean containsFriend(String friendusername){
        return this.friendList.contains(friendusername);
    }


    //Getter, Setter
    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }


}

package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-11-21.
 */
public class FriendList {

    ArrayList<String> friendList = new ArrayList<String>();

    public void addNewFriend(String friendusername){
        this.friendList.add(0,friendusername);
    }

    public void deleteOldFriendName(String friendusername){this.friendList.remove(friendusername);}

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

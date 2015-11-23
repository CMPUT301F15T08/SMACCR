package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;

/**
 * Created by ConnorSheremeta on 2015-11-23.
 */
public class FriendRequestList {

    //Array list of strings of usernames
    ArrayList<String> friendRequestList = new ArrayList<String>();

    //Add friend request to top of list to use position of username
    public void addNewFriend(String friendusername){
        this.friendRequestList.add(0,friendusername);
    }

    //Delete request by username
    public void deleteOldFriendName(String friendusername){this.friendRequestList.remove(friendusername);}


    //Delete friend request by index
    public void deleteOldFriendIndex(int i){
        this.friendRequestList.remove(i);
    }

    public boolean containsFriendRequestFrom(String friendusername){
        return this.friendRequestList.contains(friendusername);
    }


    //Getter, Setter
    public ArrayList<String> getFriendList() {
        return friendRequestList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendRequestList = friendList;
    }


}



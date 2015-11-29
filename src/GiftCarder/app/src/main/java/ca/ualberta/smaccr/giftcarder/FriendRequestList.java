/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

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



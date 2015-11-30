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
 * Created by Richard on 2015-11-21.
 */

/*
FriendList Class is responsible for add/Deleting Friends which controls an arraylist of string that are references to users, as users are
identified by their username which is their unique id which is a string.
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

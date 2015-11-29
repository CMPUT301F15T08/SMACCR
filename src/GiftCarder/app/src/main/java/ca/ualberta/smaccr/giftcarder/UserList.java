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

/*
Student Picker: Randomly pick students to answer questions

Copyright (C) 2015 Carin Li Abram Hindle abram.hindle@softwareprocess.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package ca.ualberta.smaccr.giftcarder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carin on 10/26/2015.
 */
public class UserList implements Observable {

    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t08/user/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t08/user/";

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.notifyUpdated(this);
        }
    }

    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    public String getSearchUrl() {
        return SEARCH_URL;
    }

    /**
     * Java wants this, we don't need it for Gson/Json
     */
    private static final long serialVersionUID = 3199561696102797345L;

    protected List<User> userList = new ArrayList<User>();

    /**
     * Adds new user
     * @param user new user
     */
    public void addUser(User user) {
        userList.add(user);
    }

    public void deleteUser(User user){
        userList.remove(user);
    }

    /**
     * Edits user given its index in the list
     * @param userIndex integer of user's index in list
     * @param user User
     */
    public void editUser(int userIndex, User user){
        userList.set(userIndex, user);
    }

    /**
     * Gets user given a username
     * @param username String
     */
    public User getUser(String username) {
        for (int i=0;i<userList.size();i++){
            if (userList.get(i).getUsername().equals(username)) return userList.get(i);


        }
        return null;
    }

    /**
     * Gets username of User at given index
     * @param index integer
     */
    public String getUsername(int index) {
        return userList.get(index).getUsername();
    }

    /**
     * Checks to see if the list is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return userList.isEmpty();
    }

    /**
     * Returns size of list
     * @return int
     */
    public int getSize() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Clears user list (for UI testing)
     */
    public void clearList() {
        if (!userList.isEmpty()) {
            userList.clear();
        }

    }
}

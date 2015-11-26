package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/16/15.
 */

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class UserListController {
    private Gson gson = new Gson();
    private UserList userlist;
    private static final String TAG = "UserListController";


    public UserListController(UserList userlist) {
        super();
        this.userlist = userlist;
    }

    /**
     * Adds a new user
     */
    public void addUser(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(userlist.getResourceUrl() + user.getUsername());
            //System.out.println(gson.toJson(user));
            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the user with the specified id
     */
    public void deleteUser(String userId) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpDelete deleteRequest = new HttpDelete(userlist.getResourceUrl() + userId);
            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserlist(UserList userlist) {
        this.userlist = userlist;
    }

    public UserList getUserlist() {
        return userlist;

    }
}

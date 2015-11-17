package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/16/15.
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.smaccr.giftcarder.Hits;
import ca.ualberta.smaccr.giftcarder.SearchHit;
import ca.ualberta.smaccr.giftcarder.SearchResponse;
import ca.ualberta.smaccr.giftcarder.SimpleSearchCommand;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ESUserManager {

    private static final String TAG = "UserSearch";
    private Gson gson;
    private UserList userlist = new UserList();

    public UserList getUserList() {
        return userlist;
    }

    public ESUserManager(String search) {
        gson = new Gson();
        // searchUserList(search, null);
    }

    /**
     * Get a movie with the specified id
     */
    public User getUser(String id) {
        SearchHit<User> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(userlist.getResourceUrl() + id);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<User>>() {}.getType();

        try {
            sr = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchHitType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sr.getSource();

    }

    /**
     * Get userlist with the specified search string. If the search does not
     * specify fields, it searches on all the fields.
     */
    public UserList searchUserList(String searchString, String field) {
        UserList result = new UserList();

        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost(userlist.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<User>>() {
        }.getType();

        SearchResponse<User> esResponse;
        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Extract the userlist from the esResponse and put them in result

        for (SearchHit<User> hit : esResponse.getHits().getHits()) {
            result.addUser(hit.getSource());
        }

        result.notifyObservers();

        return result;
    }
}


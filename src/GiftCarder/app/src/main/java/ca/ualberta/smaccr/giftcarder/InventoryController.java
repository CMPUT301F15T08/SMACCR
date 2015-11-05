package ca.ualberta.smaccr.giftcarder;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by splant on 11/4/15.
 */

// Adapted from CMPUT 301 lab on ElasticSearch, from 'MoviesContoller.java'
public class InventoryController {
    private Gson gson = new Gson();
    private Inventory inv;
    private static final String TAG = "InventoryController";


    public InventoryController(Inventory inv) {
        super();
        this.inv = inv;
    }

    /**
     * Adds a new GiftCard
     */
    public void addMovie(GiftCard gc) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(inv.getResourceUrl() + movie.getId());

            StringEntity stringEntity = new StringEntity(gson.toJson(movie));
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
     * Deletes the movie with the specified id
     */
    public void deleteMovie(int movieId) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpDelete deleteRequest = new HttpDelete(movies.getResourceUrl() + movieId);
            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

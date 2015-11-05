package ca.ualberta.smaccr.giftcarder;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by splant on 11/4/15.
 */

// Heavily derived from CMPUT 301 ElasticSearch lab
public class ESGiftCardManager {

    private static final String TAG = "GiftCardSearch";
    private Gson gson;
    private Inventory inv = new Inventory();

    public Inventory getInventory() {
        return inv;
    }

    public ESGiftCardManager(String search) {
        gson = new Gson();
        // searchMovies(search, null);
    }

    /**
     * Get a movie with the specified id
     */
    public GiftCard getGiftCard(int id) {
        SearchHit<GiftCard> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(inv.getResourceUrl() + id);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<GiftCard>>() {}.getType();

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
     * Get movies with the specified search string. If the search does not
     * specify fields, it searches on all the fields.
     */
    public Inventory searchInventory(String searchString, String field) {
        Inventory result = new Inventory();

        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost(inv.getSearchUrl());

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
        Type searchResponseType = new TypeToken<SearchResponse<GiftCard>>() {
        }.getType();

        SearchResponse<GiftCard> esResponse;
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

        // Extract the movies from the esResponse and put them in result

        for (SearchHit<GiftCard> hit : esResponse.getHits().getHits()) {
            result.addGiftCard(hit.getSource());
        }

        result.notifyObservers();

        return result;
    }
}

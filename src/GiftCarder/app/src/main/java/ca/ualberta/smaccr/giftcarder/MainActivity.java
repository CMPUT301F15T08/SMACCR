package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class MainActivity extends Activity {

    public static Cache myCache = null;

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";

    Inventory inv;
    //String username;

    private ESUserManager userManager;

    //getter for UI testing
    public EditText getEtUsername() {return (EditText) findViewById(R.id.enterUsername);}
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        user.setUsername("t");
        user.setCity("Edmo");
        user.setPhone("012-345-6789");
        user.setEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);
        */

    }

    @Override
    protected void onStart() {
        super.onStart();

        userManager = new ESUserManager("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //Back button disabled
    }

    /**
     * Called when user presses Register button
     *
     * @param  view  view that is clicked
     */
    public void registerNewUser(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * IF internet Download a new cache
     * else load a saved one
     * else make a new one
     * @param username
     * @return
     */
    public void initCache(String username){

        if(NetworkChecker.isNetworkAvailable(this)) {// if connected downlioad new
            myCache = new Cache(this, username);
            myCache.updateFriends();
        }else {
            loadCacheFromFile(this);//if not connected load old.
        }

    }

    //lazysingleton
    public static Cache getCache(){
        if (myCache == null){
            throw new ExceptionInInitializerError();
        }

        return myCache;
    }

    /**
     * Called when user presses Login button.
     * <p>
     * Checks to see if user-entered username is an already registered user.  If not, it prompts
     * the user to register.
     *
     * @param  view  view that is clicked
     */
    public void logInUser(View view) {
        EditText etUsername = (EditText) findViewById(R.id.enterUsername);
        String username = etUsername.getText().toString().trim();

        UserRegistrationController urc = new UserRegistrationController(this);
        userManager = new ESUserManager("");



        // Try to retrieve the data. If no internet, pop up some toast to say so.
        if (!NetworkChecker.isNetworkAvailable(this)) {

            initCache(username);
            if (myCache == null){
                Toast.makeText(this, "Cache save error", Toast.LENGTH_LONG).show();
            }else {

                User owner = myCache.getOwner();
                if (owner.getUsername().equalsIgnoreCase(username)) {
                    urc.addUser(owner);
                    Intent intent = new Intent(this, AllActivity.class);
                    intent.putExtra(EXTRA_USERNAME, username);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();
                }
            }


        } else {
            if (Validation.hasText(etUsername)) {
                // Calls GetThreat to check if user is on server
                Thread thread = new GetThread(username);
                thread.start();
            }
        }
    }

    // Get thread to attempt to get user from server by id ->"this is his username"
    class GetThread extends Thread {
        private String id;

        public GetThread(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            user = userManager.getUser(id);
            runOnUiThread(checkUserOnServer);
        }
    }

    private Runnable checkUserOnServer = new Runnable() {
        public void run() {
            checkForUserOnServer(user);
        }
    };

    public void checkForUserOnServer(User user){
        UserRegistrationController urc = new UserRegistrationController(this);
        userManager = new ESUserManager("");

        //If the user exist then we start "all activity", and he gets added to singleton, he is only user in the singleton right now
        if (user != null) {
            try{
                getCache().setOwner(user);
            }catch (ExceptionInInitializerError e){
                myCache = new Cache(this, user.getUsername());
                myCache.setOwner(user);
            }
            saveCacheInFile(this);

            Toast.makeText(this, user.getUsername(), Toast.LENGTH_LONG).show();
            urc.addUser(user);
            String usernameFromServer = user.getUsername();
            Intent intent = new Intent(this, AllActivity.class);
            intent.putExtra(EXTRA_USERNAME, usernameFromServer);
            startActivity(intent);
        } else {
            Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();
        }

    }

    public void loadCacheFromFile(Activity activity){
        String FILENAME = "GiftCarderCache.sav";

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gsom = new Gson();
            // following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type cacheType = new TypeToken<CacheStore>() {}.getType();
            CacheStore cacheStore = gsom.fromJson(in, cacheType);
            this.myCache = cacheStore.getCache(this);

        } catch (FileNotFoundException e) {
            myCache = new Cache(this, user.getUsername());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveCacheInFile(Context context) {
        String FILENAME = "GiftCarderCache.sav";

        try {
            CacheStore cacheStore = new CacheStore(myCache);
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(cacheStore, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

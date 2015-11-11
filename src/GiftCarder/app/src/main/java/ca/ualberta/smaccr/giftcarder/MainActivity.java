package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity {
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";

    private static final String FILENAME = "giftcard_userlist.sav";

    //Arraylist to load/save to internal storage
    private ArrayList<User> records = new ArrayList<User>();

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

        //load the cache from internal storage
        loadFromFile();

        if (records == null) {
            throw new RuntimeException();
        }
        UserRegistrationController uc = new UserRegistrationController();
        uc.setSingletonUserList(records);
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

        UserRegistrationController urc = new UserRegistrationController();

        if (Validation.hasText(etUsername)) {
            if (urc.checkForUser(username)) {
                Intent intent = new Intent(this, InventoryActivity.class);
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent);

            } else {
                Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();

            }
        }
    }

    //Delete the cache file
    public void clearFile(View menu) {
        //delete reaction times for practice mode
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        boolean deleted = file.delete();
    }

        /*
    Using gson/json for file storage.

    loadFromFile(), saveInFile() is from "Joshua Charles Campbell" (joshua2ua) monday lab, retrieved 2015-09-21
     */

    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            records = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            records = new ArrayList<User>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

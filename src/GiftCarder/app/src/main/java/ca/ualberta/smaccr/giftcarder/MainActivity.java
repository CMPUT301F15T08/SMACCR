package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    private ESUserManager userManager;
    private User user;

    //getter for UI testing
    public EditText getEtUsername() {return (EditText) findViewById(R.id.enterUsername);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userManager = new ESUserManager("");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Toast.makeText(this, "No internet connection.  Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
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

    /**
     * Checks for user on server
     *
     * @param  user User
     */
    public void checkForUserOnServer(User user){
        UserRegistrationController urc = new UserRegistrationController(this);
        userManager = new ESUserManager("");

        //If the user exist then we start "all activity", and he gets added to singleton, he is only user in the singleton right now
        if (user != null) {
            //Toast.makeText(this, user.getUsername(), Toast.LENGTH_LONG).show();
            urc.addUser(user);
            String usernameFromServer = user.getUsername();
            Intent intent = new Intent(this, AllActivity.class);
            intent.putExtra(EXTRA_USERNAME, usernameFromServer);
            startActivity(intent);
        } else {
            Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();
        }

    }
}

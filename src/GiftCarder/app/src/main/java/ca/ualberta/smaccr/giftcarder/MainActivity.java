package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    Inventory inv;
    String username;

    private ESUserManager userManager;

    //getter for UI testing
    public EditText getEtUsername() {return (EditText) findViewById(R.id.enterUsername);}
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user.setUsername("t");
        user.setCity("Edmo");
        user.setPhone("012-345-6789");
        user.setEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);


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

        if (Validation.hasText(etUsername)) {
            /*
            if (urc.checkForUser(username)) {
                Intent intent = new Intent(this, AllActivity.class);
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent);

            } else {
                Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();
            }
            */

            Thread thread = new GetThread(username);
            thread.start();

            if (user != null) {
                if (urc.getUser(user.getUsername()) == null) {
                    urc.addUser(user);
                }
                Intent intent = new Intent(this, AllActivity.class);
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();
            }
        }
    }

    class GetThread extends Thread {
        private String id;

        public GetThread(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            user = userManager.getUser(id);
            // runOnUiThread(doUpdate);
        }
    }
}

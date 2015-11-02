package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void registerNewUser(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void logInUser(View view) {
        EditText etUsername = (EditText) findViewById(R.id.enterUsername);
        String username = etUsername.getText().toString().trim();

        UserRegistrationController urc = new UserRegistrationController();

        if (Validation.hasText(etUsername)) {
            if (urc.checkForUser(username)) {
            /* temporarily start UserProfile activity -> should start Inventory */
                Intent intent = new Intent(this, UserProfile.class);
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent);

            }else {
                Toast.makeText(this, "User not found. Register a new account.", Toast.LENGTH_LONG).show();

            }
        }
    }
}

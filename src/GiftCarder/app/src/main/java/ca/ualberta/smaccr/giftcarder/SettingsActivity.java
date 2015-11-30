package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {

    public final static String EXTRA_USERNAME= "ca.ualberta.smaccr.giftcarder.USERNAME";
    String username;
    Inventory inv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        username = intent.getStringExtra(AllActivity.EXTRA_USERNAME);

        TextView tvLoggedInAs = (TextView) findViewById(R.id.loggedInAsTextView);
        tvLoggedInAs.setText("Logged in as: " + username);

        // Disable no-downloads option
        CheckBox checkbox = (CheckBox) findViewById(R.id.downloadCheckBox);
        checkbox.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when user presses Logout button.
     * <p>
     * Exits to MainActivity
     *
     * @param  view  view that is clicked
     */
    public void onLogoutButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        UserRegistrationController urc = new UserRegistrationController();
        User user = urc.getUser(username);
        startActivity(intent);
    }

    /**
     * Called when user presses Edit Profile button.
     * <p>
     * Brings up UserProfileActivity
     *
     * @param  view  view that is clicked
     */
    public void onEditProfileButtonClick(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    public void onSyncAllButtonClick(View view) {
        Toast.makeText(this, "Sync All Button clicked", Toast.LENGTH_LONG).show();
    }

}

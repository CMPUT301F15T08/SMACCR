/* Hussain Akhtar Wahid 'Ghouri',
 * http://stackoverflow.com/questions/19452269/android-set-text-to-textview, retrieved 11/02/15
 */

package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserProfile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView tvUsername = (TextView) findViewById(R.id.usernameTextView);
        TextView tvCity = (TextView) findViewById(R.id.cityTextView);
        TextView tvPhone = (TextView) findViewById(R.id.phoneTextView);
        TextView tvEmail = (TextView) findViewById(R.id.emailTextView);

        User user = (User) getIntent().getExtras().getSerializable("user");

        tvUsername.setText("user.getUsername()");
        tvCity.setText("user.getCity()");
        tvPhone.setText("user.getPhone()");
        tvEmail.setText("user.getEmail()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
}

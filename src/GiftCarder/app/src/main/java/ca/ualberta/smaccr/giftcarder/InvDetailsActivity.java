package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InvDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_details);
        setTitle("Inventory Details");

    }

    @Override
    public void onResume() {
        super.onResume();
        // Get user using the app
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        UserRegistrationController urc = new UserRegistrationController();
        User user = urc.getUser(username);

        String invDetailsTitle = user.getUsername() + "'s Inventory";
        double totalValue = getTotalValue(user.getInv());
        String invDetailsValue = "Total Value of Cards: $" + totalValue;

        TextView detailsTitle = (TextView) findViewById(R.id.userInvDetTitleTextView);
        detailsTitle.setText(invDetailsTitle);
        TextView detailsValue = (TextView) findViewById(R.id.totalValueTextView);
        detailsValue.setText(invDetailsValue);
        TextView detailsNumberOfCards = (TextView) findViewById(R.id.ID_numberOfCards);
        detailsNumberOfCards.setText(String.valueOf(user.getInv().getSize()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inv_details, menu);
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

    double getTotalValue(Inventory inv) {
        double value = 0;
        for (int i = 0; i < inv.getInvList().size(); ++i) {
            value += inv.getInvList().get(i).getValue();
        }
        return value;
    }
}

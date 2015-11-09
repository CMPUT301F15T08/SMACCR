package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {

    Inventory inv;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button logOutButton = (Button) findViewById(R.id.logOutButton);

        inv = (Inventory)getIntent().getSerializableExtra("inventorySettings");
        username = (String)getIntent().getStringExtra("userSettings");
        Toast.makeText(getApplicationContext(), inv.getInvList().get(0).getMerchant(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_LONG).show();


        //tToast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //taken from: http://stackoverflow.com/questions/6330260/finish-all-previous-activities
                //user DArkO

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("inventorySettings1", inv);
                intent.putExtra("userSettings1", username);
                startActivity(intent);
            }
        });
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

}

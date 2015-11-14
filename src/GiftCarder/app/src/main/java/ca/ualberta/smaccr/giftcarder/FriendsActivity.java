package ca.ualberta.smaccr.giftcarder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FriendsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("Enter their username:");

            //textbox for user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    // Check if its a valid user, and send request
                    UserRegistrationController URC = new UserRegistrationController();
                    if (URC.checkForUser(value)){
                        Log.d("y","y");
                    }
                    else{
                        Log.d("n","n");
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    return;
                }
            });

            alert.show();
            return true;
        }

        if (id == R.id.action_delete) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

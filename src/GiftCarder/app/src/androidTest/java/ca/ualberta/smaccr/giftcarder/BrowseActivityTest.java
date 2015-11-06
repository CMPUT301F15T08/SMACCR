package ca.ualberta.smaccr.giftcarder;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * Created by mrijlaar on 11/5/15.
 */
public class BrowseActivityTest extends ActivityInstrumentationTestCase2 {

    public BrowseActivityTest(){
        super(ca.ualberta.smaccr.giftcarder.BrowseActivity.class);
    }

    public void testBrowseActivityTest() throws Exception{
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

        User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);

        final Button saveButton = activity.getGoButton();
        final String searchText = "Bestbuy";
        final EditText searchBar = activity.getSearchBar();
        final Spinner catSpinner = activity.getCatSpinner();
        final Cache cache = activity.getMyCache();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchBar.setText(searchText);
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(searchBar.getText().toString() == searchText);
    }
}
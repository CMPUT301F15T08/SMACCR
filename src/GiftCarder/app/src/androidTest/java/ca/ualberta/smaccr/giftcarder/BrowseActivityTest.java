package ca.ualberta.smaccr.giftcarder;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


/**
 * Created by mrijlaar on 11/5/15.
 */
public class BrowseActivityTest extends ActivityInstrumentationTestCase2 {

    public BrowseActivityTest() {
        super(ca.ualberta.smaccr.giftcarder.BrowseActivity.class);
    }

    public void testClickItem() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

     /*   User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);*/

        final Button goButton = activity.getGoButton();
        final String searchText = "Bestbuy";
        final EditText searchBar = activity.getSearchBar();
        final Spinner catSpinner = activity.getCatSpinner();
        final ListView listView = activity.getbrowseListID();
        final Cache cache = activity.getMyCache();

        User user = new User();
        Inventory inventory = user.getInv();
        GiftCard giftCard1 = new GiftCard(10.00, "Bestbuy", 1, 1, 6, "scratched but usable");
        inventory.addGiftCard(giftCard1);
        cache.add(user);
        cache.loadItems();

        //Code from: https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: 2015-11-05

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor itemActivityMonitor =
                getInstrumentation().addMonitor(ItemActivity.class.getName(),
                        null, false);

        assertTrue("Cache is empty: " + cache.getItems().size(), cache.getItems().size() > 0);
        assertTrue("Listview is empty: " + listView.getChildCount(), listView.getChildCount() > 0);

        // Validate that ReceiverActivity is started
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();


        ItemActivity itemActivity = (ItemActivity) itemActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", itemActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, itemActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", ItemActivity.class, itemActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(itemActivityMonitor);//*/
    }

    public void testTextSearch() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

     /*   User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);*/

        final Button goButton = activity.getGoButton();
        final String searchText = "Bestbuy";
        final EditText searchBar = activity.getSearchBar();
        final Spinner catSpinner = activity.getCatSpinner();
        final Cache cache = activity.getMyCache();

        User user = new User();
        Inventory inventory = user.getInv();
        GiftCard giftCard1 = new GiftCard(10.00, "Bestbuy", 1, 1, 6, "scratched but usable");
        inventory.addGiftCard(giftCard1);
        cache.add(user);
        cache.loadItems();


        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchBar.setText(searchText);
                goButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(searchBar.getText().toString() + "==" + searchText, searchBar.getText().toString().equals(searchText));

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchBar.setText("other");
                goButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(searchBar.getText().toString() + "==" + searchText, searchBar.getText().toString().equals("other"));

    }

    public void testCategorySearch() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

     /*   User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);*/

        final Button goButton = activity.getGoButton();
        final Spinner catSpinner = activity.getCatSpinner();
        final Cache cache = activity.getMyCache();

        User user = new User();
        Inventory inventory = user.getInv();
        GiftCard giftCard1 = new GiftCard(10.00, "Bestbuy", 1, 1, 6, "scratched but usable");
        inventory.addGiftCard(giftCard1);
        cache.add(user);
        cache.loadItems();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.updateBrowseList();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertTrue("" + cache.getResults().size(), cache.getResults().size() > 0);
        assertTrue("" + activity.getbrowseListID().getChildCount(), activity.getbrowseListID().getChildCount() > 0);

        assertTrue("childCount" + catSpinner.getChildCount(), true);
        activity.runOnUiThread(new Runnable() {
            public void run() {

                /*View v = catSpinner.getChildAt(0);
                catSpinner.performClick();
                catSpinner.performItemClick(catSpinner, 2, catSpinner.getId());*/
                catSpinner.setSelection(2);
                goButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue("" + cache.getResults().size(), cache.getResults().size() == 0);
        assertTrue("" + activity.getbrowseListID().getChildCount(), activity.getbrowseListID().getChildCount() == 0);

        assertTrue("childCount" + catSpinner.getChildCount(), true);
        activity.runOnUiThread(new Runnable() {
            public void run() {

                /*View v = catSpinner.getChildAt(0);
                catSpinner.performClick();
                catSpinner.performItemClick(catSpinner, 2, catSpinner.getId());*/
                catSpinner.setSelection(0);
                goButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue("" + cache.getResults().size(), cache.getResults().size() == 1);
        assertTrue("" + activity.getbrowseListID().getChildCount(), activity.getbrowseListID().getChildCount() == 1);
    }

    public void testSearchBoth() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

     /*   User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);*/

        final Button goButton = activity.getGoButton();
        final String searchText = "Bestbuy";
        final EditText searchBar = activity.getSearchBar();
        final Spinner catSpinner = activity.getCatSpinner();
        final ListView listView = activity.getbrowseListID();
        final Cache cache = activity.getMyCache();

        User user = new User();
        Inventory inventory = user.getInv();
        GiftCard giftCard1 = new GiftCard(10.00, "Bestbuy", 1, 1, 6, "scratched but usable");
        inventory.addGiftCard(giftCard1);
        cache.add(user);
        cache.loadItems();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                searchBar.setText(searchText);
                View v = catSpinner.getChildAt(0);
                catSpinner.performClick();
                catSpinner.performItemClick(v, 0, v.getId());
                goButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue("Listview is empty", listView.getChildCount() > 0);
    }

    public void testStartTrade() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), BrowseActivity.class);
        setActivityIntent(intent);
        final BrowseActivity activity = (BrowseActivity) getActivity();

     /*   User user = new User();
        user.addUsername("t");
        user.addCity("Edmo");
        user.addPhone("012-345-6789");
        user.addEmail("t@g.c");
        UserRegistrationController.getUserList().addUser(user);*/

        final Button goButton = activity.getGoButton();
        final String searchText = "Bestbuy";
        final EditText searchBar = activity.getSearchBar();
        final Spinner catSpinner = activity.getCatSpinner();
        final ListView listView = activity.getbrowseListID();
        final Cache cache = activity.getMyCache();

        User user = new User();
        Inventory inventory = user.getInv();
        GiftCard giftCard1 = new GiftCard(10.00, "Bestbuy", 1, 1, 6, "scratched but usable");
        inventory.addGiftCard(giftCard1);
        cache.add(user);
        cache.loadItems();

        //Code from: https://developer.android.com/training/activity-testing/activity-functional-testing.html
        //Date: 2015-11-05

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor tradeActivityMonitor =
                getInstrumentation().addMonitor(/*FIXME Trade*/ItemActivity.class.getName(),
                        null, false);

        assertTrue("Cache is empty: " + cache.getItems().size(), cache.getItems().size() > 0);
        assertTrue("Listview is empty: " + listView.getChildCount(), listView.getChildCount() > 0);

        // Validate that ReceiverActivity is started
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = listView.getChildAt(0);
                v.performLongClick();
            }
        });
        getInstrumentation().waitForIdleSync();


        ItemActivity tradeActivity = (/*FIXME Trade*/ItemActivity) tradeActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", tradeActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, tradeActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", ItemActivity.class, tradeActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(tradeActivityMonitor);//*/
    }


}
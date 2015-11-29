package ca.ualberta.smaccr.giftcarder;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by rdhe on 11/29/15.
 */
public class ServerTest extends ActivityInstrumentationTestCase2 {
    public ServerTest() {
        super(ca.ualberta.smaccr.giftcarder.MainActivity.class);
    }

    //Server("Elastic Search") need to work for these test to work

    //Retrieving an existing user already on the server
    public void testRetrieveUserFromServer()throws Exception {
        //bob must exist on the server for this to pass
        String username = "bob";


        //http://stackoverflow.com/questions/9148899/returning-value-from-thread retrived Nov 29 2015

        GetThread foo = new GetThread(username);
        Thread t = new Thread(foo);
        t.start();
        t.join();
        User testuser = foo.getUser();
        assertTrue(testuser.getUsername().equals(username));

    }


    class GetThread implements Runnable {
        private String id;

        public volatile User testuser;

        public GetThread(String id) {
            this.id = id;
        }

        private ESUserManager userManager;

        @Override
        public void run() {
            userManager = new ESUserManager("");


            testuser = userManager.getUser(id);
        }

        public User getUser(){
            return testuser;
        }
    }

    //Test adding User and retrieving him
    public void testWriteRetrieveUserFromServer()throws Exception {

        User user = new User();
        user.setUsername("bob");

        //http://stackoverflow.com/questions/9148899/returning-value-from-thread retrived Nov 29 2015

        AddThread foo = new AddThread(user);
        Thread t = new Thread(foo);
        t.start();
        t.join();
        User testuser = foo.getUser();
        assertTrue(testuser.getUsername().equals(user.getUsername()));

    }


    class AddThread implements Runnable {

        public volatile User testuser;

        public AddThread(User testuser) {
            this.testuser = testuser;
        }

        private ESUserManager userManager;

        @Override
        public void run() {
            userManager = new ESUserManager("");

            UserListController ulc = new UserListController(new UserList());
            ulc.addUser(testuser);

            testuser = userManager.getUser(testuser.getUsername());
        }

        public User getUser(){
            return testuser;
        }
    }


    public void testThread()throws Exception {
        //http://stackoverflow.com/questions/9148899/returning-value-from-thread retrived Nov 29 2015
        Foo foo = new Foo();
        Thread t = new Thread(foo);
        t.start();
        t.join();
        int value = foo.getValue();
        assertTrue(value == 2);

    }

    public class Foo implements Runnable {
        private volatile int value;

        @Override
        public void run() {
            value = 2;
        }

        public int getValue() {
            return value;
        }
    }


}


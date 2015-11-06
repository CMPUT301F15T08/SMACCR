package ca.ualberta.smaccr.giftcarder;

/**
 * Created by Carin on 10/25/2015.
 */
public class UserTest extends android.test.ActivityInstrumentationTestCase2{

    public UserTest(){
        super(ca.ualberta.smaccr.giftcarder.UserTest.class);
    }

    public void testAddUsername() throws Exception {
        User user = new User();
        user.addUsername("Link");
        assertTrue(user.getUsername().equals("Link"));
    }

    public void testAddCity() throws Exception {
        User user = new User();
        user.addCity("Skyloft");
        assertTrue(user.getCity().equals("Skyloft"));
    }

    public void testAddPhone() throws Exception {
        User user = new User();
        user.addPhone("555-555-5555");
        assertTrue(user.getPhone().equals("555-555-5555"));
    }

    public void testAddEmail() throws Exception {
        User user = new User();
        user.addEmail("hero@hyrule.com");
        assertTrue(user.getEmail().equals("hero@hyrule.com"));
    }
}

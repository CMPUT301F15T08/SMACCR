package ca.ualberta.smaccr.giftcarder;

/**
 * Created by Carin on 10/25/2015.
 */
public class UserTest extends android.test.ActivityInstrumentationTestCase2{

    public UserTest(){
        super(ca.ualberta.smaccr.giftcarder.UserTest.class);
    }

    public void testSetUsername() throws Exception {
        User user = new User();
        user.setUsername("Link");
        assertTrue(user.getUsername().equals("Link"));
    }

    public void testSetCity() throws Exception {
        User user = new User();
        user.setCity("Skyloft");
        assertTrue(user.getCity().equals("Skyloft"));
    }

    public void testSetPhone() throws Exception {
        User user = new User();
        user.setPhone("555-555-5555");
        assertTrue(user.getPhone().equals("555-555-5555"));
    }

    public void testSetEmail() throws Exception {
        User user = new User();
        user.setEmail("hero@hyrule.com");
        assertTrue(user.getEmail().equals("hero@hyrule.com"));
    }
}

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
        assertTrue(user.getUsername() == "Link");
    }

    public void testAddCity() throws Exception {
        User user = new User();
        user.addCity("Skyloft");
        assertTrue(user.getCity() == "Skyloft");
    }

    public void testAddPhone() throws Exception {
        User user = new User();
        user.addPhone("5555555555");
        assertTrue(user.getPhone() == "5555555555");
    }

    public void testAddEmail() throws Exception {
        User user = new User();
        user.addEmail("hero@hyrule.com");
        assertTrue(user.getEmail() == "hero@hyrule.com");
    }
}

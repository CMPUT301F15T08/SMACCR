package ca.ualberta.smaccr.giftcarder;

/**
 * Created by Carin on 11/2/2015.
 */
public class UserListTest extends android.test.ActivityInstrumentationTestCase2 {

    public UserListTest(){
        super(ca.ualberta.smaccr.giftcarder.UserListTest.class);
    }

    public void testAddUser() throws Exception {
        UserList userList = new UserList();
        User user = new User();

        user.addUsername("Link");
        user.addCity("Skyloft");
        user.addPhone("555-555-5555");
        user.addEmail("hero@hyrule.com");

        userList.addUser(user);
        assertFalse(userList.isEmpty());
    }

    public void testAddSecondUser() throws Exception {
        UserList userList = new UserList();
        User userOne = new User();
        User userTwo = new User();

        userOne.addUsername("Link");
        userOne.addCity("Skyloft");
        userOne.addPhone("555-555-5555");
        userOne.addEmail("hero@hyrule.com");
        userList.addUser(userOne);

        userTwo.addUsername("ColetteBrunel");
        userTwo.addCity("Iselia");
        userTwo.addPhone("555-555-5556");
        userTwo.addEmail("chosen@sylvarant.com");
        userList.addUser(userTwo);

        assertTrue(userList.getSize() == 2);
    }
}

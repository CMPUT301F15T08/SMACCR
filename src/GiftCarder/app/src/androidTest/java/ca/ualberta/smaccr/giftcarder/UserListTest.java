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

        user.setUsername("Link");
        user.setCity("Skyloft");
        user.setPhone("555-555-5555");
        user.setEmail("hero@hyrule.com");

        userList.addUser(user);
        assertFalse(userList.isEmpty());
    }

    public void testAddSecondUser() throws Exception {
        UserList userList = new UserList();
        User userOne = new User();
        User userTwo = new User();

        userOne.setUsername("Link");
        userOne.setCity("Skyloft");
        userOne.setPhone("555-555-5555");
        userOne.setEmail("hero@hyrule.com");
        userList.addUser(userOne);

        userTwo.setUsername("ColetteBrunel");
        userTwo.setCity("Iselia");
        userTwo.setPhone("555-555-5556");
        userTwo.setEmail("chosen@sylvarant.com");
        userList.addUser(userTwo);

        assertTrue(userList.getSize() == 2);
    }

    public void testGetUser() throws Exception {
        UserList userList = new UserList();
        User userOne = new User();
        User userTwo = new User();
        User userThree;

        userOne.setUsername("Link");
        userOne.setCity("Skyloft");
        userOne.setPhone("555-555-5555");
        userOne.setEmail("hero@hyrule.com");
        userList.addUser(userOne);

        userTwo.setUsername("ColetteBrunel");
        userTwo.setCity("Iselia");
        userTwo.setPhone("555-555-5556");
        userTwo.setEmail("chosen@sylvarant.com");
        userList.addUser(userTwo);

        userThree = userList.getUser("Link");
        assertTrue(userThree.getCity().equals("Skyloft"));
    }
}

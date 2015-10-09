import android.test.ActivityInstrumentationTestCase2;

/*Copyright 2015 SMACCR

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.

import android.test.ActivityInstrumentationTestCase2;*/

/**
 * Created by ConnorSheremeta on 15-10-09.
 */
public class FriendTest extends ActivityInstrumentationTestCase2 {
    public FriendTest(Class activityClass) {
        super(activityClass);
    }

    //tests adding and deleting friends methods
    public void testAddAndDeleteFriend() throws Exception {
        User friend = new User();
        friend.setUserName('RichieRich');
        friend.addFriend();
        assertTrue(friend.IsFriend);

        friend.deleteFriend();
        assertFalse(friend.IsFriend);
    }

    //tests for adding and getting things from a users profile
    public void testProfile() throws Exception {
        Profile profile = new Profile();
        profile.setContactInfo("rich@gmail.com");
        profile.setCity("Edmonton");

        assertTrue("Edmonton" == profile.getCity());
        assertTrue("rich@gmail.com"==profile.getContactInfo());
    }

    //tests for tracking

    public void testTracking() throws Exception {
        User tuser = new User();
        tuser.setUserName('RichieRich2');
        tuser.addToTracking();
        assertTrue(friend.IsTracked);
    }


}

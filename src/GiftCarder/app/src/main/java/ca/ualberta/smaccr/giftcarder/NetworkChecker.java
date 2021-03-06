/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/

package ca.ualberta.smaccr.giftcarder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by splant on 11/27/15.
 *
 * Alexandre Jasmin
 * http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
 */

/* NetworkCheckers checks to see if the network is available */
public class NetworkChecker {

    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

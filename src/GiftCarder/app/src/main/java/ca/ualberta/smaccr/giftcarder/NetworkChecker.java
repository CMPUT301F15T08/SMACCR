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
public class NetworkChecker {

    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

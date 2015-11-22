/* Modified from Nilanchala, retrieved 11/18/15,
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailsActivity extends Activity {
    public final static String EXTRA_BITMAP_STRING = "ca.ualberta.smaccr.giftcarder.BITMAPSTRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        ItemPictureController ipc = new ItemPictureController();
        String bitmapString = getIntent().getStringExtra(EXTRA_BITMAP_STRING);
        Bitmap bitmap = ipc.decodeBase64(bitmapString);

        ImageView imageView = (ImageView) findViewById(R.id.imageDetails);
        imageView.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
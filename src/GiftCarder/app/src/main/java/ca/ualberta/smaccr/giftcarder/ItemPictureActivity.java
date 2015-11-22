/* Modified from Nilanchala, retrieved 11/18/15,
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 *
 * Modified from Tejas Jasani, retrieved 11/18/15,
 * http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
 *
 * Get Serializable code
 * Modified from MCeley, retrieved 11/20/15,
 * http://stackoverflow.com/questions/12493818/how-to-pass-arraylistcustomobject-to-an-activity-in-another-application
 *
 */

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemPictureActivity extends ActionBarActivity {
    public final static String EXTRA_STATE = "ca.ualberta.smaccr.giftcarder.STATE";
    public final static String EXTRA_BITMAP_STRING = "ca.ualberta.smaccr.giftcarder.BITMAPSTRING";
    public final static String EXTRA_FEATURED_STRING = "ca.ualberta.smaccr.giftcarder.FEATUREDSTRING";
    public final static String EXTRA_PICTURES = "ca.ualberta.smaccr.giftcarder.PICTURES";
    public static final int ADD_STATE = 0; // add item
    public static final int OWNER_STATE = 1; // view own item

    private GridView gridView;
    private ItemGridViewAdapter gridAdapter;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    int itemState;

    ItemPictureController ipc = new ItemPictureController();
    protected ArrayList<ItemImage> itemImagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_picture);
        Button addPhotoButton = (Button) findViewById(R.id.addPhotoButton);

        // receive itemImagesList
        Intent intent = getIntent();
        itemState = (int) getIntent().getIntExtra(EXTRA_STATE, OWNER_STATE);
        itemImagesList = (ArrayList<ItemImage>)intent.getSerializableExtra(EXTRA_PICTURES);

        if (itemImagesList.isEmpty()) {
            itemImagesList = new ArrayList<ItemImage>();
        }

        gridView = (GridView) findViewById(R.id.pictureGridView);
        updateImagesList();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ItemImage item = (ItemImage) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(ItemPictureActivity.this, ItemDetailsActivity.class);
                intent.putExtra(EXTRA_FEATURED_STRING, item.isFeatured());
                intent.putExtra(EXTRA_BITMAP_STRING, item.getBitmapString());

                //Start details activity
                startActivity(intent);
            }
        });

        if (itemState != OWNER_STATE) {
            addPhotoButton.setVisibility(View.VISIBLE);

            // Long click to delete listener (modified from AllActivity)
            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;

                    AlertDialog.Builder deletedialog = new AlertDialog.Builder(ItemPictureActivity.this);
                    deletedialog.setMessage("Are you sure?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            itemImagesList.remove(pos);
                            updateImagesList();

                            dialog.dismiss();
                        }
                    });
                    deletedialog.create().show();
                    return true;
                }
            });

        } else {
            addPhotoButton.setVisibility(View.INVISIBLE);;
        }

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_picture, menu);
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
    */

    public void addPhoto(View view) {
        final CharSequence[] items = { "Camera", "Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemPictureActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ItemImage itemImage = null;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                itemImage = new ItemImage(onSelectFromGalleryResult(data));
            else if (requestCode == REQUEST_CAMERA)
                itemImage = new ItemImage(ipc.onCaptureImageResult(data));

        }

        itemImagesList.add(itemImage);
        updateImagesList();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_PICTURES, itemImagesList);

        if (!itemImagesList.isEmpty()) {
            // temporary until I get pick featured working
            returnIntent.putExtra(EXTRA_BITMAP_STRING, itemImagesList.get(0).getBitmapString());
        }

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void updateImagesList() {
        // first image gets "FEATURED" caption (first image is the one displayed for the image thumbnail)
        if (!itemImagesList.isEmpty()) {
            itemImagesList.get(0).setFeatured(true);
        }

        gridAdapter = new ItemGridViewAdapter(this, R.layout.grid_item_layout, itemImagesList);
        gridView.setAdapter(gridAdapter);
    }

    @SuppressWarnings("deprecation")
    public String onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);

        Cursor cursor =cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        return ipc.encodeToBase64(bm);
    }
}

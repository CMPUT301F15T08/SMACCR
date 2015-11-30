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

/* Modified from Nilanchala, retrieved 11/18/15,
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Carin on 11/19/2015.
 */

/* ItemGridViewAdapter is the adapter for the ItemPictureActivity */
public class ItemGridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public ItemGridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemPictureController ipc = new ItemPictureController();
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.caption = (TextView) row.findViewById(R.id.tvFeaturedImage);
            holder.image = (ImageView) row.findViewById(R.id.imageGridView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ItemImage item = (ItemImage) data.get(position);
        Bitmap bitmap = ipc.decodeBase64(item.getBitmapString());
        Boolean featured = item.isFeatured();

        holder.image.setImageBitmap(bitmap);
        if (featured) {
            holder.caption.setText("FEATURED");
        }
        return row;
    }

    static class ViewHolder {
        TextView caption;
        ImageView image;
    }
}

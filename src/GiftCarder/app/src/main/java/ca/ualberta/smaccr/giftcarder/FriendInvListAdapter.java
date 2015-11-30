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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cbli on 11/23/15.
 */

/* Modified adapter that only displays public items */
public class FriendInvListAdapter extends InvListAdapter {

    public FriendInvListAdapter(Context context, int resource, ArrayList<GiftCard> items) {
        super(context, resource, items);
        this.inv = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_inv_list, null);
        }

        GiftCard gc = (GiftCard) inv.get(position);

        if (gc != null) {

            // If private, hide item
            /*
            * retrieved from Jaydipsinh Zala, 11/23/15
             * http://stackoverflow.com/questions/13646147/hide-row-from-listview-without-taking-up-space
             */
            if (!gc.getShared()) {
                return vi.inflate(R.layout.blank_layout, parent,
                        false);
            }

            ImageView iv1 = (ImageView) v.findViewById(R.id.invListImageView);
            TextView tt1 = (TextView) v.findViewById(R.id.invListTitleTextView);
            TextView  tt2 = (TextView) v.findViewById(R.id.invListValueTextView);
            TextView  tt3 = (TextView) v.findViewById(R.id.invListQuantTextView);
            TextView  tt4 = (TextView) v.findViewById(R.id.invListCatTextView);

            if (iv1 != null) {
                try {
                    if (!(gc.getItemImagesList().isEmpty())) {
                        ipc.displayFeaturedImage(gc.getItemImagesList(), iv1);
                    } else {
                        iv1.setImageResource(R.drawable.card_icon);
                    }

                } catch (Exception e) {
                } // Do nothing to the default image if there are no images in the image list
            }

            if (tt1 != null) {
                tt1.setText(gc.getMerchant());
            }

            if (tt2 != null) {
                tt2.setText("$" + gc.getValue());
            }

            if (tt3 != null) {
                tt3.setText("x" + gc.getQuantity());
            }

            if (tt4 != null) {
                tt4.setText(gc.getCategoryString());
            }
        }

        return v;
    }
}

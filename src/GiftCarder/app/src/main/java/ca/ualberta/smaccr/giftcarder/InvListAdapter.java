package ca.ualberta.smaccr.giftcarder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by splant on 11/20/15.
 * Largely based off of Stack Overflow answer from
 * http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
 */
public class InvListAdapter extends ArrayAdapter<GiftCard> {

    ArrayList inv;

    public InvListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public InvListAdapter(Context context, int resource, ArrayList<GiftCard> items) {
        super(context, resource, items);
        this.inv = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_inv_list, null);
        }

        GiftCard gc = (GiftCard) inv.get(position);

        if (gc != null) {
            ImageView iv1 = (ImageView) v.findViewById(R.id.invListImageView);
            TextView  tt1 = (TextView) v.findViewById(R.id.invListTitleTextView);
            TextView  tt2 = (TextView) v.findViewById(R.id.invListValueTextView);
            TextView  tt3 = (TextView) v.findViewById(R.id.invListQuantTextView);
            TextView  tt4 = (TextView) v.findViewById(R.id.invListCatTextView);

            if (iv1 != null) {
                // iv1.setImageResource(p.getImage);
            }

            if (tt1 != null) {
                tt1.setText(gc.getMerchant());
            }

            if (tt2 != null) {
                tt2.setText("" + gc.getValue());
            }

            if (tt3 != null) {
                tt3.setText("x" + gc.getQuantity());
            }

            if (tt4 != null) {
                tt3.setText(gc.getCategoryString());
            }
        }

        return v;
    }
}

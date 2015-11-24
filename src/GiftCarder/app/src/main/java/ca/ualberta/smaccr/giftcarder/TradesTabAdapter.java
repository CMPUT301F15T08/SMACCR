package ca.ualberta.smaccr.giftcarder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by ali on 15-11-13.
 */
public class TradesTabAdapter extends BaseAdapter {

    private Context context;
    private User user;
    private ArrayList<Long> keys;
    private ArrayList<Trade> values;
    private static LayoutInflater inflater = null;

    public TradesTabAdapter(Context context, User user) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.user = user;
        this.keys = new ArrayList<Long>();
        this.values = new ArrayList<Trade>();
        for (Entry<Long, Trade> entry : user.getTradesList().entrySet()) {
            this.keys.add(entry.getKey());
            this.values.add(entry.getValue());
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return keys.size();
    }

    @Override
    public Trade getItem(int position) {
        // TODO Auto-generated method stub
        return values.get(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return keys.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row_my_trades, null);
        }
        TextView username1 = (TextView) view.findViewById(R.id.row_my_trades_username1);
        TextView username2 = (TextView) view.findViewById(R.id.row_my_trades_username2);
        TextView status = (TextView) view.findViewById(R.id.row_my_trades_status);


        username1.setText(values.get(position).getOwner());
        username2.setText(values.get(position).getBorrower());
        status.setText(String.valueOf(values.get(position).isStatus()));
        return view;
    }
}

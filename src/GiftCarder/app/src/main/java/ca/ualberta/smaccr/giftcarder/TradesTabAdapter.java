package ca.ualberta.smaccr.giftcarder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by ali on 15-11-13.
 */
public class TradesTabAdapter extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater = null;

    public TradesTabAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 6;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row_my_trades, null);
        }
        TextView status = (TextView) view.findViewById(R.id.row_my_trades_status);
        status.setText("Status Not Found");
        return view;
    }
}

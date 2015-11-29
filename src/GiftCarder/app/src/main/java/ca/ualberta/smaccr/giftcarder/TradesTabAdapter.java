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
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ali on 15-11-13.
 */
public class TradesTabAdapter extends BaseAdapter {

    private Context context;
    private User user;
    private ArrayList<String> keys;
    private ArrayList<Trade> values;
    private static LayoutInflater inflater = null;

    public TradesTabAdapter(Context context, User user) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.user = user;
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<Trade>();
        for (Map.Entry<String, Trade> entry : user.getTradesList().entrySet()) {
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
        return Long.parseLong(keys.get(position), 16);
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
        status.setText(values.get(position).getStatus());
        return view;
    }
}

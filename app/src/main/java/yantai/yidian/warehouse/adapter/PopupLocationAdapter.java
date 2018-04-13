package yantai.yidian.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;

/**
 * Created by BaiTao on 2018/4/12.
 */

public class PopupLocationAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private LayoutInflater inflater;
    private TextView tv_location;

    public PopupLocationAdapter(ArrayList<String> list, Context context) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(R.layout.listitem_popup_location,null);
        }
        tv_location = (TextView) convertView.findViewById(R.id.tv_location);
        tv_location.setText(list.get(position));
        return convertView;
    }
}

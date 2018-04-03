package yantai.yidian.warehouse.productIn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import yantai.yidian.warehouse.R;

import static yantai.yidian.warehouse.productIn.LocationSelectActivity.getSelectPosition;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<LocationInfo> locationInfoList;
    LayoutInflater mInflater;

    public MyAdapter(Context context, List<LocationInfo> myList){
        this.context=context;
        this.locationInfoList=myList;
        mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return locationInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.listitem,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.location=(TextView) convertView.findViewById(R.id.item_location);
            viewHolder.capacity=(TextView) convertView.findViewById(R.id.item_capacity);
            viewHolder.inventory=(TextView) convertView.findViewById(R.id.item_inventory);
            viewHolder.remainingSpace=(TextView) convertView.findViewById(R.id.item_remainingSpace);
            viewHolder.select=(RadioButton) convertView.findViewById(R.id.item_rb_check);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.location.setText(locationInfoList.get(position).getLacation());
        viewHolder.capacity.setText(locationInfoList.get(position).getCapacity());
        viewHolder.inventory.setText(locationInfoList.get(position).getInventory());
        viewHolder.remainingSpace.setText(locationInfoList.get(position).getRemainingSpace());
        if(getSelectPosition()==position){
            viewHolder.select.setChecked(true);
        }else
            viewHolder.select.setChecked(false);

        return convertView;
    }
}

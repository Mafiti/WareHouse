package yantai.yidian.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.ScatterBean;

/**
 * Created by BaiTao on 2018/4/10.
 */

public class ScatterAdapter extends BaseAdapter implements View.OnClickListener {

    private List<ScatterBean> list;
    private Context context;
    private LayoutInflater inflater;
    private Callback callback;
    private ViewHolder viewHolder;

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

    public interface Callback{
       void click(View v);
    }

    public ScatterAdapter(List<ScatterBean> list, Context context,Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = inflater.inflate(R.layout.listitem_scatter_in,null);
            viewHolder = new ViewHolder();
            viewHolder.productId = (TextView) convertView.findViewById(R.id.tv_product_id);
            viewHolder.location = (TextView) convertView.findViewById(R.id.tv_location_name);
            viewHolder.num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.weight = (TextView) convertView.findViewById(R.id.tv_weight);
            viewHolder.alter = (Button) convertView.findViewById(R.id.btn_alter);
            viewHolder.delete = (Button) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.productId.setText(list.get(position).getProductId());
        viewHolder.location.setText(list.get(position).getLocation());
        viewHolder.num.setText(list.get(position).getNum());
        viewHolder.weight.setText(list.get(position).getWeight());
        viewHolder.alter.setOnClickListener(this);
        viewHolder.delete.setOnClickListener(this);
        viewHolder.alter.setTag(position);
        viewHolder.delete.setTag(position);

        return convertView;
    }

    static class ViewHolder{
        TextView productId;
        TextView location;
        TextView num;
        TextView weight;
        Button alter;
        Button delete;
    }
}

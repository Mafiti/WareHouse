package yantai.yidian.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.SpecificWareBean;

/**
 * Created by BaiTao on 2018/4/1.
 */

public class SpecificWareAdapter extends BaseAdapter {

    private List<SpecificWareBean> list;
    private Context context;
    private LayoutInflater inflater;
    HashMap<String,Boolean> states=new HashMap<String,Boolean>();//用于记录每个RadioButton的状态，并保证只可选一个

    public SpecificWareAdapter(List<SpecificWareBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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

    //获取每个item显示的内容
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.listitem_specific_ware_select,null);
            viewHolder = new ViewHolder();
            viewHolder.rbtn = (RadioButton) convertView.findViewById(R.id.item_rb_ware_check);
            viewHolder.tv_ware_id = (TextView) convertView.findViewById(R.id.item_ware_id);
            viewHolder.tv_ware_name = (TextView) convertView.findViewById(R.id.item_ware_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final RadioButton rbtn = (RadioButton) convertView.findViewById(R.id.item_rb_ware_check);
        viewHolder.rbtn = rbtn;

        viewHolder.tv_ware_id.setText(list.get(position).getWare_id());
        viewHolder.tv_ware_name.setText(list.get(position).getWare_name());

        viewHolder.rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重置，确保最多只有一项被选中
                for(String key:states.keySet()){
                    states.put(key, false);

                }
                states.put(String.valueOf(position), rbtn.isChecked());
                SpecificWareAdapter.this.notifyDataSetChanged();
            }
        });

        boolean res=false;
        if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position))== false){
            res=false;
            states.put(String.valueOf(position), false);
        }
        else
            res = true;

        viewHolder.rbtn.setChecked(res);
        /*
        SpecificWareBean specificWareBean = list.get(position);
        TextView tv_ware_name = (TextView) view.findViewById(R.id.item_ware_name);
        TextView tv_organization = (TextView) view.findViewById(R.id.item_ware_id);

        tv_ware_name.setText(specificWareBean.ware_name);
        tv_organization.setText(specificWareBean.ware_id);
        */

        return convertView;
    }
    static class ViewHolder{
        RadioButton rbtn;
        TextView tv_ware_id;
        TextView tv_ware_name;
    }
}

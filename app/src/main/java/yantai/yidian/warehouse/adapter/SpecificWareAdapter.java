package yantai.yidian.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.listitem_specific_ware_select,null);
            view = convertView;
        }else {
            view = convertView;
        }

        SpecificWareBean specificWareBean = list.get(position);
        TextView tv_ware_name = (TextView) view.findViewById(R.id.item_ware_name);
        TextView tv_organization = (TextView) view.findViewById(R.id.item_organization);
        TextView tv_use_tag = (TextView) view.findViewById(R.id.item_use_tag);

        tv_ware_name.setText(specificWareBean.ware_name);
        tv_organization.setText(specificWareBean.organization);
        tv_use_tag.setText(specificWareBean.use_tag);

        return view;
    }
}

package yantai.yidian.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
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

    //获取每个item显示的内容
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

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


        viewHolder.tv_ware_id.setText(list.get(position).getWare_id());
        viewHolder.tv_ware_name.setText(list.get(position).getWare_name());



        boolean res=false;
        if(getStates(position)==null||
                getStates(position)==false)//判断当前位置的radiobutton点击状态
        {
            res=false;
            setStates(position, false);

        }else{
            res=true;
        }
        viewHolder.rbtn.setChecked(res);

        return convertView;
    }
    public void clearStates(int position){
        // 重置，确保最多只有一项被选中
        for(String key:states.keySet()){
            states.put(key,false);
        }
        states.put(String.valueOf(position), true);
    }

    //用于获取状态值
    public Boolean getStates(int position){
        return states.get(String.valueOf(position));
    }
    //设置状态值
    public void setStates(int position,boolean isChecked){
        states.put(String.valueOf(position),false);
    }
    static class ViewHolder{
        RadioButton rbtn;
        TextView tv_ware_id;
        TextView tv_ware_name;
    }
}

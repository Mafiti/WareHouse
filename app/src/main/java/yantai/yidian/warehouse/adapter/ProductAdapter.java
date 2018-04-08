package yantai.yidian.warehouse.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.ProductBean;

/**
 * Created by 李非 on 2018/3/30.
 * 加载入库产品到RecyclerView中显示出来
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context mContext;
    private List<ProductBean> mproductList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView box_num;
        TextView dev_id_table;
        TextView class_time;
        TextView item_number;
        TextView batch_table;
        TextView location;
        public ViewHolder(View view)
        {
            super(view);
            box_num=(TextView)view.findViewById(R.id.box_num);
            dev_id_table=(TextView)view.findViewById(R.id.dev_id_table);
            class_time=(TextView)view.findViewById(R.id.class_time);
            item_number=(TextView)view.findViewById(R.id.item_number);
            batch_table=(TextView)view.findViewById(R.id.batch_table);
            location=(TextView)view.findViewById(R.id.location);
        }
    }
    public ProductAdapter(List<ProductBean> productList){
        mproductList=productList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int ViewType){
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        ProductBean productBean=mproductList.get(position);
        holder.box_num.setText(productBean.getBox_num());
        holder.dev_id_table.setText(productBean.getDev_id()+"#线");
        holder.batch_table.setText(productBean.getBatch());
        holder.item_number.setText(productBean.getItem_number()+"");
        holder.class_time.setText(productBean.getClasss_time());
        holder.location.setText(productBean.getLocation());
    }
    @Override
    public int getItemCount()
    {
        return mproductList.size();
    }
}

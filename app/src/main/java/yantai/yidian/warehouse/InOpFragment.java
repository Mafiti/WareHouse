package yantai.yidian.warehouse;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import yantai.yidian.warehouse.productIn.ProductInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class InOpFragment extends Fragment implements View.OnClickListener{

    private LinearLayout llyt_product_in;
    private LinearLayout llyt_purchase_in;
    private LinearLayout llyt_return_in;
    private LinearLayout llyt_scatter_in;
    private LinearLayout llyt_product_return_in;
    private LinearLayout llyt_empty_in;
    private LinearLayout llyt_productionline_waste_in;
    private LinearLayout llyt_move_in;
    private LinearLayout llyt_warehouse_waste_in;


    public InOpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_op, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        addOnClickListener();
    }

    //初始化控件
    public void initView(){
        llyt_product_in = (LinearLayout) getActivity().findViewById(R.id.product_in);
        llyt_purchase_in = (LinearLayout) getActivity().findViewById(R.id.purchase_in);
        llyt_return_in = (LinearLayout) getActivity().findViewById(R.id.return_in);
        llyt_scatter_in = (LinearLayout) getActivity().findViewById(R.id.scatter_in);
        llyt_product_return_in = (LinearLayout) getActivity().findViewById(R.id.product_return_in);
        llyt_empty_in = (LinearLayout) getActivity().findViewById(R.id.empty_in);
        llyt_productionline_waste_in = (LinearLayout) getActivity().findViewById(R.id.productline_waste_in);
        llyt_move_in = (LinearLayout) getActivity().findViewById(R.id.move_in);
        llyt_warehouse_waste_in = (LinearLayout) getActivity().findViewById(R.id.warehouse_waste_in);

        llyt_product_in.setSelected(true);
    }

    //添加控件的单击监控事件
    public void addOnClickListener(){
        llyt_product_in.setOnClickListener(this);
        llyt_purchase_in.setOnClickListener(this);
        llyt_return_in.setOnClickListener(this);
        llyt_scatter_in.setOnClickListener(this);
        llyt_product_return_in.setOnClickListener(this);
        llyt_empty_in.setOnClickListener(this);
        llyt_productionline_waste_in.setOnClickListener(this);
        llyt_move_in.setOnClickListener(this);
        llyt_warehouse_waste_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //生产入库
            case R.id.product_in:
                startActivity(new Intent(getActivity(),ProductInActivity.class));
                llyt_product_in.setSelected(true);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //采购入库
            case R.id.purchase_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(true);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //退货入库
            case R.id.return_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(true);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //零散入库
            case R.id.scatter_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(true);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //生产退货入库
            case R.id.product_return_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(true);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //空箱入库
            case R.id.empty_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(true);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //产线废料入库
            case R.id.productline_waste_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(true);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //移库
            case R.id.move_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(true);
                llyt_warehouse_waste_in.setSelected(false);
                break;
            //仓库废料入库
            case R.id.warehouse_waste_in:
                llyt_product_in.setSelected(false);
                llyt_purchase_in.setSelected(false);
                llyt_return_in.setSelected(false);
                llyt_scatter_in.setSelected(false);
                llyt_product_return_in.setSelected(false);
                llyt_empty_in.setSelected(false);
                llyt_productionline_waste_in.setSelected(false);
                llyt_move_in.setSelected(false);
                llyt_warehouse_waste_in.setSelected(true);
                break;
        }

    }
}

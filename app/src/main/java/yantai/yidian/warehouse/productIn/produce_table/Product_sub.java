package yantai.yidian.warehouse.productIn.produce_table;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ProductAdapter;
import yantai.yidian.warehouse.bean.ProductBean;

public class Product_sub extends AppCompatActivity {

    public List<ProductBean> productBeanList=new ArrayList<>();
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sub);
        initView();
    }
    public void initView()
    {
        productBeanList=Product.productBeanList;
       /*ProductBean product=new ProductBean();
       product.setBox_num("TMX0400011");
       product.setProduct_line("2#线");
       product.setClasss_time("白班");
       product.setItem_number(100);
       product.setProduct_time("user10001");
       product.setLocation("B103");
        productBeanList.add(product);*/
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter=new ProductAdapter(productBeanList);
        recyclerView.setAdapter(productAdapter);
    }
}

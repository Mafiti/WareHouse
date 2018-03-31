package yantai.yidian.warehouse.productIn.produce_table;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ProductAdapter;
import yantai.yidian.warehouse.bean.ProductBean;
import yantai.yidian.warehouse.scan.ScanActivity;

public class Product_table extends AppCompatActivity {
    public List<ProductBean> productBeanList=new ArrayList<>();
    private ProductAdapter productAdapter;
    private ButtonView btn_scan_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table);
        initView();
    }
    public void initView()
    {
        productBeanList=Product.productBeanList;
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter=new ProductAdapter(productBeanList);
        recyclerView.setAdapter(productAdapter);
        btn_scan_delete=(ButtonView)findViewById(R.id.btn_scan_delete);
        btn_scan_delete.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_table.this, ScanActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data_return");
                    for(int i=0;i<productBeanList.size();i++)
                    {

                        if(productBeanList.get(i).getBox_num().equals(returnedData))
                        {
                            productBeanList.remove(i);
                            Toast.makeText(Product_table.this,"删除成功", Toast.LENGTH_SHORT).show();
                            initView();
                        }
                    }
                }
                break;
            default:
        }
    }
}

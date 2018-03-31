package yantai.yidian.warehouse.productIn.produce_table;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;


import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.ProductBean;
import yantai.yidian.warehouse.scan.ScanActivity;

public class Product extends AppCompatActivity {
    private EditText box_number;
    private EditText type;
    private EditText product_time;
    private EditText location;
    private Button  select_location;
    private ButtonView sure;
    private ImageView scan;
    private ImageView x1;
    private ImageView x2;
    public static List<ProductBean>  productBeanList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        inintView();
    }
    protected void inintView()
    {
        box_number=(EditText)findViewById(R.id.box_number1);
        type=(EditText)findViewById(R.id.type1);
        product_time=(EditText)findViewById(R.id.product_time1);
        location=(EditText)findViewById(R.id.location1);
        select_location=(Button)findViewById(R.id.select_location);
        sure=(ButtonView)findViewById(R.id.sure);
        scan=(ImageView)findViewById(R.id.scan);
        x1=(ImageView)findViewById(R.id.x1);
        x2=(ImageView)findViewById(R.id.x2);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new IntentIntegrator(ScanActivity.this).initiateScan(); //初始化扫描
                Intent intent = new Intent(Product.this, ScanActivity.class);
                startActivityForResult(intent, 1);

            }
        });
        x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type.setText("");
            }
        });
        x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_time.setText("");
            }
        });

        sure.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Product.this,"点击",Toast.LENGTH_SHORT).show();
                ProductBean productBean=new ProductBean();
                //还要判断信息是否为空
                productBean.setBox_num(box_number.getText().toString());
                productBean.setProduct_line("2#线");
                productBean.setClasss_time("白班");       //可以添加逻辑，根据获取的系统时间判断白班还是夜班
                productBean.setItem_number(10);     //个数这里我不太理解
                productBean.setProduct_time(product_time.getText().toString());
                productBean.setLocation(location.getText().toString());
                Toast.makeText(Product.this,"生成",Toast.LENGTH_SHORT).show();
                productBeanList.add(productBean);
                Toast.makeText(Product.this,"提交成功",Toast.LENGTH_SHORT).show();
                box_number.setText("");
            }
        });
    }
    //回调获取扫描得到的条码值
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data_return");
                    box_number.setText(returnedData);
                }
                break;
            default:
        }
    }
}

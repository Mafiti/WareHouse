package yantai.yidian.warehouse.productIn.produce_table;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ProductAdapter;
import yantai.yidian.warehouse.bean.ProductBean;
import yantai.yidian.warehouse.scan.ScanActivity;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;

public class Product_table extends AppCompatActivity {
    private static final String TAG="Product_table";
    public static final int WRONG=1;
    public static final int OK=2;
    public List<ProductBean> productBeanList=new ArrayList<>();
    private ProductAdapter productAdapter;
    private TextView scan_delete;   //扫码删除
    private TextView sure_sub;  //  确认提交
    private TextView wave;  //入库仓库
    private TextView product_noid;  //产品品号
    private TextView batch;         //生产批次
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table);
        initView();
    }
    private Handler handler=new myHandler();
    class myHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case WRONG:
                    toast_WRONG();     //数据提交失败
                    break;
                case OK:
                    toast_OK();       //数据提交成功
                    break;
                default:
                    break;
            }
        }
    }
    protected void toast_WRONG(){
        Toast.makeText(Product_table.this,"数据提交失败",Toast.LENGTH_SHORT).show();
    }
    protected void toast_OK(){
        Toast.makeText(Product_table.this,"提交成功",Toast.LENGTH_SHORT).show();
    }
    public void initView()
    {
        productBeanList=Product.productBeanList;
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list);
        wave=(TextView)findViewById(R.id.wave);
        product_noid=(TextView)findViewById(R.id.product_noid);
        batch=(TextView)findViewById(R.id.batch);
        product_noid.setText(Product.Product_noid);
        batch.setText(Product.Batch);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter=new ProductAdapter(productBeanList);
        recyclerView.setAdapter(productAdapter);
        scan_delete=(TextView)findViewById(R.id.scan_delete);
        sure_sub=(TextView)findViewById(R.id.sure_sub);
        scan_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //扫码删除
                Intent intent = new Intent(Product_table.this, ScanActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        sure_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPost();
            }
        });
    }
    protected void getPost()
    {

        String sessionId="92D84AAD121190462E763B7D773F144C";
        String urlPath="http://10.0.2.2:8081/mes/mobile/mBillSubmit?sessionid="+sessionId;
        JSONObject jsonPost=new JSONObject();
        List<Object> tempList = new ArrayList<Object>();
        JSONObject jsonObj = new JSONObject();

        String postData="";
        for(int i=0;i<productBeanList.size();i++)
        {
            jsonObj.clear();
            jsonObj.put("box_id",productBeanList.get(i).getBox_id());
            jsonObj.put("bill_state", 1);
            jsonObj.put("qc_flag", "Y");
            jsonObj.put("qc_role", 1);
            jsonObj.put("qc_person","admin");
            tempList.add(jsonObj.toString());
        }


        //组装
        jsonPost.put("status", 0);
        jsonPost.put("count", 1);
        jsonPost.put("bill_id", 1);
        jsonPost.put("result", tempList);
        postData=jsonPost.toString();
        HttpPost.sendHttpRequest(urlPath,postData,new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                Log.d(TAG, "onFinish: "+response);
                if(response=="数据提交失败")
                {
                    Message message = new Message();
                    message.what = WRONG;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = OK;
                    handler.sendMessage(message);

                }

            }
            @Override
            public void onError(Exception e) {
                //Toast.makeText(Product.this,"Error",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.d(TAG, "onFinish:Error"+e);
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
                            Toast.makeText(Product_table.this,"删除成功",Toast.LENGTH_SHORT).show();
                            initView();
                        }
                    }
                }
                break;
            default:
        }
    }
}

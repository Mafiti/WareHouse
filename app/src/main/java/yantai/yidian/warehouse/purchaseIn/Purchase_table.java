package yantai.yidian.warehouse.purchaseIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ProductAdapter;
import yantai.yidian.warehouse.bean.ProductBean;
import yantai.yidian.warehouse.productIn.CheckActivity;
import yantai.yidian.warehouse.productIn.produce_table.Product_table;
import yantai.yidian.warehouse.scan.ScanActivity;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.WareApi;

public class Purchase_table extends AppCompatActivity implements View.OnClickListener,WareApi{
    private static final String TAG="Purchase_table";
    public static final int WRONG=1;
    public static final int OK=2;
    public List<ProductBean> productBeanList=new ArrayList<>();
    private ProductAdapter productAdapter;
    private TextView scan_delete;   //扫码删除
    private TextView sure_sub;  //  确认提交
    private TextView wave;  //入库仓库
    private TextView product_noid;  //产品品号
    private TextView batch;         //生产批次
    private TextView boxNum;        //入库箱数
    private TextView itemNum;       //入库个数
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table_pur);
        Log.d(TAG, "onCreate: 111111111111");
        initData();
    }

    private Handler handler=new Purchase_table.myHandler();
    class myHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case WRONG:
                    toast_WRONG();     //数据提交失败
                    break;
                case OK:
                   // toast_OK();       //数据提交成功
                    Intent intent1 = new Intent(Purchase_table.this, CheckActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case 3:
                    toast_OK();
                default:
                    break;
            }
        }
    }
    protected void toast_WRONG(){
        Toast.makeText(Purchase_table.this,"数据提交失败",Toast.LENGTH_SHORT).show();
    }
    protected void toast_OK(){
        Toast.makeText(Purchase_table.this,"提交成功",Toast.LENGTH_SHORT).show();
    }

    protected void initData(){
        Log.d(TAG, "initData: ghchkgchjcfjhchgchcjcg");
        productBeanList = Purchase.productBeanList;

        scan_delete = (TextView) findViewById(R.id.scan_delete_pur);
        sure_sub = (TextView)  findViewById(R.id.sure_sub_pur);
        wave = (TextView) findViewById(R.id.wave_pur);
        product_noid = (TextView)  findViewById(R.id.product_noid_pur);
        batch = (TextView) findViewById(R.id.batch_pur);
        boxNum = (TextView) findViewById(R.id.box_num_to_ware_pur);
        itemNum = (TextView) findViewById(R.id.item_num_to_ware_pur);
        recyclerView=(RecyclerView)findViewById(R.id.list_pur);

        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        wave.setText(spf.getString("wareName",null));
        product_noid.setText(Purchase.product_noid);
        batch.setText(Purchase.batch);

        scan_delete.setOnClickListener(this);
        sure_sub.setOnClickListener(this);

        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter=new ProductAdapter(productBeanList);
        recyclerView.setAdapter(productAdapter);
    }

    protected void getPost()
    {
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionId=spf.getString("sessionid",null);
        String urlPath="http://10.0.2.2:8080/mes/mobile/mBillSubmit?sessionid="+sessionId;
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
                //Log.d(TAG, "onFinish: "+response);
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

    protected void saveDetail(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionId=spf.getString("sessionid",null);
        String urlPath = URL_mBillPurchaseDetail + sessionId;

        JSONObject jsonPost=new JSONObject();
        List<Object> tempList = new ArrayList<Object>();
        JSONObject jsonObj = new JSONObject();

        String postData="";
        for(int i=0;i<productBeanList.size();i++)
        {
            jsonObj.clear();
            jsonObj.put("box_id",productBeanList.get(i).getBox_id());
            jsonObj.put("fact_num", productBeanList.get(i).getItem_number());
            jsonObj.put("full_flag", productBeanList.get(i).getFullFlag());
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
                    message.what = 3;
                    handler.sendMessage(message);
//                    Toast.makeText(Purchase_table.this,"提交成功",Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_delete_pur:
                Intent intent = new Intent(Purchase_table.this, ScanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.sure_sub_pur:
                getPost();
                saveDetail();
                Purchase.productBeanList.clear();
                break;
        }
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
                            Toast.makeText(Purchase_table.this,"删除成功",Toast.LENGTH_SHORT).show();
//                            initView();
                            productAdapter=new ProductAdapter(productBeanList);
                            recyclerView.setAdapter(productAdapter);
                        }
                    }
                }
                break;
            default:
        }
    }
}

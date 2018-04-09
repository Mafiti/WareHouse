package yantai.yidian.warehouse.productIn.produce_table;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.example.mondschein.btnview.ButtonView;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.ProductBean;
import yantai.yidian.warehouse.productIn.LocationInfo;
import yantai.yidian.warehouse.productIn.LocationSelectActivity;
import yantai.yidian.warehouse.productIn.feedback.ScanFeedbackActivity;
import yantai.yidian.warehouse.scan.ScanActivity;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;

public class Product extends AppCompatActivity {
    private static final String TAG="Product";
    public static final int UPDATE_TEXT=0;
    public static final int WRONG=1;
    private EditText box_number;
    private EditText product_noid;
    private EditText batch;
    private EditText location;
    private EditText class_time;
    private EditText item_number;
    private Button select_location;
    private TextView table_detail;
    private TextView scan_next;
    private Button post;
    private ImageView scan;
    private ImageView x1;
    private ImageView x2;
    private ImageView x3;
    private ImageView x4;
    private int    Box_id;      //箱ID
    private List<LocationInfo> locationInfoList = locationInfoList = new ArrayList<LocationInfo>();

    private int Dev_id;    //产线
    public static String Product_noid;    //产品品号
    private String Classs_time;     //班次
    private String Item_number;     //个数
    public static String Batch;    //生产批次
    private String Location;    //库位
    public static List<ProductBean>  productBeanList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        inintView();


    }
    /*   private Handler handler=new Handler(new Handler.Callback() {
           @Override
           public boolean handleMessage(Message message) {
               load();
               return false;
           }
       });*/
    private Handler handler=new myHandler();
    class myHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case UPDATE_TEXT:
                    load();         //加载数据到EditText里
                    break;
                case WRONG:
                    toast();        //数据提交失败
                default:
                    break;
            }
        }
    }

    public void clearText(){
        box_number.setText("");
        product_noid.setText("");
        batch.setText("");
        location.setText("");
        class_time.setText("");
        item_number.setText("");

    }
    protected void inintView()
    {
        box_number=(EditText)findViewById(R.id.box_number1);
        product_noid=(EditText)findViewById(R.id.product_noid);
        batch=(EditText)findViewById(R.id.batch);
        location=(EditText)findViewById(R.id.location1);
        select_location=(Button)findViewById(R.id.select_location);
        class_time=(EditText)findViewById(R.id.class_time);
        item_number=(EditText)findViewById(R.id.item_number);
        clearText();
        scan_next=(TextView) findViewById(R.id.scan_next);
        table_detail=(TextView)findViewById(R.id.table_detail);
        scan=(ImageView)findViewById(R.id.scan);
        x1=(ImageView)findViewById(R.id.x1);
        x2=(ImageView)findViewById(R.id.x2);
        x3=(ImageView)findViewById(R.id.x3);
        x4=(ImageView)findViewById(R.id.x4);
        post=(Button)findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                getPost();
                getLocate();
            }
        });
        //getPost();
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
                product_noid.setText("");

            }
        });
        x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batch.setText("");
            }
        });
        x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class_time.setText("");
            }
        });
        x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_number.setText("");
            }
        });
        scan_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getPost();
                if(box_number.getText().toString().equals("") || product_noid.getText().toString().equals("") || batch.getText().toString().equals("") || class_time.getText().toString().equals("") ||item_number.getText().toString().equals("") ||location.getText().toString().equals("")) {
                    Toast.makeText(Product.this, "有一项为空", Toast.LENGTH_SHORT).show();
                }else {
                    ProductBean productBean=new ProductBean();
                    //还要判断信息是否为空
                    productBean.setBox_id(Box_id);
                    productBean.setDev_id(Dev_id);
                    productBean.setBox_num(box_number.getText().toString());
                    int a=0;
                    if (!product_noid.getText().toString().equals("")){
                       a =Integer.parseInt(product_noid.getText().toString());
                    }
                    productBean.setProduct_noid(a);
                    productBean.setClasss_time(class_time.getText().toString());       //可以添加逻辑，根据获取的系统时间判断白班还是夜班
                    int num=0;
                    if (!item_number.getText().toString().equals("")){
                        num=Integer.parseInt(item_number.getText().toString());
                    }
                    productBean.setItem_number(num);     //个数这里我不太理解
                    productBean.setBatch(batch.getText().toString());
                    productBean.setLocation(location.getText().toString());
                    productBeanList.add(productBean);
                    Toast.makeText(Product.this,"提交成功",Toast.LENGTH_SHORT).show();
                    box_number.setText("");
                    product_noid.setText("");
                    batch.setText("");
                    class_time.setText("");
                    item_number.setText("");
                    location.setText("");
                }

            }
        });
        table_detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!box_number.getText().toString().equals("") && !product_noid.getText().toString().equals("") && !batch.getText().toString().equals("") && !class_time.getText().toString().equals("") && !item_number.getText().toString().equals("") && !location.getText().toString().equals("")) {
                    ProductBean productBean=new ProductBean();
                    //还要判断信息是否为空
                    productBean.setBox_id(Box_id);
                    productBean.setDev_id(Dev_id);
                    productBean.setBox_num(box_number.getText().toString());
                    int a=0;
                    if (!product_noid.getText().toString().equals("")){
                        a =Integer.parseInt(product_noid.getText().toString());
                    }
                    productBean.setProduct_noid(a);
                    productBean.setClasss_time(class_time.getText().toString());       //可以添加逻辑，根据获取的系统时间判断白班还是夜班
                    int num=0;
                    if (!item_number.getText().toString().equals("")){
                        num=Integer.parseInt(item_number.getText().toString());
                    }
                    productBean.setItem_number(num);     //个数这里我不太理解
                    productBean.setBatch(batch.getText().toString());
                    productBean.setLocation(location.getText().toString());
                    productBeanList.add(productBean);
                    Toast.makeText(Product.this,"提交成功",Toast.LENGTH_SHORT).show();
                    box_number.setText("");
                    product_noid.setText("");
                    batch.setText("");
                    class_time.setText("");
                    item_number.setText("");
                    location.setText("");
                    Intent intent=new Intent(Product.this,Product_table.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(Product.this,Product_table.class);
                    startActivity(intent);
                }

            }
        });
        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("locationInfoList", (ArrayList<LocationInfo>) locationInfoList);
                intent.putExtras(bundle);
                intent.setClass(Product.this, LocationSelectActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }
    protected void getPost()
    {
        Toast.makeText(Product.this,"点击",Toast.LENGTH_SHORT).show();
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionId=spf.getString("sessionid",null);
        String urlPath="http://10.0.2.2:8080/mes/mobile/mScanBox?sessionid="+sessionId;

        JSONObject jsonPost=new JSONObject();
        List<Object> tempList = new ArrayList<Object>();
        JSONObject jsonObj = new JSONObject();

        String postData="";
        // StringBuilder sb=new StringBuilder();
        // sb.append('[');

        //准备数据
        //box 1
             /*   jsonObj.clear();
                jsonObj.put("box_id", 3);
                jsonObj.put("loc_id", 1);
                tempList.add(jsonObj.toString());*/
        jsonObj.clear();
        jsonObj.put("bill_id", 1);
        jsonObj.put("box_no", box_number.getText().toString());
        tempList.add(jsonObj.toString());
        //box 2
              /*  jsonObj.clear();
                jsonObj.put("box_id", 2);
                jsonObj.put("loc_id", 2);
                tempList.add(jsonObj.toString());*/

        //组装
        jsonPost.put("status", 0);
        jsonPost.put("count", 1);
        //jsonPost.put("bill_id", 1);
        jsonPost.put("result", tempList);
        //sb.append(jsonPost.toString());
        //  sb.append(']');
        postData=jsonPost.toString();


        Log.d(TAG, "onFinish: 点估计"+postData);
        HttpPost.sendHttpRequest(urlPath,postData,new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                // Toast.makeText(Product.this,response,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFinish: "+response);
                if(response=="数据提交失败")
                {
                    Message message = new Message();
                    message.what = WRONG;
                    handler.sendMessage(message);
                }else {
                    JSONObject jsonObject1 = JSONObject.fromObject(response);
                    JSONArray jsonArray = jsonObject1.getJSONArray("result");//里面有一个数组数据，可以用getJSONArray获取数组
                    int status = jsonObject1.getInt("status");
                    Log.d(TAG,status+"");
                    if (status!=0){
                        Intent intent = new Intent(Product.this, ScanFeedbackActivity.class);
                        intent.putExtra("status",status);
                        startActivity(intent);
                    }else {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); //每条记录又由几个Object对象组成
                            Box_id=item.getInt("bill_id");
                            Dev_id=item.getInt("dev_id");            //产线
                            Product_noid = item.getString("product_noid");    //产品品号
                            Classs_time = item.getString("class_type_name");     //班次
                            Item_number = item.getString("fact_num");     //个数
                            Batch = item.getString("batch");    //生产批次
                            //Location = item.getString("loc_id");    //库位
                            Log.d(TAG, "onFinish: loading");
                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);

                        }
                    }

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
    protected void load(){

        product_noid.setText(Product_noid);

        class_time.setText(Classs_time);
        item_number.setText(Item_number);
        batch.setText(Batch);
        location.setText(Location);
    }
    protected void toast(){
        Toast.makeText(Product.this,"数据提交失败",Toast.LENGTH_SHORT).show();
    }
    protected void getLocate(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionId=spf.getString("sessionid",null);
        String urlPath="http://10.0.2.2:8080/mes/mobile/mBillInLoc?sessionid="+sessionId;
        String postData = "";

        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("status", "0");
            object.put("count", "0");
            object.put("bill_id","1");
//                object.put("bill_id", "1");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        postData = object.toString();

        HttpPost.sendHttpRequest(urlPath, postData, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d(TAG, "onFinish: "+response);
                if(response=="数据提交失败")
                {
                    Message message = new Message();
                    message.what = WRONG;
                    handler.sendMessage(message);
                }else {
                    // 获取响应的正文
                    JSONObject jsonObject1 = JSONObject.fromObject(response);
                    int status = jsonObject1.getInt("status");
                    if (status == 0) {
                        JSONArray jsonLocArray = jsonObject1.getJSONArray("result");
                        for (int i = 0; i < jsonLocArray.size(); i++) {

                                JSONObject locObject = jsonLocArray.getJSONObject(i);
                                int loc_id = locObject.getInt("loc_id");
                                String loc_name = locObject.getString("loc_name");
                                int max_num = locObject.getInt("max_num");
                                int remain_num = locObject.getInt("remain_num");
                                int al_num = max_num - remain_num;
                                LocationInfo locationInfo = new LocationInfo(loc_id, loc_name, max_num, al_num, remain_num);
                                locationInfoList.add(locationInfo);
                                if (i == 0) {
                                    Location=loc_name;
                                    Message message = new Message();
                                    message.what = UPDATE_TEXT;
                                    handler.sendMessage(message);
                                }
                            }
                        }


                }

            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onFinish:Error"+e);
            }
        });

    }

    //关闭键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //回调获取扫描得到的条码值
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data_return");
                    box_number.setText(returnedData);
                    getPost();
                }
                break;
            case 2:
                if (resultCode==RESULT_OK){
                    String backData = data.getStringExtra("location");
                    location.setText(backData);
                }
            default:
        }
    }
}

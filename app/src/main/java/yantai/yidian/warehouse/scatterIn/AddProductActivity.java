package yantai.yidian.warehouse.scatterIn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;
import com.mondschein.titleview.TitleView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.PopupLocationAdapter;
import yantai.yidian.warehouse.adapter.PopupProductnoAdapter;
import yantai.yidian.warehouse.bean.ScatterBean;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.WareApi;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,WareApi {

    public static final int LOCATION_WRONG=1;
    public static final int LOCATION_OK=2;

    public static final int PRODUCTNO_WRONG=3;
    public static final int PRODUCTNO_OK=4;

    private boolean isEditable = false;//标记数量、重量的输入框是否可编辑

    private ScatterBean scatterBean;

    private ImageView img_search_location;
    private ImageView img_search_productno;
    private ButtonView btn_add;
    private EditText edt_location;
    private EditText edt_productno;
    private EditText edt_num;
    private EditText edt_weight;
    private ArrayList<String> locationList;
    private ArrayList<String> productList;
    private ListView lv_popup;
    private View ContentView;
    private String TAG;
    private Handler handler;
    private TitleView titleView;

    private PopupWindow searchLocationPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
    }

    protected void initView(){
        img_search_location = (ImageView) findViewById(R.id.img_search_location);
        img_search_productno = (ImageView) findViewById(R.id.img_search_product_no);
        btn_add = (ButtonView) findViewById(R.id.btn_scatter_add);
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_productno = (EditText) findViewById(R.id.edt_productno);
        edt_num = (EditText) findViewById(R.id.edt_num);
        edt_weight = (EditText) findViewById(R.id.edt_weight);
        ContentView = getLayoutInflater().inflate(R.layout.popup_location,null);
        lv_popup = (ListView) ContentView.findViewById(R.id.lv_popup_location_name_search);
        titleView = (TitleView) findViewById(R.id.tv_scatter_title);
        //产品品号为空时，数量、重量输入框不可编辑
        setEditable();
        setTitle();

        img_search_location.setOnClickListener(this);
        img_search_productno.setOnClickListener(this);
        btn_add.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productno = edt_productno.getText().toString();
                String location = edt_location.getText().toString();
                String num = edt_num.getText().toString();
                String weight = edt_weight.getText().toString();
                if (productno.equals("")){
                    Toast.makeText(AddProductActivity.this,"产品品号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (location.equals("")){
                    Toast.makeText(AddProductActivity.this,"入库库位不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (num.equals("")){
                    Toast.makeText(AddProductActivity.this,"散件数量不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weight.equals("")){
                    Toast.makeText(AddProductActivity.this,"散件重量不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                scatterBean = new ScatterBean(productno,location,num,weight);
                Intent intent = new Intent(AddProductActivity.this,ScatterInActivity.class);
                intent.putExtra("scatterBean",scatterBean);
                startActivity(intent);
                finish();
            }
        });
        //产品输入框的焦点监听事件
        edt_productno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    if (edt_productno.getText().toString().equals("")){
                        isEditable = false;
                    }else {
                        isEditable = true;
                    }
                }
                setEditable();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_search_location:
                TAG = "入库库位";
                getPopWindow(edt_location);
                getPostByLocation();
                locationList = new ArrayList<>();
                handler = new InnerHandler();
                PopupLocationAdapter popupLocationAdapter = new PopupLocationAdapter(locationList,this);

                lv_popup.setAdapter(popupLocationAdapter);
                lv_popup.setOnItemClickListener(this);
                break;
            case R.id.img_search_product_no:
                TAG = "产品品号";
                getPopWindow(edt_productno);
                getPostByProductNo();
                productList = new ArrayList<>();
                handler = new InnerHandler();
                PopupProductnoAdapter popupProductnoAdapter = new PopupProductnoAdapter(productList,this);
                lv_popup.setAdapter(popupProductnoAdapter);
                lv_popup.setOnItemClickListener(this);
                break;
        }
    }

    class InnerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOCATION_WRONG:
                    Toast.makeText(AddProductActivity.this,"响应失败，响应码为"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case LOCATION_OK:
                    String requestResult = (String) msg.obj;
                    JSONObject jsonObject = JSONObject.fromObject(requestResult);
                    int status = jsonObject.getInt("status");
                    if (status !=0){
                        Toast.makeText(AddProductActivity.this,"获取库位信息失败",Toast.LENGTH_SHORT).show();
                    }else {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject locationItem;
                        String locationName;
                        for (int i = 0;i<jsonArray.size();i++){
                            locationItem = jsonArray.getJSONObject(i);
                            locationName = locationItem.getString("loc_name");
                            locationList.add(locationName);
                        }
                    }
                    break;
                case PRODUCTNO_WRONG:
                    Toast.makeText(AddProductActivity.this,"响应失败，响应码为"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case PRODUCTNO_OK:
                    String requestProductResult = (String) msg.obj;
                    JSONObject productJson = JSONObject.fromObject(requestProductResult);
                    int statusProduct = productJson.getInt("status");
                    if (statusProduct !=0){
                        Toast.makeText(AddProductActivity.this,"获取库位信息失败",Toast.LENGTH_SHORT).show();
                    }else {
                        JSONArray jsonArray = productJson.getJSONArray("product_no");
                        JSONObject productItem;
                        int product_noid;
                        for (int i = 0;i<jsonArray.size();i++){
                            productItem = jsonArray.getJSONObject(i);
                            product_noid = productItem.getInt("product_noid");
                            productList.add(product_noid+"");
                        }
                    }
                    break;

            }
        }
    }

    //获取库位信息
    public void getPostByLocation(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionid = spf.getString("sessionid",null);
        String url = URL_mBillInLoc+"sessionid="+sessionid;
        String postData="";
        JSONObject jsonObject = new JSONObject();
        jsonObject.clear();
        jsonObject.put("status",0);
        jsonObject.put("count",0);
        jsonObject.put("bill_id",1);
        postData = jsonObject.toString();
        HttpPost.sendHttpRequest(url, postData, new HttpCallbackListener() {
            @Override
            public int onFinish(String response) {
                if(response=="数据提交失败")
                {
                    Message message = new Message();
                    message.what = LOCATION_WRONG;
                    message.obj = response;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = LOCATION_OK;
                    message.obj = response;
                    handler.sendMessage(message);

                }
                return 0;
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("AddProductActicity", "onFinish:Error"+e);
            }
        });

    }

    //获取产品品号
    public void getPostByProductNo(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionid = spf.getString("sessionid",null);
        String url = URL_PARAMOBTAIN+"sessionid="+sessionid;
        String postData="";
        HttpPost.sendHttpRequest(url, postData, new HttpCallbackListener() {
            @Override
            public int onFinish(String response) {
                if(response=="数据提交失败")
                {
                    Message message = new Message();
                    message.what = PRODUCTNO_WRONG;
                    message.obj = response;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = PRODUCTNO_OK;
                    message.obj = response;
                    handler.sendMessage(message);

                }
                return 0;
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.d("AddProductActicity", "onFinish:Error"+e);
            }
        });

    }

    //加载PopupWindow，实现下拉列表
    public void getPopWindow(EditText position){
        searchLocationPopupWindow = new PopupWindow();
        searchLocationPopupWindow.setWidth(240);
        searchLocationPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        searchLocationPopupWindow.setContentView(ContentView);
        //获取焦点
        searchLocationPopupWindow.setFocusable(true);
        //点击其他区域关闭popup
        searchLocationPopupWindow.setOutsideTouchable(true);
        searchLocationPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        searchLocationPopupWindow.showAsDropDown(position);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (TAG){
            case "入库库位":
                edt_location.setText(locationList.get(position));
                searchLocationPopupWindow.dismiss();
                break;
            case "产品品号":
                edt_productno.setText(productList.get(position));
                isEditable = true;
                setEditable();
                searchLocationPopupWindow.dismiss();
                break;
        }

    }

    public void setEditable(){
        edt_num.setFocusable(isEditable);
        edt_num.setFocusableInTouchMode(isEditable);
        edt_weight.setFocusable(isEditable);
        edt_weight.setFocusableInTouchMode(isEditable);
    }

    public void setTitle(){
        switch (getIntent().getStringExtra("btnType")){
            case "新增":
                titleView.setTitle("增加零散入库产品");
                break;
            case "修改":
                titleView.setTitle("修改零散入库产品");
                ScatterBean scatterBean = (ScatterBean) getIntent().getSerializableExtra("edtContent");
                edt_productno.setText(scatterBean.getProductId());
                edt_location.setText(scatterBean.getLocation());
                edt_num.setText(scatterBean.getNum());
                edt_weight.setText(scatterBean.getWeight());
                break;
        }
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
}

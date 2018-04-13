package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;
import com.mondschein.titleview.TitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.SpecificWareBean;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.WareApi;


public class ProductInActivity extends AppCompatActivity implements View.OnClickListener,WareApi{

    private static final String TAG = "ProductInActivity";

    final static int RESPONSERESULT_ERROR = -1;
    final static int RESPONSERESULT_SUCCESS = 1;

    //记录仓库
    private String wareName = "";
    private RadioButton btnComplete;
    private RadioButton btnGp12;
    private RadioButton btnButter;
    private RadioButton btnWaste;

    private TitleView titleView;
    private String title;

    private Handler handler;
    private List<SpecificWareBean> list;
    private SpecificWareBean specificWareBean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_in);

        handler = new InnerHandler();

        list = new ArrayList<SpecificWareBean>();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        initData();
        initTitle();
    }

    public void initData(){
        btnComplete = (RadioButton)findViewById(R.id.btn_complete_ware);
        btnGp12 =  (RadioButton)findViewById(R.id.btn_gp_ware);
        btnButter = (RadioButton)findViewById(R.id.btn_buffer_ware);
        btnWaste = (RadioButton)findViewById(R.id.btn_waste_ware);
        titleView = (TitleView) findViewById(R.id.title_pur);

        title = getIntent().getStringExtra("title");

        btnComplete.setOnClickListener(this);
        btnGp12.setOnClickListener(this);
        btnButter.setOnClickListener(this);
        btnWaste.setOnClickListener(this);
        ButtonView buttonView = (ButtonView)findViewById(R.id.btn_next);
        buttonView.setButtonListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                        if (wareName == ""){
                                            Toast.makeText(ProductInActivity.this,"请先选择仓库类型",Toast.LENGTH_SHORT).show();
                                        }else {
                                            SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = spf.edit();
                                            editor.putString("wareTypeName",wareName);
                                            editor.commit();
                                           getPost();
                                        }
                                         }
                                     }
        );

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_complete_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "成品库";
                    btnComplete.setChecked(true);
                    btnButter.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_gp_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "GP12库";
                    btnGp12.setChecked(true);
                    btnComplete.setChecked(false);
                    btnButter.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_buffer_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "缓冲库";
                    btnButter.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_waste_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "废品库";
                    btnWaste.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnButter.setChecked(false);
                }
                break;
            default:
                break;

        }

    }

    class InnerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //请求失败时，响应码不等于200
                case RESPONSERESULT_ERROR:
                    Toast.makeText(ProductInActivity.this,"响应码错误"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case RESPONSERESULT_SUCCESS:
                    String responseResult = (String) msg.obj;
                    JSONObject jsonObject = null;
                    SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                    String store_type_name = spf.getString("wareTypeName",null);
                    Log.d("仓库类型为：",store_type_name);
                    int store_type_id = 0;
                    try {
                        jsonObject = new JSONObject(responseResult);
                        int status = jsonObject.getInt("status");
                        if (status ==0){//返回正确的信息
                            JSONArray array_Store_Type = jsonObject.getJSONArray("store_type");
                            for (int i =0;i<array_Store_Type.length();i++){
                                JSONObject storeType = array_Store_Type.getJSONObject(i);
                                if (storeType.getString("store_type_name").equals(store_type_name)){//判断上一个界面所选仓库类型是否和返回的数据一致
                                    store_type_id = storeType.getInt("store_type");
                                    // Log.d("仓库id",storeType.getInt("store_type")+"");
                                    break;
                                }
                            }
                            if (store_type_id ==0){//值为0说明该仓库类型下无具体仓库
                                Toast.makeText(ProductInActivity.this,"暂无该仓库类型的信息！",Toast.LENGTH_SHORT).show();
                            } else{//获取具体仓库
                                JSONArray array_Store = jsonObject.getJSONArray("store");
                                for (int i =0;i<array_Store.length();i++){
                                    JSONObject store = array_Store.getJSONObject(i);
                                    // Log.d("store_type_id",store_type_id+"");
                                    if (store.getInt("store_type")==store_type_id){//store_type到时根据上一个界面选择类型后传进来,现在为了测试填0
                                        String ware_id = store.getString("store_id");
                                        Log.d(TAG, "handleMessage: store_id"+ware_id);
                                        String ware_name = store.getString("store_name");
                                        Log.d(TAG, "handleMessage: store_name"+ware_name);
                                        specificWareBean = new SpecificWareBean(ware_id,ware_name);
//                                        specificWareBean.setWare_id(ware_id);
//                                        specificWareBean.setWare_name(ware_name);
                                        list.add(specificWareBean);
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                        intent.putExtras(bundle);
                                        intent.putExtra("tag",title);
                                        intent.setClass(ProductInActivity.this, SpecificWareSelectActivity.class);
                                        startActivity(intent);
                                        list.clear();

                                    }

                                }
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void getPost(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String id = spf.getString("sessionid",null);
        String url = URL_PARAMOBTAIN +  "?sessionid="+ id;

        HttpPost.sendHttpRequest(url, "", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d(TAG, "onFinish: "+response);
                Message msg = Message.obtain();
                msg.what = RESPONSERESULT_SUCCESS;
                msg.obj = response;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = RESPONSERESULT_ERROR;
                msg.obj = e;
                handler.sendMessage(msg);
            }
        });
    }

    public void initTitle(){
        switch (title){
            case "生产入库":
                titleView.setTitle("生产入库");
                break;
            case "采购入库":
                titleView.setTitle("采购入库");
                break;
            case "退货入库":
                titleView.setTitle("退货入库");
                break;
            case "零散入库":
                titleView.setTitle("零散入库");
                break;
            case "生产退货入库":
                titleView.setTitle("生产退货入库");
                break;
            case "空箱入库":
                titleView.setTitle("空箱入库");
                break;
            case "产线废料入库":
                titleView.setTitle("产线废料入库");
                break;
            case "移库":
                titleView.setTitle("移库");
                break;
            case "仓库废料入库":
                titleView.setTitle("仓库废料入库");
                break;
        }
    }

}

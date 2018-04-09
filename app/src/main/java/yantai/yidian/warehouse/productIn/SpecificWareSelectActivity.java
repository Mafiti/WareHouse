package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.SpecificWareAdapter;
import yantai.yidian.warehouse.bean.SpecificWareBean;
import yantai.yidian.warehouse.productIn.produce_table.Product;
import yantai.yidian.warehouse.scan.ScanActivity;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.ParamBuilder;
import yantai.yidian.warehouse.util.WareApi;

public class SpecificWareSelectActivity extends AppCompatActivity implements WareApi{

    private ListView listView;
    private SpecificWareAdapter specificWareAdapter;
    private ButtonView btn_sure;
    private Handler handler;
    private List<SpecificWareBean> list;
    private SpecificWareBean specificWareBean;
    private String wareName = "";
    private static int selectPosition=-1;

    final static int RESPONSERESULT_ERROR = -1;
    final static int RESPONSERESULT_SUCCESS = 1;

    public static int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specific_ware_select);
        getPost();
        //handle用于发送和接受处理message
        handler = new InnerHandler();
        initView();

        list = new ArrayList<>();

        specificWareAdapter = new SpecificWareAdapter(list,this);
        listView.setAdapter(specificWareAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                specificWareAdapter.notifyDataSetChanged();
                specificWareBean = list.get(position);
                wareName = specificWareBean.getWare_name();
                Log.d("specificWareBean:",wareName);
            }
        });

        btn_sure.setButtonListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (wareName ==""){
                                                 Toast.makeText(SpecificWareSelectActivity.this,"请先选择具体仓库",Toast.LENGTH_SHORT).show();
                                             }else {
                                                 SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                                                 SharedPreferences.Editor editor = spf.edit();
                                                 editor.putString("wareName",wareName);
                                                 editor.commit();
                                                 startActivity(new Intent(SpecificWareSelectActivity.this,WareChooseActivity.class));
                                                 finish();
                                             }

                                         }
                                     }
        );
    }

    public void initView(){
        btn_sure = (ButtonView) findViewById(R.id.btn_specific_ware_sure);
        listView = (ListView) findViewById(R.id.lv_specific_ware);
    }

    class InnerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //请求失败时，响应码不等于200
                case RESPONSERESULT_ERROR:
                    Toast.makeText(SpecificWareSelectActivity.this,"响应码错误"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case RESPONSERESULT_SUCCESS:
                    String responseResult = (String) msg.obj;
                    JSONObject jsonObject = null;
                    SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                    String store_type_name = spf.getString("wareTypeName",null);
                    //Log.d("仓库类型为：",store_type_name);
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
                                    //Log.d("仓库id",storeType.getInt("store_type")+"");
                                    break;
                                }
                            }
                            if (store_type_id ==0){//值为0说明该仓库类型下无具体仓库
                                Toast.makeText(SpecificWareSelectActivity.this,"暂无该仓库类型的信息！",Toast.LENGTH_SHORT).show();
                            } else{//获取具体仓库
                                JSONArray array_Store = jsonObject.getJSONArray("store");
                                for (int i =0;i<array_Store.length();i++){
                                    JSONObject store = array_Store.getJSONObject(i);
                                    Log.d("store_type_id",store_type_id+"");
                                    if (store.getInt("store_type")==store_type_id){//store_type到时根据上一个界面选择类型后传进来,现在为了测试填0
                                        String ware_id = store.getString("store_id");
                                        String ware_name = store.getString("store_name");
                                        specificWareBean = new SpecificWareBean();
                                        specificWareBean.setWare_id(ware_id);
                                        specificWareBean.setWare_name(ware_name);
                                        list.add(specificWareBean);
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


}

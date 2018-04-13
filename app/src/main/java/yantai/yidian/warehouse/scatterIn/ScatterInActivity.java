package yantai.yidian.warehouse.scatterIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.MainActivity;
import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ScatterAdapter;
import yantai.yidian.warehouse.bean.ScatterBean;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.WareApi;

public class ScatterInActivity extends AppCompatActivity implements View.OnClickListener,ScatterAdapter.Callback, AdapterView.OnItemClickListener ,WareApi{

    private ListView listView;
    private ScatterAdapter scatterAdapter;
    private List<ScatterBean> list;
    private ButtonView buttonView;
    private ScatterBean scatterBean;
    private TextView wareName;
    private TextView operatorName;
    private ImageView add;
    private int first = 0;//用于标记该界面是否为第一次进入
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_in);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG","onResume");
        Log.d("first",first+"");
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        first = Integer.parseInt(spf.getString("first",null));
        if (first ==1){//非首次进入时加载Adapter
            scatterBean = (ScatterBean) getIntent().getSerializableExtra("scatterBean");
            String json = spf.getString("scatterList",null);
            if (json!=null){
                Gson gson = new Gson();
                Type type  = new TypeToken<List<ScatterBean>>(){}.getType();
                list = gson.fromJson(json,type);
            }
            if (getIntent().getSerializableExtra("scatterBean")!=null){
                list.add(scatterBean);
            }
            getIntent().removeExtra("scatterBean");
            scatterAdapter = new ScatterAdapter(list, this, this);
            listView.setAdapter(scatterAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    protected void initView(){
        listView = (ListView) findViewById(R.id.lv_scatter);
        buttonView = (ButtonView) findViewById(R.id.btn_in_sure);
        wareName = (TextView) findViewById(R.id.tv_wareName);
        operatorName = (TextView) findViewById(R.id.tv_operatorName);
        add = (ImageView) findViewById(R.id.img_add);
        list = new ArrayList<>();

        add.setOnClickListener(this);
        //提交按钮
        buttonView.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size()==0){
                    Toast.makeText(ScatterInActivity.this,"提交的数据不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    post();
                    handler = new InnerHandle();
                    Intent intent = new Intent(ScatterInActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //新增按钮
            case R.id.img_add:
                first = 1;
                SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("first",first+"");
                //把list转换为String保存到SharedPreferences中
                Gson gson = new Gson();
                String json = gson.toJson(list);
                Log.d("json",json);
                editor.putString("scatterList",json);
                editor.commit();
                Intent intent = new Intent(ScatterInActivity.this,AddProductActivity.class);
                intent.putExtra("btnType","新增");
                startActivity(intent);
                break;
        }
    }


    /*
        *接口方法响应listView内部按钮点击事件
        * */
    @Override
    public void click(View v) {
        //保存展示在listView的数据
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("scatterList",json);
        editor.commit();
        Button alter = (Button) findViewById(R.id.btn_alter);
        Button delete = (Button) findViewById(R.id.btn_delete);
        switch (v.getId()){
            //修改按钮
            case R.id.btn_alter:
                int indexAlter = (int) v.getTag();
                ScatterBean scatterBean2 = list.get(indexAlter);
                alter.setSelected(true);
                delete.setSelected(false);
                Intent intent = new Intent(ScatterInActivity.this,AddProductActivity.class);
                intent.putExtra("btnType","修改");
                intent.putExtra("edtContent",scatterBean2);
                startActivity(intent);
                break;
            //删除按钮
            case R.id.btn_delete:
                int indexDel = (int) v.getTag();
                alter.setSelected(false);
                delete.setSelected(true);
                Intent intentDel = new Intent(ScatterInActivity.this,DeleteProductActivity.class);
                intentDel.putExtra("indexDel",indexDel+"");
                startActivity(intentDel);
                break;
        }
    }

    //测试用的
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.d("listview",position+"");
        Toast.makeText(this,"listview点击的位置是"+scatterBean.getLocation(),Toast.LENGTH_SHORT).show();
    }

    class InnerHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            String postResult = (String) msg.obj;
            JSONObject jsonObject = JSONObject.fromObject(postResult);
            int status = jsonObject.getInt("status");
            if (status ==0){
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject json = new JSONObject();
                String remark;
                for (int i=0;i<jsonArray.size();i++){
                    json = jsonArray.getJSONObject(i);
                    remark = json.getString("remark");//提交成功时remark为保存成功
                    Toast.makeText(ScatterInActivity.this,remark,Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    //提交本界面的数据
    public void post(){
        SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
        String sessionid = sp.getString("sessionid",null);
        String url = URL_mBillPartDetail+"sessionid="+sessionid;
        String postData="";
        JSONObject jsonObject = new JSONObject();
        List<Object> tempList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            jsonObject.clear();
            jsonObject.put("product_noid",list.get(i).getProductId());
            jsonObject.put("loc_id",GetLocIdByLocName(list.get(i).getLocation()));
            jsonObject.put("fact_num",list.get(i).getNum());
            jsonObject.put("weight",list.get(i).getWeight());
            tempList.add(jsonObject);
        }
        JSONObject jsonPost = new JSONObject();
        jsonPost.put("status",0);
        jsonPost.put("count",tempList.size());
        jsonPost.put("bill_id",1);
        jsonPost.put("result",tempList);

        postData = jsonPost.toString();
        HttpPost.sendHttpRequest(url, postData, new HttpCallbackListener() {
            @Override
            public int onFinish(String response) {
                if(response=="数据提交失败")
                {
                    Toast.makeText(ScatterInActivity.this,response,Toast.LENGTH_SHORT).show();
                }else {
                    Message msg = Message.obtain();
                    msg.obj = response;
                    handler.sendMessage(msg);
                }
                return 0;
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ScatterInActivity.this,"数据提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //提交数据时需要提交库位id而不是库位名
    //根据选择的库位名去获取相应的库位id
    public int GetLocIdByLocName(final String locName){
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
                if (response == "数据提交失败") {
                    Toast.makeText(ScatterInActivity.this, "响应失败，响应码为" + response, Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    int status = jsonObject.getInt("status");
                    if (status != 0) {
                        Toast.makeText(ScatterInActivity.this, "获取库位信息失败", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject locationItem;
                        String locationName;
                        int locationId;
                        for (int i = 0; i < jsonArray.size(); i++) {
                            locationItem = jsonArray.getJSONObject(i);
                            locationName = locationItem.getString("loc_name");
                            if (locationName == locName){
                                locationId = locationItem.getInt("loc_id");
                                return locationId;
                            }
                        }

                    }
                }
                return 0;
            }

            @Override
            public void onError(Exception e) {

            }
        });

        return 0;
    }
}

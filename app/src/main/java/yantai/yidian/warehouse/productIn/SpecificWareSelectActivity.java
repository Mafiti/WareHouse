package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.SpecificWareAdapter;
import yantai.yidian.warehouse.bean.SpecificWareBean;
import yantai.yidian.warehouse.scan.ScanActivity;
import yantai.yidian.warehouse.util.ParamBuilder;
import yantai.yidian.warehouse.util.WareApi;

public class SpecificWareSelectActivity extends AppCompatActivity implements WareApi{

    private ListView listView;
    private SpecificWareAdapter specificWareAdapter;
    private ButtonView btn_sure;
    private Handler handler;
    private List<SpecificWareBean> list;
    private SpecificWareBean specificWareBean;

    final static int RESPONSERESULT_ERROR = -1;
    final static int RESPONSERESULT_SUCCESS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specific_ware_select);
        //handle用于发送和接受处理message
        handler = new InnerHandler();
        initView();

         /*
        for (int i = 0;i<10;i++){
            list.add(new SpecificWareBean("成品库一号","xxx组织",""));
        }
        */
        list = new ArrayList<>();

        specificWareAdapter = new SpecificWareAdapter(list,this);
        listView.setAdapter(specificWareAdapter);
        WorkThread thread = new WorkThread();
        thread.start();

        btn_sure.setButtonListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             startActivity(new Intent(SpecificWareSelectActivity.this,ScanActivity.class));
                                             finish();
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
                    Toast.makeText(SpecificWareSelectActivity.this,"响应码错误"+msg.arg1,Toast.LENGTH_SHORT).show();
                    break;
                case RESPONSERESULT_SUCCESS:
                    String responseResult = (String) msg.obj;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseResult);
                        int status = jsonObject.getInt("status");
                        JSONArray array_Store = jsonObject.getJSONArray("store");
                        JSONArray array_vender = jsonObject.getJSONArray("vender");
                        if (status ==0){
                            for (int i =0;i<array_Store.length();i++){
                                JSONObject store = array_Store.getJSONObject(i);
                                JSONObject vender = array_vender.getJSONObject(i);
                                //store_type到时根据上一个界面选择类型后传进来,现在为了测试填0
                                if (store.getInt("store_type")==1){
                                    String ware_name = store.getString("store_name");
                                    String organization = vender.getString("vender_name");
                                    specificWareBean = new SpecificWareBean();
                                    specificWareBean.setWare_name(ware_name);
                                    specificWareBean.setOrganization(organization);
                                    specificWareBean.setUse_tag("");
                                    list.add(specificWareBean);
                                }
                            }
                            Toast.makeText(SpecificWareSelectActivity.this, "请求成功，返回的数据为："+array_Store, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



            }
        }
    }

    class WorkThread extends Thread{
        @Override
        public void run() {

            String id = "92D84AAD121190462E763B7D773F144C";

            //格式转换
            ParamBuilder builder = new ParamBuilder();
            builder.add("sessionid",id);
            String params = builder.build();

            Log.d("PARAMS",params);
            //准备联网
            HttpURLConnection conn = null;
            URL url = null;

            try {
                url = new URL(URL_PARAMOBTAIN);//创建网址对象
                conn = (HttpURLConnection) url.openConnection();//创建连接对象

                conn.setRequestMethod(METHOD_POST);//配置请求类型
                conn.addRequestProperty("Content-Length",params.length()+"");//配置参数：数据长度
                conn.addRequestProperty("ContentType","application/json");//配置参数：类型
                conn.addRequestProperty("Charset","UTF-8");//配置参数，字符编码
                conn.setDoOutput(true);//向服务器输出数据
                conn.getOutputStream().write(params.getBytes());//向服务器输出请求数据

                int responseCode = conn.getResponseCode();//获取响应码，正常时为200

                //判断响应码
                if (responseCode !=200){
                    //出错
                    Message msg = Message.obtain();//获取Message对象
                    msg.what = RESPONSERESULT_ERROR;//用来标记信息的类型
                    msg.arg1 = responseCode;//添加要传送的数据，参数有arg1、arg2、obj
                    handler.sendMessage(msg);//用handler发送信息
                }else {
                    //获取数据成功
                    InputStream in = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(isr);
                    String responseResult = br.readLine();
                    //有关UI操作不能在子线程中进行，须由主线程实现
                    //封装信息，通知主线程更新UI
                    Message msg = Message.obtain();
                    msg.what = RESPONSERESULT_SUCCESS;
                    msg.obj = responseResult;
                    handler.sendMessage(msg);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (conn != null){
                    conn.disconnect();
                }
            }
        }
    }

}

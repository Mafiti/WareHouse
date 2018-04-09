package yantai.yidian.warehouse.productIn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;

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
import yantai.yidian.warehouse.productIn.feedback.ScanFeedbackActivity;
import yantai.yidian.warehouse.util.WareApi;

public class LocationSelectAutoOrManualActivity extends AppCompatActivity implements WareApi{
    private static final String TAG = "LocationSelectAutoOrMan";

    private ButtonView btn_sure;
    private Button btn_people_select;
    private List<LocationInfo> locationInfoList;
    private TextView systemText1;
    private TextView systemText2;
    private EditText people_edittext;
    RadioButton btn_system_select;
    Button help;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select_auto_or_manual);
        initView();
        WorkThread workThread = new WorkThread();
        workThread.start();
        btn_sure.setButtonListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      startActivity(new Intent(LocationSelectAutoOrManualActivity.this,ScanFeedbackActivity.class));
                                                  }
                                              }
        );

        btn_system_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_edittext.setText("");
                if(v==btn_system_select) {
                    btn_system_select.setChecked(true);
                    btn_system_select.setBackgroundResource(R.drawable.system_select);
                    btn_system_select.setTextColor(getResources().getColor(R.color.text_choose_color));
                    findViewById(R.id.linear_select).setFocusable(true);
                    findViewById(R.id.linear_select).setFocusableInTouchMode(true);
                    findViewById(R.id.linear_select).requestFocus();
                }
            }
        });
        btn_people_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btn_people_select)
                {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("locationInfoList",(ArrayList<LocationInfo>)locationInfoList);
                    intent.putExtras(bundle);
                    intent.setClass(LocationSelectAutoOrManualActivity.this,LocationSelectActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==help)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, Help01Activity.class);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.linear_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.linear_select).setFocusable(true);
                findViewById(R.id.linear_select).setFocusableInTouchMode(true);
                findViewById(R.id.linear_select).requestFocus();
            }
        });

        people_edittext.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if(touch_flag==2){
                    touch_flag=0;
                    btn_system_select.setChecked(false);
                    btn_system_select.setBackgroundResource(R.drawable.system_unselect);
                    btn_system_select.setTextColor(getResources().getColor(R.color.text_unchoose_color));
                }
                return false;
            }
        });

       /* help=(ImageButton) findViewById(R.id.imageView);
        testview1=(EditText) findViewById(R.id.editText);
        rb = (RadioButton) findViewById(R.id.rb1);
        search2 = (ImageButton) findViewById(R.id.search2);


        testview1.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if(touch_flag==2){
                    touch_flag=0;
                    rb.setChecked(false);
                }
                return false;
            }
        });

        final GlobalValue globalValue = new GlobalValue();
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v==rb) {
                    rb.setChecked(true);
                    testview1.clearFocus();
                    closeKeyboard();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==help)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, Help01Activity.class);
                    startActivity(intent);
                    LocationSelectAutoOrManualActivity.this.finish();
                }
            }
        });

        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==search2)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, LocationSelectActivity.class);
                    startActivity(intent);
                    LocationSelectAutoOrManualActivity.this.finish();
                }
            }
        });
        */

    }

    public void initView(){
        people_edittext=(EditText)findViewById(R.id.people_edittext);
        btn_sure = (ButtonView) findViewById(R.id.buttonbottom);
        help=(Button) findViewById(R.id.btn_help_system);
        btn_system_select = (RadioButton) findViewById(R.id.rb_system_ware_num);
        btn_people_select = (Button) findViewById(R.id.btn_people_select);
        locationInfoList = new ArrayList<LocationInfo>();
        handler = new InnerHandler();
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

    //json 处理
    @SuppressLint("HandlerLeak")
    class InnerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, "handleMessage: "+msg.obj);
            switch (msg.what){
                case 1:
                    // Toast.makeText(LocationSelectAutoOrManualActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT);
                    break;
                case 0:
                    // 获取响应的正文
                    String responseLoc = (String)msg.obj;
                    // 将正文转换为Json对象
                    JSONObject jsonLoc = null;

                    try{
                        jsonLoc = new JSONObject(responseLoc);
                        int status = jsonLoc.getInt("status");
                        if(status == 0){
                            String location = null;
                            JSONArray jsonLocArray = jsonLoc.getJSONArray("result");
                            for(int i = 0;i<jsonLocArray.length();i++){

                                JSONObject locObject = jsonLocArray.getJSONObject(i);
                                int loc_id = locObject.getInt("loc_id");
                                String loc_name = locObject.getString("loc_name");
                                int max_num = locObject.getInt("max_num");
                                int remain_num = locObject.getInt("remain_num");
                                int al_num = max_num - remain_num;
                                LocationInfo locationInfo = new LocationInfo(loc_id,loc_name,max_num,al_num,remain_num);
                                locationInfoList.add(locationInfo);
                                if(i == 0){
                                    location = loc_name;
                                    btn_system_select.setText(location);
                                    String s1 = "该库位已放同批次产品箱数为:"+ String.valueOf(al_num) +"箱";
                                    String s2 = "库存容量为:"+String .valueOf(max_num) +"箱,还能放该产品"+ String .valueOf(remain_num) +"箱";
                                    systemText1 = (TextView)findViewById(R.id.system_text_1);
                                    systemText2 = (TextView)findViewById(R.id.system_text_2);
                                    systemText1.setText(s1.toString());
                                    systemText2.setText(s2.toString());
                                }
                            }


                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

            }

        }
    }

    //android 线程
    class WorkThread extends Thread {
        @Override
        public void run() {

            HttpURLConnection conn = null;
            URL url = null;

            JSONObject object = new JSONObject();
            JSONObject object1 = new JSONObject();
            JSONArray object2 = new JSONArray();
            try {
                object.put("status", "0");
                object.put("count", "0");
                object.put("bill_id","1");
//                object.put("bill_id", "1");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try{
                url = new URL(URL_mBillInLoc);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(METHOD_POST);
                conn.addRequestProperty("Charset", "UTF-8");
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                conn.addRequestProperty("contentType",
                        "application/x-www/form-urlencoded");
                conn.setRequestProperty("accept","application/json");
                String content = String.valueOf(object);
//                        "{\"status\":0,\"count\":1,\"result\":[{\"bill_id\":1,\"box_no\":\"TMX0100124\"}]} ";
                //String.valueOf(object);
                byte[] writebytes = content.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                Log.d(TAG, "run: 11111");
                conn.getOutputStream().write(content.getBytes());
                int responCode = conn.getResponseCode();

                if (responCode != 200) {
                    // right

                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.arg1 = responCode; // 将响应码封装在消息中
                    handler.sendMessage(msg);
                    //System.out.println("response result = " + responCode);
                } else {
                    // error
                    conn.setInstanceFollowRedirects(false);
                    InputStream in = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in,"UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String responseResult = br.readLine();
                    String response = URLDecoder.decode(responseResult,"UTF-8");
                    in.close();
                    //获取session
                    //String headerCookie = "12";
                    //headerCookie +=	conn.getHeaderField("Content-Type");
                    //System.out.println(headerCookie);
                    //session = headerCookie.split(";")[0];

                    // System.out.println("response result = " +
                    // responseResult);
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = response;
                    handler.sendMessage(msg);
                }

            }catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                conn = null;
            }

        }
    }


    /**
     * 设置跳转  接受返回数据
     * @param requestCode
     *              请求码
     * @param resultCode
     *              返回码
     * @param data
     *              返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  如果请求码与返回码等于预期设置的值  则进行后续操作
        if (requestCode == 1 && resultCode == 2){
            // 获取返回的数据
            String backData = data.getStringExtra("location");
            // 设置给页面的文本TextView显示
            people_edittext.setText(backData);
        }
    }


    /*private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(reader != null){
                        try{
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private  void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }*/

}

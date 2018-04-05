package yantai.yidian.warehouse.log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import yantai.yidian.warehouse.MainActivity;
import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.util.HttpCallbackListener;
import yantai.yidian.warehouse.util.HttpPost;
import yantai.yidian.warehouse.util.ParamBuilder;
import yantai.yidian.warehouse.util.WareApi;

public class LauncherActivity extends AppCompatActivity implements WareApi{
    private static final String TAG = "LauncherActivity";

    private Button btn_log;
    private EditText edit_name;
    private EditText edit_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        initData();

//        //若是不是第一次登陆，直接跳转主界面
//        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
//        boolean isFirstRun = sp.getBoolean("isFirstLog", true);
//        if(!isFirstRun){
//            startActivity(new Intent(LauncherActivity.this,MainActivity.class));
//            finish();
//        }

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String userName = edit_name.getText().toString();
                String pwd = edit_pwd.getText().toString();
                ParamBuilder builder = new ParamBuilder();
                builder.add(PARAM_userid,userName);
                builder.add(PAREM_password,pwd);
                String parem = builder.build();
                String url = URL_LOGIN + parem.toString();
                Log.d(TAG, "onClick: "+url);
                HttpPost.sendHttpRequest(url, "", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));

//                        //标记不是首次运行
//                        SharedPreferences sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putBoolean("isFirstLog", false);
//                        editor.commit();
//                        //销毁当前Activity
//                        finish();
                        Log.d(TAG, "onFinish: "+ response.toString());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }
                });
            }
        });



        //使edittext失去焦点
        findViewById(R.id.launcher_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.launcher_layout).setFocusable(true);
                findViewById(R.id.launcher_layout).setFocusableInTouchMode(true);
                findViewById(R.id.launcher_layout).requestFocus();
            }
        });
    }

    public void initData(){
        btn_log = (Button) findViewById(R.id.btn_log);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
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

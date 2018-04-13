package yantai.yidian.warehouse.purchaseIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import yantai.yidian.warehouse.R;

public class AddPurInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AddPurInfoActivity";

    private Button btnSelectType;
    private Button btnNext;                 //下一步按钮
    private AutoCompleteTextView textType;  //产品种类
    private AutoCompleteTextView textNum;   //产品品号
    private AutoCompleteTextView textBetch; //生产批次
    private AutoCompleteTextView textVender; //采购厂家

    private ImageView x1;
    private ImageView x2;
    private ImageView x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pur_info);

        initData();
    }

    protected void initData(){
        btnSelectType = (Button)findViewById(R.id.select_type);
        textType = (AutoCompleteTextView)findViewById(R.id.item_type_text_pur);
        textNum = (AutoCompleteTextView)findViewById(R.id.item_num_text_pur);
        textBetch = (AutoCompleteTextView)findViewById(R.id.produce_time_text_pur);
        textVender = (AutoCompleteTextView)findViewById(R.id.purchase_vender_text_pur) ;
        x1 = (ImageView) findViewById(R.id.close1_pur);
        x2 = (ImageView) findViewById(R.id.close3_pur);
        x3 = (ImageView) findViewById(R.id.close4_pur);
        btnNext = (Button)findViewById(R.id.btn_enter_to_ware_pur);

        x1.setOnClickListener(this);
        x2.setOnClickListener(this);
        x3.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSelectType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_type:
                Intent intent = new Intent(AddPurInfoActivity.this,PurWareChooseActivity.class);
                startActivityForResult(intent,1);
                break;

            case R.id.btn_enter_to_ware_pur:
                if(textNum.getText().toString().isEmpty()|| textNum.getText().toString().isEmpty()|| textBetch.getText().toString().isEmpty() || textVender.getText().toString().isEmpty()) {
                    Toast.makeText(AddPurInfoActivity.this,"有一项数据为空",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("textNum", textNum.getText().toString());
                    intent1.putExtra("textType", textNum.getText().toString());
                    intent1.putExtra("textBetch", textBetch.getText().toString());
                    intent1.putExtra("textVender", textVender.getText().toString());
                    intent1.setClass(AddPurInfoActivity.this, Purchase.class);
                    startActivity(intent1);
                }
                break;
            case R.id.close1_pur:
                textNum.setText("");
                break;
            case R.id.close3_pur:
                textBetch.setText("");
                break;
            case R.id.close4_pur:
                textVender.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1&&resultCode==2){
            String requestType = data.getStringExtra("pruchaseType");
            textType.setText(requestType);
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

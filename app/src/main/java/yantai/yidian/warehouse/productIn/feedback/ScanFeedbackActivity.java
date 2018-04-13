package yantai.yidian.warehouse.productIn.feedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mondschein.btnview.ButtonView;
import com.mondschein.titleview.TitleView;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.productIn.produce_table.Product;
import yantai.yidian.warehouse.scan.ScanActivity;

public class ScanFeedbackActivity extends AppCompatActivity {

    private TitleView tv_top_title;
    private TextView tv_title;
    private RelativeLayout rlyt_img;
    private TextView tv_msg;
    private TextView tv_msg_detail;
    private ButtonView btn_scan_sure;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        status = getIntent().getIntExtra("status",0);
        initView();
        changeText();
        btn_scan_sure.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    public void initView(){
        tv_top_title = (TitleView) findViewById(R.id.title_pur);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rlyt_img = (RelativeLayout) findViewById(R.id.rlyt_img);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg_detail = (TextView) findViewById(R.id.tv_msg_detail);
        btn_scan_sure = (ButtonView) findViewById(R.id.btn_scan_sure);
    }
    public void changeText(){
        SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
        String methodToWare = spf.getString("methodToWare",null);
        switch (methodToWare.toString()){
            case "生产入库":
                tv_top_title.setTitle("生产入库");
                tv_title.setText("生产入库扫码");
                if (status ==0){
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.right));
                    tv_msg.setText("扫码操作成功");
                    tv_msg_detail.setText("");
                }else if (status ==1){
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.error));
                    tv_msg.setText("扫码操作失败");
                    tv_msg_detail.setText("箱号不存在或没有登记.");
                }else {
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.error));
                    tv_msg.setText("扫码操作失败");
                    tv_msg_detail.setText("箱中产品信息不正确.");
                }
                break;
            case "采购入库":
                tv_top_title.setTitle("采购入库");
                tv_title.setText("采购入库扫码");
                if (status ==0){
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.right));
                    tv_msg.setText("扫码操作成功");
                    tv_msg_detail.setText("");
                }else if (status ==1){
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.error));
                    tv_msg.setText("扫码操作失败");
                    tv_msg_detail.setText("箱号不存在或没有登记.");
                }else {
                    rlyt_img.setBackground(getResources().getDrawable(R.drawable.error));
                    tv_msg.setText("扫码操作失败");
                    tv_msg_detail.setText("箱中产品信息不正确.");
                }
                break;
        }

    }

}

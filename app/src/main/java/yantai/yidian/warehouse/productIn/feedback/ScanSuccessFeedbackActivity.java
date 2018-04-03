package yantai.yidian.warehouse.productIn.feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mondschein.btnview.ButtonView;
import com.mondschein.titleview.TitleView;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.scan.ScanActivity;

public class ScanSuccessFeedbackActivity extends AppCompatActivity {

    private TitleView tv_title;
    private TextView tv_right;
    private ButtonView btn_scan_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        changeText();
        btn_scan_sure.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanSuccessFeedbackActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initView(){
        tv_title = (TitleView) findViewById(R.id.title_pur);
        tv_right = (TextView) findViewById(R.id.tv_msg);
        btn_scan_sure = (ButtonView) findViewById(R.id.btn_scan_sure);
    }
    public void changeText(){
        tv_title.setTitle("生产入库-扫码");
        tv_right.setText("扫码成功");
    }

}

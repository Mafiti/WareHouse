package yantai.yidian.warehouse.productIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import yantai.yidian.warehouse.R;

public class WareChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton rbtnAWare;
    private RadioButton rbtnBWare;
    private RadioButton rbtnASWare;
    private RadioButton rbtnEllipsis;
    private int wareType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_choose);
        initData();
    }

    public void initData(){
        rbtnAWare = (RadioButton) findViewById(R.id.btn_a_ware);
        rbtnBWare = (RadioButton) findViewById(R.id.btn_b_ware);
        rbtnASWare = (RadioButton) findViewById(R.id.btn_as_ware);
        rbtnEllipsis = (RadioButton) findViewById(R.id.btn_____ware) ;

        rbtnAWare.setOnClickListener(this);
        rbtnBWare.setOnClickListener(this);
        rbtnASWare.setOnClickListener(this);
        rbtnEllipsis.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_a_ware:
                rbtnAWare.setChecked(true);
                rbtnBWare.setChecked(false);
                rbtnASWare.setChecked(false);
                rbtnEllipsis.setChecked(false);
                rbtnEllipsis.setBackgroundResource(R.drawable.ellipsis);
                rbtnAWare.setTextColor(this.getResources().getColor(R.color.choose_ware_btn));
                rbtnBWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnASWare.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.btn_b_ware:
                rbtnAWare.setChecked(false);
                rbtnBWare.setChecked(true);
                rbtnASWare.setChecked(false);
                rbtnEllipsis.setChecked(false);
                rbtnEllipsis.setBackgroundResource(R.drawable.ellipsis);
                rbtnAWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnBWare.setTextColor(this.getResources().getColor(R.color.choose_ware_btn));
                rbtnASWare.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.btn_as_ware:
                rbtnAWare.setChecked(false);
                rbtnBWare.setChecked(false);
                rbtnASWare.setChecked(true);
                rbtnEllipsis.setChecked(false);
                rbtnEllipsis.setBackgroundResource(R.drawable.ellipsis);
                rbtnAWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnBWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnASWare.setTextColor(this.getResources().getColor(R.color.choose_ware_btn));
                break;
            case  R.id.btn_____ware:
                rbtnAWare.setChecked(false);
                rbtnBWare.setChecked(false);
                rbtnASWare.setChecked(false);
                rbtnEllipsis.setBackgroundResource(R.drawable.ellipsis_white);
                rbtnAWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnBWare.setTextColor(this.getResources().getColor(R.color.white));
                rbtnASWare.setTextColor(this.getResources().getColor(R.color.white));

        }
    }
}

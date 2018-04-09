package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.productIn.produce_table.Product;

public class WareChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton rbtnAWare;
    private RadioButton rbtnBWare;
    private RadioButton rbtnASWare;
    private RadioButton rbtnEllipsis;
    private ButtonView btn_production_next;
    private String productType = "";

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
        btn_production_next = (ButtonView) findViewById(R.id.btn_production_type_next);

        rbtnAWare.setOnClickListener(this);
        rbtnBWare.setOnClickListener(this);
        rbtnASWare.setOnClickListener(this);
        rbtnEllipsis.setOnClickListener(this);
        btn_production_next.setButtonListener(this);
        btn_production_next.setButtonListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (productType ==""){
                                                 Toast.makeText(WareChooseActivity.this,"请先选择产品类型",Toast.LENGTH_SHORT).show();
                                             }else {
                                                 SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                                                 SharedPreferences.Editor editor = spf.edit();
                                                 editor.putString("productType",productType);
                                                 editor.commit();
                                                 startActivity(new Intent(WareChooseActivity.this,Product.class));
                                                 finish();
                                             }

                                         }
                                     }
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_a_ware:
                productType = rbtnAWare.getText().toString();
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
                productType = rbtnBWare.getText().toString();
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
                productType = rbtnASWare.getText().toString();
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
                break;

        }
    }
}

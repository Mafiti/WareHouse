package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import yantai.yidian.warehouse.R;

public class ProductInActivity extends AppCompatActivity implements View.OnClickListener{
    //记录仓库
    private String wareName = "";
    private RadioButton btnComplete;
    private RadioButton btnGp12;
    private RadioButton btnButter;
    private RadioButton btnWaste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_in);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        initData();
    }

    public void initData(){
        btnComplete = (RadioButton)findViewById(R.id.btn_complete_ware);
        btnGp12 =  (RadioButton)findViewById(R.id.btn_gp_ware);
        btnButter = (RadioButton)findViewById(R.id.btn_buffer_ware);
        btnWaste = (RadioButton)findViewById(R.id.btn_waste_ware);
        btnComplete.setOnClickListener(this);
        btnGp12.setOnClickListener(this);
        btnButter.setOnClickListener(this);
        btnWaste.setOnClickListener(this);
        ButtonView buttonView = (ButtonView)findViewById(R.id.btn_next);
        buttonView.setButtonListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                        if (wareName == ""){
                                            Toast.makeText(ProductInActivity.this,"请先选择仓库类型",Toast.LENGTH_SHORT).show();
                                        }else {
                                            SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = spf.edit();
                                            editor.putString("wareTypeName",wareName);
                                            editor.commit();
                                            startActivity(new Intent(ProductInActivity.this,SpecificWareSelectActivity.class));
                                            finish();
                                        }
                                         }
                                     }
        );

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_complete_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "成品库";
                    btnComplete.setChecked(true);
                    btnButter.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_gp_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "GP12库";
                    btnGp12.setChecked(true);
                    btnComplete.setChecked(false);
                    btnButter.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_buffer_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "缓冲库";
                    btnButter.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_waste_ware:
                if(((RadioButton)view).isChecked()){
                    wareName = "废品库";
                    btnWaste.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnButter.setChecked(false);
                }
                break;
            default:
                break;

        }

    }
}

package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.mondschein.btnview.ButtonView;

import yantai.yidian.warehouse.R;

public class ProductInActivity extends AppCompatActivity implements View.OnClickListener{
    //记录仓库
    private int wareNum = 0;
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
                                             startActivity(new Intent(ProductInActivity.this,SpecificWareSelectActivity.class));
                                         }
                                     }
        );

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_complete_ware:
                if(((RadioButton)view).isChecked()){
                    wareNum = 1;
                    btnComplete.setChecked(true);
                    btnButter.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_gp_ware:
                if(((RadioButton)view).isChecked()){
                    wareNum = 2;
                    btnGp12.setChecked(true);
                    btnComplete.setChecked(false);
                    btnButter.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_buffer_ware:
                if(((RadioButton)view).isChecked()){
                    wareNum = 3;
                    btnButter.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnWaste.setChecked(false);
                }
                break;
            case R.id.btn_waste_ware:
                if(((RadioButton)view).isChecked()){
                    wareNum = 4;
                    btnWaste.setChecked(true);
                    btnComplete.setChecked(false);
                    btnGp12.setChecked(false);
                    btnButter.setChecked(false);
                }
                break;
            case R.id.btn_next:

                break;
            default:
                break;

        }
    }
}

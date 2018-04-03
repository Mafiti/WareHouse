package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.productIn.produce_table.Product_sub;

public class CheckActivity extends AppCompatActivity {

    private RadioButton RadioButton1;
    private RadioButton RadioButton2;
    private Spinner Spinner1;
    private Spinner Spinner2;
    private RadioGroup RadioGroup1;
    private ArrayAdapter<String> roleAdapter,staffAdapter;
    private ButtonView buttonbottom1;

    private String[] role = {"所有角色","A类","B类","C类"};
    private String[][] staff = {{"所有员工"},{"A类一号","A类二号","A类三号"},{"B类一号","B类二号","B类三号"},{"C类一号","C类二号","C类三号"}};
    private int rolePosition,staffposition;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        buttonbottom1 = (ButtonView)findViewById(R.id.buttonbottom1);
        RadioButton1 = (RadioButton)findViewById(R.id.RadioButton1);
        RadioButton2 = (RadioButton)findViewById(R.id.RadioButton2);
        Spinner1 = (Spinner)findViewById(R.id.spinner1);
        Spinner2 = (Spinner)findViewById(R.id.spinner2);
        RadioGroup1=(RadioGroup)findViewById(R.id.RadioGroup1);

        roleAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, role);
        staffAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, staff[0]);

        //为所有Spinner添加适配器
        Spinner1.setAdapter(roleAdapter);
        Spinner2.setAdapter(staffAdapter);


        /*RadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(checkedId == RadioButton1.getId()){
                    Spinner1.setClickable(true);
                    Spinner2.setClickable(true);
                }else if(checkedId == RadioButton2.getId()){
                    Spinner1.setClickable(false);
                    Spinner2.setClickable(false);
                }
            }
        });*/

        Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rolePosition = i;//记录第一个Spinner选择的位置
                //选中了角色，就得限制员工，重新设定适配器，改变其中显示的数据
                staffAdapter = new ArrayAdapter<String>(CheckActivity.this,
                        android.R.layout.simple_selectable_list_item, staff[i]);
                Spinner2.setAdapter(staffAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner1.setEnabled(true);
                Spinner2.setEnabled(true);
            }
        });
        RadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner1.setEnabled(false);
                Spinner2.setEnabled(false);
            }
        });

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckActivity.this,"helo",Toast.LENGTH_SHORT).show();

            }
        };
        buttonbottom1.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckActivity.this, Product_sub.class);
                startActivity(intent);
            }
        });

    }
}

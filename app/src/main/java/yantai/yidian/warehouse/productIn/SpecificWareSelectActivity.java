package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.SpecificWareAdapter;
import yantai.yidian.warehouse.bean.SpecificWareBean;

import yantai.yidian.warehouse.scatterIn.ScatterInActivity;
import yantai.yidian.warehouse.util.WareApi;

public class SpecificWareSelectActivity extends AppCompatActivity implements WareApi{


    private ListView listView;
    private SpecificWareAdapter specificWareAdapter;
    private ButtonView btn_sure;

    private List<SpecificWareBean> list;
    private SpecificWareBean specificWareBean;
    private String wareName = "";
    private String tag;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_specific_ware_select);


        initView();

        specificWareAdapter = new SpecificWareAdapter(list,this);
        listView.setAdapter(specificWareAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioButton mRB=(RadioButton)view.findViewById(R.id.item_rb_ware_check);
                specificWareAdapter.clearStates(position);
                mRB.setChecked(specificWareAdapter.getStates(position));
                specificWareBean = list.get(position);
                wareName = specificWareBean.getWare_name();
                specificWareAdapter.notifyDataSetChanged();
                Log.d("specificWareBean:",wareName);
            }
        });

        btn_sure.setButtonListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           if (wareName ==""){
                                               Toast.makeText(SpecificWareSelectActivity.this,"请先选择具体仓库",Toast.LENGTH_SHORT).show();
                                           }else {
                                               SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                                               SharedPreferences.Editor editor = spf.edit();
                                               editor.putString("wareName",wareName);
                                               editor.commit();
                                               startActityByTag();
                                           }

                                       }
                                   }
        );
    }

    public void initView(){
        btn_sure = (ButtonView) findViewById(R.id.btn_specific_ware_sure);
        listView = (ListView) findViewById(R.id.lv_specific_ware);
        Intent intent = this.getIntent();
        list = intent.getParcelableArrayListExtra("list");
        tag = intent.getStringExtra("tag");
    }

    public void startActityByTag(){
        Log.d("tag",tag);
        switch (tag){
            case "生产入库":
                startActivity(new Intent(SpecificWareSelectActivity.this,WareChooseActivity.class));
                break;
            case "采购入库":

                break;
            case "退货入库":

                break;
            case "零散入库":
                SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("first",0+"");
                editor.commit();
                startActivity(new Intent(SpecificWareSelectActivity.this,ScatterInActivity.class));
                break;
            case "生产退货入库":

                break;
            case "空箱入库":

                break;
            case "产线废料入库":

                break;
            case "移库":

                break;
            case "仓库废料入库":

                break;
        }
    }



}

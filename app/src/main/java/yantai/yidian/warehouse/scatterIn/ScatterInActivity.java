package yantai.yidian.warehouse.scatterIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;

import com.mondschein.titleview.TitleView;

import java.util.ArrayList;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.adapter.ScatterAdapter;
import yantai.yidian.warehouse.bean.ScatterBean;

public class ScatterInActivity extends AppCompatActivity {

    private ListView listView;
    private ScatterAdapter scatterAdapter;
    private List<ScatterBean> list;
    ScatterBean scatterBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_in);
        initData();
        initView();
        listView.setAdapter(scatterAdapter);
    }


    protected void initData(){
        scatterBean = new ScatterBean("3","CPLOC001","56","100");
        list = new ArrayList<>();
        list.add(scatterBean);
        scatterAdapter = new ScatterAdapter(list,this);
    }
    protected void initView(){
        listView = (ListView) findViewById(R.id.lv_scatter);
    }

}

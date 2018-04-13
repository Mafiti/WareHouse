package yantai.yidian.warehouse.productIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yantai.yidian.warehouse.R;

public class LocationSelectActivity extends AppCompatActivity {

    private ListView listView;
    private MyAdapter myAdapter;
    private List<LocationInfo> data_list;
    private static int selectPosition=-1;
    private LocationInfo selecedLocationInfo;
    private Map<String,Object> selectedInfo;


    public static int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        data_list = new ArrayList<>();
        data_list.clear();
        //监听下方确定按钮点击事件
        ButtonView buttonView= (ButtonView) findViewById(R.id.buttonbottom);
        buttonView.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(LocationSelectActivity.this,"你选择了第"+(selectPosition+1)+"行："
                        +" "+selecedLocationInfo.getLocation()+" "
                        +selecedLocationInfo.getCapacity()+" "+selecedLocationInfo.getInventory()
                        +" "+selecedLocationInfo.getRemainingSpace(),Toast.LENGTH_SHORT).show();

            }
        });

        listView=(ListView)findViewById(R.id.listview);
        //MyAdapter

        selectPosition = -1;
        initArrayAdapterData();
        myAdapter=new MyAdapter(this,data_list);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition=position;
                myAdapter.notifyDataSetChanged();
                selecedLocationInfo=data_list.get(position);
                Intent intent1 = new Intent();
                intent1.putExtra("location",selecedLocationInfo.getLocation());
                setResult(RESULT_OK,intent1);

            }
        });
    }

    private void initArrayAdapterData(){

        Intent intent = this.getIntent();
        data_list = intent.getParcelableArrayListExtra("locationInfoList");

    }

}

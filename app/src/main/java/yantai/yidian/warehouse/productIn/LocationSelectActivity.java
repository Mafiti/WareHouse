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

//        LocationInfo li0=new LocationInfo("B103","100","85","15");
//        LocationInfo li1=new LocationInfo("B104","100","67","33");
//        LocationInfo li2=new LocationInfo("B105","100","55","45");
//        LocationInfo li3=new LocationInfo("B106","100","43","57");
//        LocationInfo li4=new LocationInfo("A210","100","0","100");
//        LocationInfo li5=new LocationInfo("A211","100","0","100");
//        LocationInfo li6=new LocationInfo("A301","100","0","100");
//        LocationInfo li7=new LocationInfo("A305","100","0","100");
//        data_list.add(li0);
//        data_list.add(li1);
//        data_list.add(li2);
//        data_list.add(li3);
//        data_list.add(li4);
//        data_list.add(li5);
//        data_list.add(li6);
//        data_list.add(li7);
    }

}

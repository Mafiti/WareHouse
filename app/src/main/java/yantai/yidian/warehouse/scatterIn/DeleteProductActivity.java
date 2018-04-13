package yantai.yidian.warehouse.scatterIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mondschein.btnview.ButtonView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.bean.ScatterBean;

public class DeleteProductActivity extends AppCompatActivity {

    private List<ScatterBean> list;
    private ButtonView buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        final int index = Integer.parseInt(getIntent().getStringExtra("indexDel"));
        buttonView = (ButtonView) findViewById(R.id.btn_scatter_delete);
        buttonView.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提取保存的list
                SharedPreferences spf = getSharedPreferences("setting",MODE_PRIVATE);
                String json = spf.getString("scatterList",null);
                if (json!=null){
                    Gson gson = new Gson();
                    Type type  = new TypeToken<List<ScatterBean>>(){}.getType();
                    list = gson.fromJson(json,type);
                }
                Log.d("list",list.toString());
                if (list.size() == 1){
                    list.clear();
                }else {
                    list.remove(list.get(index));
                }
                //保存删除一条数据后的list
                SharedPreferences sp = getSharedPreferences("setting",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Gson gson1 = new Gson();
                String json1 = gson1.toJson(list);
                editor.putString("scatterList",json1);
                editor.commit();
                Toast.makeText(DeleteProductActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteProductActivity.this,ScatterInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

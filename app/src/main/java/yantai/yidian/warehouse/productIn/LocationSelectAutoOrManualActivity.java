package yantai.yidian.warehouse.productIn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mondschein.btnview.ButtonView;

import yantai.yidian.warehouse.R;
import yantai.yidian.warehouse.productIn.feedback.ScanSuccessFeedbackActivity;

public class LocationSelectAutoOrManualActivity extends AppCompatActivity {

    private ButtonView btn_sure;
    private Button btn_people_select;

    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select_auto_or_manual);
        initView();

        btn_sure.setButtonListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      startActivity(new Intent(LocationSelectAutoOrManualActivity.this,ScanSuccessFeedbackActivity.class));
                                                  }
                                              }
        );

        btn_people_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btn_people_select)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, LocationSelectActivity.class);
                    startActivity(intent);
                    LocationSelectAutoOrManualActivity.this.finish();
                }
            }
        });

       /* help=(ImageButton) findViewById(R.id.imageView);
        testview1=(EditText) findViewById(R.id.editText);
        rb = (RadioButton) findViewById(R.id.rb1);
        search2 = (ImageButton) findViewById(R.id.search2);


        testview1.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if(touch_flag==2){
                    touch_flag=0;
                    rb.setChecked(false);
                }
                return false;
            }
        });

        final GlobalValue globalValue = new GlobalValue();
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v==rb) {
                    rb.setChecked(true);
                    testview1.clearFocus();
                    closeKeyboard();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==help)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, Help01Activity.class);
                    startActivity(intent);
                    LocationSelectAutoOrManualActivity.this.finish();
                }
            }
        });

        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==search2)
                {
                    Intent intent = new Intent(LocationSelectAutoOrManualActivity.this, LocationSelectActivity.class);
                    startActivity(intent);
                    LocationSelectAutoOrManualActivity.this.finish();
                }
            }
        });
        */

    }

    public void initView(){
        btn_sure = (ButtonView) findViewById(R.id.buttonbottom);
        btn_people_select = (Button) findViewById(R.id.btn_people_select);
    }



    /*private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(reader != null){
                        try{
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private  void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }*/

}

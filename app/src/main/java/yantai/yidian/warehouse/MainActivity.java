package yantai.yidian.warehouse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TabItem> mTableItemList;
    private TextView tv_top_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        initData();
        initTabHost();
    }

    private void initData(){
        mTableItemList = new ArrayList<>();
        mTableItemList.add(new TabItem(R.drawable.workbench_normal,R.drawable.workbench_pressed,R.string.workbench,WorkBenchFragment.class));
        mTableItemList.add(new TabItem(R.drawable.message_normal,R.drawable.message_pressed,R.string.message,MessageFragment.class));
        mTableItemList.add(new TabItem(R.drawable.inqurie_noraml,R.drawable.inqurie_pressed,R.string.inqurie,InquireFragment.class));
        mTableItemList.add(new TabItem(R.drawable.personal_normal,R.drawable.personal_pressed,R.string.personal,PersonalFragment.class));

    }

    private void initTabHost(){
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i=0;i<mTableItemList.size();i++){
            TabItem tabItem = mTableItemList.get(i);
            //实例化一个Tabspec，设置tab的名称和视图
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec,tabItem.getFragmentClass(),null);


            //默认选中第一个
            if (i==0){
                tabItem.setChecked(true);
                tv_top_title.setText("工作台");
            }
            fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    for (int i=0;i<mTableItemList.size();i++){
                        TabItem tabitem = mTableItemList.get(i);
                        if(tabId.equals(tabitem.getTitleString())){
                            tabitem.setChecked(true);
                            tv_top_title.setText(tabitem.getTitleString());
                        }else {
                            tabitem.setChecked(false);
                        }
                    }
                }
            });
        }
    }

    class TabItem{
        //正常情况下显示的图片
        private int imageNormal;
        //选中情况下显示的图片
        private int imagePressed;
        //tab的名字
        private int title;
        private String titleString;
        //tab对应的fragment
        public Class<? extends Fragment> fragmentClass;

        private View view;
        private ImageView imageView;
        private TextView textView;

        public TabItem(int imageNormal,int imagePressed,int title,Class<? extends Fragment> fragmentClass){
            this.imageNormal = imageNormal;
            this.imagePressed = imagePressed;
            this.title = title;
            this.fragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePressed() {
            return imagePressed;
        }

        public int getTitle() {
            return title;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if (TextUtils.isEmpty(titleString)){
                titleString = getString(title);
            }
            return titleString;
        }

        public View getView() {
            if(this.view==null){
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator,null);
                this.imageView = (ImageView) this.view.findViewById(R.id.tab_iv_image);
                this.textView = (TextView) this.view.findViewById(R.id.tab_iv_text);
                if(this.title == 0){
                    this.textView.setVisibility(View.GONE);
                }else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }

        public void setChecked(boolean isChecked){
            if (imageView != null){
                if(isChecked){
                    imageView.setImageResource(imagePressed);
                }else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if (textView != null && title !=0){
                if (isChecked){
                    textView.setTextColor(getResources().getColor(R.color.tab_blue));
                }else {
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }

    }
}




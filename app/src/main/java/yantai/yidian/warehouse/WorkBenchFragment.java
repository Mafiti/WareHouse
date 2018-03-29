package yantai.yidian.warehouse;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WorkBenchFragment extends Fragment implements View.OnClickListener {


    private ViewPager viewPager;

    private LinearLayout inop,outop,insideop;
    private ImageView img_inop,img_outop,img_insideop;
    private TextView txt_inop,txt_outop,txt_insideop;

    private InOpFragment inOpFragment;
    private OutOpFragment outOpFragment;
    private InsideOpFragment insideOpFragment;

    private List<Fragment> fragments;
    private MyAdapter myAdapter;



    public WorkBenchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work_bench, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(0);

        inop.setSelected(true);
        img_inop.setSelected(true);
        txt_inop.setSelected(true);

        inop.setOnClickListener(this);
        outop.setOnClickListener(this);
        insideop.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        inop.setSelected(true);
                        img_inop.setSelected(true);
                        txt_inop.setSelected(true);

                        outop.setSelected(false);
                        img_outop.setSelected(false);
                        txt_outop.setSelected(false);

                        insideop.setSelected(false);
                        img_insideop.setSelected(false);
                        txt_insideop.setSelected(false);

                        break;
                    case 1:
                        inop.setSelected(false);
                        img_inop.setSelected(false);
                        txt_inop.setSelected(false);

                        outop.setSelected(true);
                        img_outop.setSelected(true);
                        txt_outop.setSelected(true);

                        insideop.setSelected(false);
                        img_insideop.setSelected(false);
                        txt_insideop.setSelected(false);
                        break;
                    case 2:
                        inop.setSelected(false);
                        img_inop.setSelected(false);
                        txt_inop.setSelected(false);

                        outop.setSelected(false);
                        img_outop.setSelected(false);
                        txt_outop.setSelected(false);

                        insideop.setSelected(true);
                        img_insideop.setSelected(true);
                        txt_insideop.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initView(){
        viewPager = (ViewPager) getActivity().findViewById(R.id.vp_essence);

        fragments = new ArrayList<>();
        inOpFragment = new InOpFragment();
        outOpFragment = new OutOpFragment();
        insideOpFragment = new InsideOpFragment();

        fragments.add(inOpFragment);
        fragments.add(outOpFragment);
        fragments.add(insideOpFragment);

        inop = (LinearLayout) getActivity().findViewById(R.id.llyt_inop);
        outop = (LinearLayout) getActivity().findViewById(R.id.llyt_outop);
        insideop = (LinearLayout) getActivity().findViewById(R.id.llyt_insideop);

        img_inop = (ImageView) getActivity().findViewById(R.id.img_inop);
        img_outop = (ImageView) getActivity().findViewById(R.id.img_outop);
        img_insideop = (ImageView) getActivity().findViewById(R.id.img_insideop);

        txt_inop = (TextView) getActivity().findViewById(R.id.txt_inop);
        txt_outop = (TextView) getActivity().findViewById(R.id.txt_outop);
        txt_insideop = (TextView) getActivity().findViewById(R.id.txt_insideop);

        myAdapter = new MyAdapter(this.getChildFragmentManager(),fragments);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llyt_inop:
                inop.setSelected(true);
                img_inop.setSelected(true);
                txt_inop.setSelected(true);

                outop.setSelected(false);
                img_outop.setSelected(false);
                txt_outop.setSelected(false);

                insideop.setSelected(false);
                img_insideop.setSelected(false);
                txt_insideop.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.llyt_outop:
                viewPager.setCurrentItem(1);
                inop.setSelected(false);
                img_inop.setSelected(false);
                txt_inop.setSelected(false);

                outop.setSelected(true);
                img_outop.setSelected(true);
                txt_outop.setSelected(true);

                insideop.setSelected(false);
                img_insideop.setSelected(false);
                txt_insideop.setSelected(false);
                break;
            case R.id.llyt_insideop:
                viewPager.setCurrentItem(2);
                inop.setSelected(false);
                img_inop.setSelected(false);
                txt_inop.setSelected(false);

                outop.setSelected(false);
                img_outop.setSelected(false);
                txt_outop.setSelected(false);

                insideop.setSelected(true);
                img_insideop.setSelected(true);
                txt_insideop.setSelected(true);
                break;
        }
    }


    public class MyAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragmentList = new ArrayList<>();

        public MyAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

}

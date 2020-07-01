package com.justefun.ordering;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justefun.ordering.fragment.MenuFragment;
import com.justefun.ordering.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment>mfragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉bar上方的阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

    }
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("菜单");
        mTitle.add("订单");

        mfragment = new ArrayList<>();
        mfragment.add(new MenuFragment());
        mfragment.add(new OrderFragment());
    }
    private void initView() {

        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mfragment.size());

        //mViewPager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mfragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mfragment.size();
            }

            //设置标题

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }



}

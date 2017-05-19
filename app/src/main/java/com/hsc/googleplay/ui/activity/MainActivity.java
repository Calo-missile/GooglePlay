package com.hsc.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hsc.googleplay.R;
import com.hsc.googleplay.ui.fragment.BaseFragment;
import com.hsc.googleplay.ui.fragment.FragmentFactory;
import com.hsc.googleplay.ui.view.PagerTab;
import com.hsc.googleplay.utils.UIUtils;

import static com.hsc.googleplay.ui.fragment.FragmentFactory.creatFragment;

public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mMAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMAdapter);
        
        mPagerTab.setViewPager(mViewPager);//将指针和Viewpager绑定在一起
        
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.creatFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        
    }

    class MyAdapter extends FragmentPagerAdapter {

        private final String[] mTabName;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabName = UIUtils.getStringArray(R.array.tab_names);//页面标题数组
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName[position];
        }
        
        //返回当前页面的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = creatFragment(position);
            return fragment;
        }

        //fragment数量
        @Override
        public int getCount() {
            return mTabName.length;
        }
    }
    
}

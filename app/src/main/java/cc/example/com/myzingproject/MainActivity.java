package cc.example.com.myzingproject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import cc.example.com.myzingproject.fragment.HomeFragment;
import cc.example.com.myzingproject.fragment.MessageFragment;
import cc.example.com.myzingproject.fragment.ScanFragment;
import cc.example.com.myzingproject.fragment.SettingFragment;
import cc.example.com.myzingproject.fragment.ToolFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabLayout.Tab HomeTab;
    private TabLayout.Tab MessageTab;
    private TabLayout.Tab ScanTab;
    private TabLayout.Tab ToolTab;
    private TabLayout.Tab SettingTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvents();
    }

    private void initView() {

        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewPager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"首页", "联系","短信","我的"};

            @Override
            public Fragment getItem(int position) {
                if (position == 1) {
                    return new MessageFragment();
                }  else if (position == 2){
                    return new ToolFragment();
                } else if (position == 3){
                    return new SettingFragment();
                }
                return new HomeFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });

        mTabLayout.setupWithViewPager(mViewPager);

        HomeTab = mTabLayout.getTabAt(0);
        MessageTab = mTabLayout.getTabAt(1);
        ToolTab = mTabLayout.getTabAt(2);
        SettingTab = mTabLayout.getTabAt(3);

        HomeTab.setIcon(getResources().getDrawable(R.drawable.ic_account_balance_indigo_500_24dp));
        MessageTab.setIcon(getResources().getDrawable(R.drawable.ic_contact_mail_black_24dp));
        //ScanTab.setIcon(getResources().getDrawable(R.drawable.ic_local_see_black_24dp));
        ToolTab.setIcon(getResources().getDrawable(R.drawable.ic_local_post_office_black_24dp));
        SettingTab.setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp));
    }

    private void initEvents() {

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    HomeTab.setIcon(getResources().getDrawable(R.drawable.ic_account_balance_indigo_500_24dp));
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTabLayout.getTabAt(1)) {
                    MessageTab.setIcon(getResources().getDrawable(R.drawable.ic_contact_mail_indigo_500_24dp));
                    mViewPager.setCurrentItem(1);
                } else if (tab == mTabLayout.getTabAt(2)){
                    ToolTab.setIcon(getResources().getDrawable(R.drawable.ic_local_post_office_indigo_500_24dp));
                    mViewPager.setCurrentItem(2);
                }else if (tab == mTabLayout.getTabAt(3)){
                    SettingTab.setIcon(getResources().getDrawable(R.drawable.ic_person_indigo_500_24dp));
                    mViewPager.setCurrentItem(3);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTabLayout.getTabAt(0)) {
                    HomeTab.setIcon(getResources().getDrawable(R.drawable.ic_account_balance_black_24dp));
                } else if (tab == mTabLayout.getTabAt(1)) {
                    MessageTab.setIcon(getResources().getDrawable(R.drawable.ic_contact_mail_black_24dp));
                } else if (tab == mTabLayout.getTabAt(2)){
                    ToolTab.setIcon(getResources().getDrawable(R.drawable.ic_local_post_office_black_24dp));
                }else if (tab == mTabLayout.getTabAt(3)){
                    SettingTab.setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

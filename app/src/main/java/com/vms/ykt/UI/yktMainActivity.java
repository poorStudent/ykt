package com.vms.ykt.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.vms.ykt.R;
import com.vms.ykt.UI.Fragment.*;
import com.vms.ykt.viewModel.ViewModelUtils;
import com.vms.ykt.viewModel.userVModel;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;


import java.util.HashMap;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class yktMainActivity extends AppCompatActivity {
    private zjyUser mZjyUser;
    private String appv;
    private String cookie;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public final String[] mTabTitle = new String[]{"职教云", "mooc", "icve", "cqooc", "设置"};
    public final String[] FragmentTag = {"zjy", "mooc", "icve", "cqooc", "set"};
    private final HashMap<Integer, Fragment> mFragmensts = new HashMap<>();
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mTransaction;
    private String ARG_PARAM = "param_key";
    public userVModel mUserVModel;
    Bundle bundle;
    private int goCqooc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserVModel=ViewModelUtils.getPrivateViewModel(getApplication(), userVModel.class,this);
        initData();
        initId();
        initView();
        initListener();

    }

    private void initData() {

        Intent i = getIntent();
        String userInfo = i.getStringExtra("userInfo");
        String appv = i.getStringExtra("appv");
        String ck = i.getStringExtra("ck");
        goCqooc = i.getIntExtra("goCqooc",0);
        if (userInfo==null||userInfo.isEmpty())return;
        this.appv = appv;
        this.cookie = ck;
        this.mZjyUser =  JSONObject.parseObject(userInfo, zjyUser.class);
        mZjyUser.setAppv(appv);
        mZjyUser.setCookie(cookie);
        mUserVModel.SGzjyUser(mZjyUser);

    }

    private void initId() {
        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.vpg);

    }

    private void initView() {

        mSupportFragmentManager = getSupportFragmentManager();
        mTransaction = mSupportFragmentManager.beginTransaction();
        // 提供自定义的布局添加Tab
        for (int i = 0; i < mTabTitle.length; i++) {
            View view = LayoutInflater.from(yktMainActivity.this).inflate(R.layout.tablayout, null);
            TextView tabText = view.findViewById(R.id.tab_content_text);
            tabText.setText(mTabTitle[i]);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(view));
        }


       // mViewPager.setOffscreenPageLimit(1);
        //onTabItemSelected(1);
        /*
         zjyFragment vZjyFragment ;
         zjyFragment.newInstance("zjy",mZjyUser,cookie,appv);
         hideOthersFragment(vZjyFragment, true);
         mFragmensts.put(0, fragment);*/

    }

    private void initListener() {


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                // Tab 选中之后，改变各个Tab的状态

                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    TextView text = view.findViewById(R.id.tab_content_text);
                    if (i == tab.getPosition()) { // 选中状态
                        text.setTextColor(getColor(R.color.teal_200));
                    } else {// 未选中状态
                        text.setTextColor(getColor(android.R.color.darker_gray));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public int getCount() {
                return mTabLayout.getTabCount();
            }

            @Override
            public Fragment getItem(int position) {
                return onTabItemSelected(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitle[position];
            }
        });
        //mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        View view = mTabLayout.getTabAt(goCqooc).getCustomView();
        TextView text = view.findViewById(R.id.tab_content_text);
        text.setTextColor(getColor(R.color.teal_200));
        mTabLayout.getTabAt(goCqooc).select();
        mViewPager.setCurrentItem(goCqooc);

    }

    public void FragmentPushParms(Fragment fragment, String p1) {

        if (bundle == null) {

            bundle = new Bundle();
        }
        bundle.clear();
        bundle.putString(ARG_PARAM, p1);
        fragment.setArguments(bundle);
    }

    private Fragment onTabItemSelected(int position) {
        Fragment fragment = mFragmensts.get(position);
        mZjyUser=mUserVModel.SGzjyUser(mZjyUser);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = zjyFragment.newInstance(FragmentTag[position]);
                    ((zjyFragment) fragment).setData(mZjyUser);
                    FragmentPushParms(fragment, FragmentTag[position]);
                    //设置参数
                   // Tool.toast(yktMainActivity.this, FragmentTag[position]);
                    break;
                case 1:
                    fragment = moocFragment.newInstance(FragmentTag[position]);
                    ((moocFragment) fragment).setData(mZjyUser);
                    FragmentPushParms(fragment, FragmentTag[position]);
                    //设置参数
                  //  Tool.toast(yktMainActivity.this, FragmentTag[position]);
                    break;
                case 2:
                    fragment = icveFragment.newInstance(FragmentTag[position]);
                    FragmentPushParms(fragment, FragmentTag[position]);
                    ((icveFragment) fragment).setData(mZjyUser);
                    //设置参数
                    //Tool.toast(yktMainActivity.this, FragmentTag[position]);
                    break;
                case 3:

                    fragment = cqoocFragment.newInstance(goCqooc);
                    FragmentPushParms(fragment, FragmentTag[position]);
                    //设置参数
                    //Tool.toast(yktMainActivity.this, FragmentTag[position]);
                    break;
                case 4:
                    fragment = setFragment.newInstance(FragmentTag[position]);
                    FragmentPushParms(fragment, FragmentTag[position]);
                    //设置参数
                    //Tool.toast(yktMainActivity.this, FragmentTag[position]);
                    break;
            }
            mFragmensts.put(position, fragment);
        }
        // Tool.toast(yktMainActivity.this, FragmentTag[position]);
        return fragment;

       /* if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mframeLayout,fragment,DataGenerator.FragmentTag[position]).commit();
        }*/
    }

    private void onTabItemSelected2(int position) {

        Fragment fragment = mFragmensts.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = zjyFragment.newInstance(FragmentTag[position]);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
                case 1:
                    fragment = moocFragment.newInstance(FragmentTag[position]);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
                case 2:
                    fragment = icveFragment.newInstance(FragmentTag[position]);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
                case 3:
                    fragment = setFragment.newInstance(FragmentTag[position]);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
            }
            mFragmensts.put(position, fragment);
        }
        hideOthersFragment(fragment, false, FragmentTag[position]);

    }

    private void hideOthersFragment(Fragment showFragment, boolean add, String tag) {

        if (add) {
            //mTransaction.add(R.id.m_frameLayout, showFragment,tag);
        }

        for (Fragment fragment : mFragmensts.values()) {
            if (showFragment.equals(fragment)) {
                mTransaction.show(fragment);
            } else {
                mTransaction.hide(fragment);
            }
        }
        mTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        // Glide.with(getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
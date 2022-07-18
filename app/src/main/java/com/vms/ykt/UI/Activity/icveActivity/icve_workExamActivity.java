package com.vms.ykt.UI.Activity.icveActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.vms.ykt.R;
import com.vms.ykt.UI.Fragment.icve_workExam_Fragment;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;

import java.util.HashMap;


public class icve_workExamActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private TabLayout mTabLayout;
    public static final String[] mTabTitle = new String[]{"作业", "考试"};
    public static final String[] FragmentTag = {"zy", "ks"};
    private HashMap<Integer, Fragment> mFragmensts = new HashMap<>();

    private static String ARG_PARAM = "param_key";
    Bundle bundle;
    private zjyUser mZjyUser;
    private icveCourseInfo mCourseIfno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icve_workexam_activity);

        initData();
        initId();
        initView();
        initListener();
    }

    private void initData() {
        Intent i = getIntent();
        this.mCourseIfno = (icveCourseInfo) i.getSerializableExtra("Course");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("mZjyUser");

    }

    private void initId() {
        mTabLayout = findViewById(R.id.tab);
        mFrameLayout = findViewById(R.id.zjy_frameLayout);

    }

    private void initView() {


        // 提供自定义的布局添加Tab
        for (int i = 0; i < mTabTitle.length; i++) {
            View view = LayoutInflater.from(icve_workExamActivity.this).inflate(R.layout.icve_tablayout, null);
            TextView tabText = view.findViewById(R.id.tab_content_text);
            tabText.setText(mTabTitle[i]);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(view));
        }


        View view = mTabLayout.getTabAt(0).getCustomView();
        TextView text = view.findViewById(R.id.tab_content_text);
        text.setTextColor(getColor(R.color.teal_200));
        mTabLayout.getTabAt(0).select();
        onTabItemSelected(0);
    }

    private void initListener() {


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

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

    }

    public void FragmentPushParms(Fragment fragment, String p1) {

        if (bundle == null) {

            bundle = new Bundle();
        }
        bundle.clear();
        bundle.putString(ARG_PARAM, p1);
        fragment.setArguments(bundle);
    }


    private void onTabItemSelected(int position) {

        Fragment fragment = mFragmensts.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new icve_workExam_Fragment(1);
                    ((icve_workExam_Fragment) fragment).setData(mZjyUser, mCourseIfno);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
                case 1:
                    fragment = new icve_workExam_Fragment(2);
                    ((icve_workExam_Fragment) fragment).setData(mZjyUser, mCourseIfno);
                    hideOthersFragment(fragment, true, FragmentTag[position]);
                    break;
            }
            mFragmensts.put(position, fragment);
        }
        hideOthersFragment(fragment, false, FragmentTag[position]);

    }

    private void hideOthersFragment(Fragment showFragment, boolean add, String tag) {

        FragmentManager mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mSupportFragmentManager.beginTransaction();
        if (add) {
            mTransaction.add(R.id.zjy_frameLayout, showFragment, tag);
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


}
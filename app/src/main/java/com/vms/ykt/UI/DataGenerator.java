package com.vms.ykt.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vms.ykt.R;


public class DataGenerator {

    public static final int[] mTabRes = new int[]{R.drawable.ic_launcher_background};
    public static final int[] mTabResPressed = new int[]{R.drawable.ic_launcher_background};
    public static final String[] mTabTitle = new String[]{"职教云", "mooc", "icve"};
    public static final String[] FragmentTag = {"zjy", "mooc", "icve"};


    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.tablayout, null);
        //ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        //tabIcon.setImageResource(DataGenerator.mTabRes[0]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }

}

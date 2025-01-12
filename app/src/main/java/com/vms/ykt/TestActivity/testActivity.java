package com.vms.ykt.TestActivity;


import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.ViewUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.vms.ykt.R;
import com.vms.ykt.Util.DateTimeFormatUtil;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.viewModel.ViewModelUtils;
import com.vms.ykt.viewModel.userVModel;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuWeb.Cqooc.cqApi;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocCourseInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocHttp;

import com.vms.ykt.yktStuWeb.Cqooc.cqoocMain;
import com.vms.ykt.yktStuWeb.Cqooc.userInfo;

import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.newZJY.newZjyTestApi;
import com.vms.ykt.yktStuWeb.newZJY.zjyMian;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class testActivity extends AppCompatActivity {

    private Button mButton;
    private TestEditor mTestEditor;
    private TestView mTestView;
    private final String TAG = testActivity.class.getSimpleName();
   /** static {
        System.loadLibrary("native-lib");
    }*/
    private userVModel mUserVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        String ck=Tool.ck(this);
        getPackageName();
        mButton = findViewById(R.id.button);
        mTestEditor = findViewById(R.id.testEditors);
        mTestView = findViewById(R.id.testViews);
        TextView vTextView = findViewById(R.id.text11);

        getLifecycle().addObserver(mTestView);
        mButton.setOnClickListener((view) -> {
            mTestEditor.setEditType(2);
            mTestEditor.refreshDrawableState();

        });


        TestViewModel mTestViewModel = new ViewModelProvider(this).get(TestViewModel.class);

        zjyUser vUsers = new zjyUser();
        vUsers.setUserName("xxxx");
        mTestViewModel.setMutableLiveDataT(vUsers);

        mTestViewModel.getMutableLiveDataT().observe(this, new Observer<zjyUser>() {
            @Override
            public void onChanged(zjyUser o) {
                Log.d(TAG, "onChanged: " + o.getUserName());
            }
        });

        mUserVModel=ViewModelUtils.getPrivateViewModel(getApplication(), userVModel.class, this);
        String vS = String.valueOf(Tool.getRandomInt( 0,999999));
        userInfo vUserInfo= new userInfo();
        vUserInfo.setId(vS);
        mUserVModel.SGcqoocUser(vUserInfo);
        Tool.toast(this,mUserVModel.SGcqoocUser(null).getId());
        initTestPage();
    }


    private void initTestPage() {

        List<Integer> imge = new ArrayList<>();
        imge.add(R.drawable.ic_launcher_foreground);
        imge.add(R.drawable.ic_baseline_link_off_24);
        imge.add(R.drawable.ic_baseline_link_24);
        imge.add(R.drawable.ic_baseline_clear_24);
        List<mdata> vMdata = new ArrayList<>();

        for (Integer vInteger : imge) {
            vMdata.add(new mdata(vInteger + "", vInteger));
        }

        TestPageView testPageView = findViewById(R.id.testPageView);

        testPageView.initPagerAdapter(vMdata.size());
        testPageView.setBindTitleListener((int position) -> vMdata.get(position).getTitle());
        testPageView.setBindImageListener((int position) -> vMdata.get(position).getImag());
        testPageView.setPageItemOnClick(new TestPageView.PageItemOnClick() {

            @Override
            public boolean ItemOnLongClick(@NotNull View view, int position) {
                return false;
            }

            @Override
            public void ItemOnClick(@NotNull View view, int position) {
                Tool.toast(testActivity.this,position+"");
            }
        });

    }


    private void getContentCallLog() {
        Uri callUri = CallLog.Calls.CONTENT_URI;
        String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
                , CallLog.Calls.NUMBER// 通话记录的电话号码
                , CallLog.Calls.DATE// 通话记录的日期
                , CallLog.Calls.DURATION// 通话时长
                , CallLog.Calls.TYPE};// 通话类型}
        Cursor cursor = getContentResolver().query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        Log.i(TAG, "cursor count:" + cursor.getCount());
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));  //姓名
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)); //获取通话日期
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));//获取通话时长，值为多少秒
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)); //获取通话类型：1.呼入2.呼出3.未接

            Log.i(TAG, "Call log: " + "\n"
                    + "name: " + name + "\n"
                    + "phone number: " + number + "\n"

            );

        }

    }

    //获取联系人
    private void getConnect() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{"display_name", "sort_key", "contact_id",
                        "data1"}, null, null, null);
        Log.i(TAG, "cursor connect count:" + cursor.getCount());
//        moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i(TAG, "获取的通讯录是： " + name + "\n"
                    + " number : " + number);
        }
        cursor.close();
    }


    public static void main(String[] args) throws Exception {

        //System.out.println(StringUtils.getMd5("123"));
        //System.out.println(zjyTool.getMd5("123"));
        //new b().cqMian();
        new Thread(()->{

          //  zjyMian.doTest();
            newZjyMain.doMain();

        }).start();



    }

    class nn{
        public void main(String[] args) throws Exception {
            System.out.println(byte.class.getName());

            System.out.println(byte.class.getTypeName());
            System.out.println(byte.class.isArray());
            System.out.println(byte.class.getComponentType());
            System.out.println(byte.class.isPrimitive());
            System.out.println("=====================================");
            System.out.println(byte[].class.isPrimitive());
            System.out.println(byte[].class.isArray());
            System.out.println(Tool[][].class.getName());
            System.out.println(byte[][].class.getComponentType());

            System.out.println(byte[].class.getTypeName());
        }
    }

}


class a {
    @zj(v = "222")
    private int b;
    @zj(v = "222")
    private int c;

    @zj(v = "1111")
    protected int type(int p) {
        System.out.println(p);
        return p;
    }

}

class b extends a {

    public void cqMian() {
        new Thread(() -> {

            cqoocHttp.setUserCookie("player=2; xsid=9846369FAFA564C");
            userInfo vUserInfo = cqoocMain.getUsreInfo("9846369FAFA564C");
            String resp = cqApi.getCourseInfo2(vUserInfo.getId());
            if (resp != null && resp.contains("data")) {
                for (cqoocCourseInfo vCourseInfo : cqoocMain.parseCourse(resp, 2)) {
                    if (vCourseInfo.getTitle().contains("网络舆情分析")) {
                        System.out.println(vCourseInfo.getCourseId());
                        System.out.println(vCourseInfo.getClassId());
                    }
                }
            }


        }).start();
    }

    @Override
    protected int type(int p) {

        System.out.println(p);
        return 2;
    }

    public static void fscs() throws Exception {

        Class vClass = Class.forName("com.vms.ykt.TestActivity.a");
        Field vField = vClass.getDeclaredField("b");
        Method vMethod = vClass.getDeclaredMethod("type", int.class);
        vMethod.setAccessible(true);
        vField.setAccessible(true);

        Object object = vClass.getDeclaredConstructor().newInstance();
        //System.out.println(vMethod.getModifiers());
        //System.out.println(vMethod.invoke(vClass.newInstance(), 1));
        //System.out.println(vField.isAnnotationPresent(zj.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (Annotation vAnnotation : vMethod.getDeclaredAnnotationsByType(zj.class)) {
                if (vAnnotation instanceof zj) {

                }
            }
        }
        for (Method vMethod1 : vClass.getDeclaredMethods()) {
            vMethod1.setAccessible(true);
        }
        for (Field v : vClass.getDeclaredFields()) {
            v.setAccessible(true);
            for (Annotation vAnnotation : v.getDeclaredAnnotations()) {
                if (vAnnotation instanceof zj) {
                    String vS = ((zj) vAnnotation).v();
                    v.set(object, Integer.parseInt(vS));
                    System.out.println(v.get(object));
                }
            }

        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            zj vZj = (zj) vField.getDeclaredAnnotation(zj.class);
        }
        //System.out.println(vZj.v());
        //System.out.println(vClass.getClassLoader());
    }


}

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface zj {
    String v() default "0";
}
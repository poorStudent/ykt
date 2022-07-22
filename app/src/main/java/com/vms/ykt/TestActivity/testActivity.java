package com.vms.ykt.TestActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.vms.ykt.Util.DateTimeFormatUtil;
import com.vms.ykt.Util.StringUtils;
import com.vms.ykt.yktUtil.zjyTool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class testActivity extends AppCompatActivity {



    private final String TAG = testActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TestViewModel mTestViewModel = new ViewModelProvider(this).get(TestViewModel.class);

        zjyUser vUsers =new zjyUser();
        vUsers.setUserName("xxxx");
        mTestViewModel.setMutableLiveDataT(vUsers);

        mTestViewModel.getMutableLiveDataT().observe(this, new Observer<zjyUser>() {
            @Override
            public void onChanged(zjyUser o) {
                Log.d(TAG, "onChanged: "+o.getUserName());
            }
        });


        List<MutableLiveData<zjyUser>> vMutableLiveDataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            zjyUser vUser =new zjyUser();
            vUser.setUserName("mk"+i);
            vUser.setType(i+100);
            MutableLiveData<zjyUser> vMutableLiveData=new MutableLiveData<>();
            vMutableLiveData.setValue(vUser);
            vMutableLiveDataList.add(vMutableLiveData);
        }
        mTestViewModel.setLiveDataListTLL(vMutableLiveDataList);

        for (MutableLiveData<zjyUser> vLiveData:mTestViewModel.getLiveDataListTLL()){
            vLiveData.observe(this, new Observer<zjyUser>() {
                @Override
                public void onChanged(zjyUser zjyUser) {
                    Log.d(TAG, "onChanged: "+(zjyUser).getUserName());
                }
            });
        }

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
        Log.i(TAG,"cursor count:" + cursor.getCount());
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));  //姓名
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)); //获取通话日期
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));//获取通话时长，值为多少秒
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)); //获取通话类型：1.呼入2.呼出3.未接

            Log.i(TAG,"Call log: " + "\n"
                    + "name: " + name +"\n"
                    + "phone number: " + number  + "\n"

            );

        }

    }

    //获取联系人
    private void getConnect() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { "display_name", "sort_key", "contact_id",
                        "data1" }, null, null, null);
        Log.i(TAG,"cursor connect count:" + cursor.getCount());
//        moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i(TAG,"获取的通讯录是： " + name + "\n"
                    +  " number : " + number);
        }
        cursor.close();
    }


    public static void main(String[] args) throws Exception {

        System.out.println(StringUtils.getMd5("123"));
        System.out.println(zjyTool.getMd5("123"));
  //   b.getTcpCode();


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
    @Override
    protected int type(int p) {

        System.out.println(p);
        return 2;
    }

    public static void getTcpCode(){
        new Thread(()->{
            System.out.println(DateTimeFormatUtil.getInternetTime());
            String tcpIpAddress = "101.37.228.98";
            int tcpPort = 7788;
            try {
                // 和服务器创建连接
                Socket socket = new Socket(tcpIpAddress,tcpPort);
                // 要发送给服务器的信息
                OutputStream os = socket.getOutputStream();
                os.write("getKey".getBytes());
                os.flush();

                socket.shutdownOutput();

                // 从服务器接收的信息
                byte[] b=new byte[1024];
                InputStream is = socket.getInputStream();
                int a=is.read(b);

                byte[] bArr2 = new byte[a];
                System.arraycopy(b, 0, bArr2, 0, a);
                System.out.println(new String(bArr2, StandardCharsets.UTF_8));

                is.close();
                os.close();
                os.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


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
        for (Method vMethod1 : vClass.getDeclaredMethods()){
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
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface zj{
    String v() default "0";
}
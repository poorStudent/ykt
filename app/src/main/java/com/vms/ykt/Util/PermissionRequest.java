package com.vms.ykt.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vms.ykt.UI.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionRequest {

    private final String TAG =PermissionRequest.class.getSimpleName() ;
    //10000比较霸气
    public final int MY_PERMISSIONS_REQUEST_CODE = 10000;
    // 声明一个集合，用来存储用户拒绝授权的权
    private List<String> unPermissionList = new ArrayList<>(); //申请未得到授权的权限列表

    private AlertDialog mPermissionDialog;

    private String mPackName ;  //获取 a'p'k 包名


    //1、首先声明一个数组permissions，将所有需要申请的权限都放在里面
    String[] permissionList = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS


    };

    public void checkPermission(Context mContext) {

        if (unPermissionList==null)unPermissionList=new ArrayList<>();

        unPermissionList.clear();//清空申请的没有通过的权限

        //逐个判断是否还有未通过的权限

        for (int i = 0; i < permissionList.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissionList[i]) != PackageManager.PERMISSION_GRANTED) {

                unPermissionList.add(permissionList[i]);//添加还未授予的权限到unPermissionList中
            }
        }

        //有权限没有通过，需要申请
        if (unPermissionList.size() > 0) {
            ActivityCompat.requestPermissions( (Activity) mContext,permissionList, MY_PERMISSIONS_REQUEST_CODE);
            Log.i(TAG, "check 有权限未通过");
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Log.i(TAG, "check 权限都已经申请通过");
        }
    }

    public void showPermissionDialog(Context mContext,String[] permissions) {
        Log.i(TAG,"mPackName: " + mPackName);
        Log.d(TAG, "showPermissionDialog: "+ Arrays.toString(permissions));
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(mContext)
                    .setTitle("已禁用权限，请手动授予")
                    .setMessage("禁用可能导致功能无法使用")
                    .setPositiveButton("前往设置", (dialog, which) -> {
                        cancelPermissionDialog();
                        Uri packageURI = Uri.parse("package:" + mPackName);     //去设置里面设置
                        Intent intent = new Intent(Settings.
                                ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        mContext.startActivity(intent);
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        //关闭页面或者做其他操作
                        Tool.toast(mContext, "PermissionDismiss");
                        cancelPermissionDialog();
                    }).setNeutralButton("重新申请", (dialog, which)->{
                        cancelPermissionDialog();
                        checkPermission(mContext);
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

}

package com.vms.ykt.hookTest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.AbstractSequentialList;
import java.util.ArrayList;

public class hookDataRW extends SQLiteOpenHelper {

    private static final String dbName = "hookData";
    private static final int dbVersion =1;
    private static  hookDataRW hookDB;
    private SQLiteDatabase mDatabase;
    private static final String dbTableName = "table_1";

    public hookDataRW(Context context) {

        super(context,dbName,null,dbVersion);
    }

    public hookDataRW(@Nullable Context context,  int version) {
        super(context, dbName, null, version);
    }

    public static hookDataRW getHookDBInstance(Context context,int dbVersion){

        if (hookDB == null) {
            hookDB=new hookDataRW(context);
        }else if ( hookDB== null && dbVersion >0) {
            hookDB=new hookDataRW(context,dbVersion);
        }
        return hookDB;
    }

    public SQLiteDatabase openRead(){

     if (mDatabase == null||!mDatabase.isOpen()) {
         mDatabase=hookDB.getReadableDatabase();
     }
return mDatabase;
 }

    public  SQLiteDatabase openWrite(){
     if (mDatabase == null||!mDatabase.isOpen()) {
         mDatabase=hookDB.getWritableDatabase();
     }
     return mDatabase;
 }

    public void closeDB(){
     if (mDatabase != null&&mDatabase.isOpen()) {
         mDatabase.close();
         mDatabase=null;


     }

 }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="DROP TABLE IF EXISTS "+dbTableName+";";
        db.execSQL(sql);
        sql="CREATE TABLE IF NOT EXISTS "+dbTableName+" ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +"name VARCHAR NOT NULL,"+"tel VARCHAR NOT NULL,"
                +"age INTEGER NOT NULL,"+"wg FLOAT NOT NULL,"
                +"pass VARCHAR"+");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int dbdel(String sql){
        return mDatabase.delete(dbTableName,sql,null);
    }

    public long dbinserts(ArrayList<dbData> infos){
        long ret=0;
        for (dbData vData:infos){
        long rt=dbinsert(vData);
            if (rt != 1) {
                ret++;
            }
        }
        return ret;
    }
    public long dbinsert(dbData info){
        long ret=-1;
        ArrayList<dbData>vDataArrayList ;
        ContentValues vContentValues = new ContentValues();

        if (info!=null&&info.nmae != null&&!TextUtils.isEmpty(info.nmae)) {
            String sql = String.format("tel='%s'", info.tel);
            vDataArrayList=dbquery(sql);
            if (vDataArrayList.size()> 0) {
                ret=dbupdate(info,sql);
            }else {
                vContentValues.put("name", info.nmae);
                vContentValues.put("age", info.age);
                vContentValues.put("wg", info.wg);
                vContentValues.put("tel", info.tel);
                vContentValues.put("pass", info.pass);
                ret=mDatabase.insert(dbTableName,"",vContentValues);
            }
        }

        return ret;
    }

    private long dbupdate(dbData info, String sql) {
        ContentValues vContentValues= new ContentValues();
        vContentValues.put("name", info.nmae);
        vContentValues.put("age", info.age);
        vContentValues.put("wg", info.wg);
        vContentValues.put("tel", info.tel);
        vContentValues.put("pass", info.pass);
        return mDatabase.update(dbTableName, vContentValues, sql, null);
    }

    public ArrayList<dbData> dbquery(String sql) {
        String sqls= String.format("select _id,name,age,wg,tel,pass" +
                "%s where %s",
                dbTableName,sql);
        ArrayList<dbData>vDbData =new ArrayList<dbData>();
        Cursor vCursor = mDatabase.rawQuery(sqls,null);
        while (vCursor.moveToNext()){
            dbData vData = new dbData();
            vData.nmae=vCursor.getString(1);
            vData.age=vCursor.getInt(2);
            vData.wg=vCursor.getFloat(3);
            vData.tel=vCursor.getString(4);
            vData.pass=vCursor.getString(5);
            vDbData.add(vData);
        }
        vCursor.close();
        return vDbData;
    }
}


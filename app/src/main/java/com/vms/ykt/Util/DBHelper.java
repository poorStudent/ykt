package com.vms.ykt.Util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String db_name = "ykt.db";
    public DBHelper(Context context, int version) {
        this(context,db_name,null,version,null);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table yktTable (" +
                " _byte byte," +
                " _long long," +
                " _text text," +
                " _short short," +
                " _int int," +
                " _float float," +
                " _double double," +
                " _boolean boolean," +
                " _blob blob" +
                ")");
        db.execSQL("create table userTable (" +
                " _byte byte," +
                " _long long," +
                " _text text," +
                " _short short," +
                " _int int," +
                " _float float," +
                " _double double," +
                " _boolean boolean," +
                " _blob blob" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


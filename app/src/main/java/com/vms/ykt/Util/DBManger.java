package com.vms.ykt.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;
import android.util.Log;

public class DBManger {
    private Context context;
    private static DBManger instance;
    // 操作表的对象，进行增删改查
    private SQLiteDatabase writableDatabase;

    private DBManger(Context context, int version) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context, version);
        writableDatabase = dbHelper.getWritableDatabase();
    }

    public static DBManger getInstance(Context context, int version) {
        if (instance == null) {
            synchronized (DBManger.class) {
                if (instance == null) {
                    instance = new DBManger(context, version);
                }
            }
        }
        return instance;
    }

    private void delete(String tableName, String whereClause, String[] whereArgs) {
        writableDatabase.delete(tableName, whereClause, whereArgs);

    }

    private void insert(String tableName, String nullColumnHack, ContentValues values) {
        writableDatabase.insert(tableName, nullColumnHack, values);

    }

    private void update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        writableDatabase.update(tableName, values, whereClause, whereArgs);
    }

    private Cursor query(boolean distinct, String table, String[] columns,
                         String selection, String[] selectionArgs, String groupBy,
                         String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
        return writableDatabase.query(distinct, table, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit, cancellationSignal);
    }
}

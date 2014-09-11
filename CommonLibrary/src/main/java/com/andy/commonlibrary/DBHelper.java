package com.andy.commonlibrary;


import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;


/**
 * 数据库操作协助类，使用SQLCipher对数据库加密
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private SQLiteDatabase db;

    private Context context;

    //数据库加密使用的key，测试版本不加密
    private static final String KEY = DebugConfig.DEBUG ? "" : "andydb13572468";

    private static final String DB_FILE_PATH = "zhubofm.db";

    public static DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper(context,DB_FILE_PATH,null,1);
        }

        return instance;
    }

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }


    /**
     * 加载数据库lib资源库，必须在任何数据库操作之前执行此方法
     */
    public void loadLibs(){
        SQLiteDatabase.loadLibs(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public SQLiteDatabase getDatabase(){
        return getWritableDatabase(KEY);
    }

}


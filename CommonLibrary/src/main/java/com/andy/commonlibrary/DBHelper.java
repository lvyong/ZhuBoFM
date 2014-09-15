package com.andy.commonlibrary;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 数据库操作协助类，使用SQLCipher对数据库加密
 */
public class DBHelper {

    private static DBHelper instance;

    private SQLiteDatabase db;
    private Context context;

    private static final String DB_FILE_PATH = "zhubofm.db";
    static final int DATABASE_VERSION = 1;
    private DatabaseHelper databaseHelper;

    /**
     *
     */
    private DBHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    /**
     *
     * @param context
     * @return
     */
    public static DBHelper getInstance(Context context){
        if(instance == null){
            instance = new DBHelper(context);
        }

        return instance;
    }



    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context, DB_FILE_PATH, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }



    public synchronized SQLiteDatabase getDatabase(){
        return databaseHelper.getWritableDatabase();
    }


    public synchronized void close(){
        databaseHelper.close();
    }

}


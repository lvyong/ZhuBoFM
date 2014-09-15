package com.andy.corelibray.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.commonlibrary.DBHelper;
import com.andy.commonlibrary.db.AbstractTable;
import com.andy.corelibray.CoreApplication;


import java.util.List;

/**
 * Created by andy_lv on 2014/8/23.
 */
public class BaseDB {
    protected SQLiteDatabase db;

    private Context context;

    public BaseDB(){
        this.context = CoreApplication.getInstance();
        db = DBHelper.getInstance(context).getDatabase();
    }

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return true: 存在 false: 不存在
     */
    public boolean isTableExist(String tableName){
        boolean isExist = false ;
        if(tableName == null || "".equals(tableName)){
            return isExist;
        }
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {"table", tableName});
        if(null != cursor){
            if (cursor.moveToFirst()){
                int count = cursor.getInt(0);
                isExist = count > 0;
            }
            try{
                cursor.close();
            }finally {
                cursor = null ;
            }
        }
        return isExist;
    }

    /**
     * 创建table
     * @param c
     * @param tableName
     */
    protected void createTable(AbstractTable c,String tableName){
        if(null != db){
            List<String> list = c.getCreateTable(c.getClass(),tableName);
            for (String statment : list) {
                db.execSQL(statment);
            }
        }
    }
}

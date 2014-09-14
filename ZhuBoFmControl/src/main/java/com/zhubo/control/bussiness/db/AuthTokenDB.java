package com.zhubo.control.bussiness.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.andy.commonlibrary.db.AbstractTable;
import com.andy.commonlibrary.db.Table;
import com.andy.commonlibrary.db.TableColumn;
import com.andy.corelibray.db.BaseDB;
import com.zhubo.control.bussiness.bean.AuthToken;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class AuthTokenDB extends BaseDB {

    private static AuthTokenDB instance;

    public static AuthTokenDB  getInstance(){
         if(null == instance){
             instance = new AuthTokenDB();
         }
         return instance;
    }

    private AuthTokenDB(){
        super();
        createTable();
    }

    /**
     * 创建表
     */
    private void createTable(){
        AuthTokenTableColumn auTokenTable = new AuthTokenTableColumn();
        createTable(auTokenTable,AuthTokenTableColumn.TABLE_NAME);
    }


    /**
     * 保存数据
     * @param authToken
     */
    public void save(AuthToken authToken){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AuthTokenTableColumn.id,authToken.getId());
        contentValues.put(AuthTokenTableColumn.uuid,authToken.getUuid());
        contentValues.put(AuthTokenTableColumn.anonymous,authToken.getAnonymous());
        contentValues.put(AuthTokenTableColumn.starredAnchorNum,
                authToken.getStarredAnchorNum());
        contentValues.put(AuthTokenTableColumn.starredColumnNum,
                authToken.getStarredColumnNum());
        contentValues.put(AuthTokenTableColumn.attendedActivityNum,
                authToken.getAttendedActivityNum());
        contentValues.put(AuthTokenTableColumn.subscribedColumnNum,
                authToken.getSubscribedColumnNum());
        contentValues.put(AuthTokenTableColumn.unreadMessageNum,
                authToken.getUnreadMessageNum());
        contentValues.put(AuthTokenTableColumn.starredProductNum,
                authToken.getStarredProductNum());
        contentValues.put(AuthTokenTableColumn.listenedColumnNum,
                authToken.getListenedColumnNum());
        contentValues.put(AuthTokenTableColumn.apiToken,
                authToken.getApiToken());

        if(isExistAuth(authToken.getUuid())){
            db.update(AuthTokenTableColumn.TABLE_NAME, contentValues, null,
                    null);
        }else {
            db.insert(AuthTokenTableColumn.TABLE_NAME,null,contentValues);
        }

    }

    /**
     * 得到ApiToken
     * @return
     */
    public String getApiToken(){
       String apiToken = "";
        Cursor cursor = db.query(AuthTokenTableColumn.TABLE_NAME,
                new String[]{AuthTokenTableColumn.apiToken},null
                ,null, null,null,null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                apiToken = cursor.getString(cursor.getColumnIndex(AuthTokenTableColumn.apiToken));
            }
            cursor.close();
            cursor = null;
        }
       return apiToken;
    }


    /**
     * AuthTable 表字段名
     */
    @Table(name = AuthTokenTableColumn.TABLE_NAME)
    private class AuthTokenTableColumn extends AbstractTable{

        public final static String TABLE_NAME = "authtoken";

        @TableColumn(type = TableColumn.Types.TEXT,isNotNull =true)
        public final static String uuid = "uuid";

        @TableColumn(type = TableColumn.Types.TEXT,isNotNull =true)
        public final static String id = "id";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  anonymous = "anonymous";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  starredAnchorNum = "starredAnchorNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  starredColumnNum = "starredColumnNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  attendedActivityNum = "attendedActivityNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String subscribedColumnNum  = "subscribedColumnNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String unreadMessageNum    = "unreadMessageNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String starredProductNum = "starredProductNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String listenedColumnNum = "listenedColumnNum";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String apiToken= "apiToken";
    }

    /**
     * 判断是否存在uuid
     * @param uuid
     * @return
     */
    private boolean isExistAuth(String uuid){
        Cursor cursor = db.query(
                AuthTokenTableColumn.TABLE_NAME,
                new String[]{AuthTokenTableColumn.uuid},
                AuthTokenTableColumn.uuid + " = ?",
                new String[]{uuid}, null, null, null);
        boolean flag = false;
        if (cursor.getCount() > 0){
            flag = true;
        }
        cursor.close();
        return flag;
    }
}

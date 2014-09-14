package com.zhubo.control.bussiness.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.andy.commonlibrary.db.AbstractTable;
import com.andy.commonlibrary.db.Table;
import com.andy.commonlibrary.db.TableColumn;
import com.andy.corelibray.db.BaseDB;
import com.zhubo.control.bussiness.bean.ProductBean;

import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/12.
 */
public class RecentlyChooseProductDB extends BaseDB {
    private static RecentlyChooseProductDB instance;

    private final  int  TOTAL_COUNT = 5;

    public static RecentlyChooseProductDB  getInstance(){
        if(null == instance){
            instance = new RecentlyChooseProductDB();
        }
        return instance;
    }

    private RecentlyChooseProductDB(){
        super();
        createTable();
    }

    /**
     * 创建表
     */
    private void createTable(){
        RecenlyProductColumn recenlyProductColumn = new RecenlyProductColumn();
        createTable(recenlyProductColumn,RecenlyProductColumn.TABLE_NAME);
    }

    /**
     * 保存数据
     * @param productBean
     */
    public void save(ProductBean productBean){
        boolean isExist = isProductExist(productBean.getId());
        if(isExist){
            deleteProduct(productBean.getId());
        }else{
            handledb(1);
        }
        db.insert(RecenlyProductColumn.TABLE_NAME
                    ,null,getContentValues(productBean));
    }

    /**
     * 保存
     * @param productBeanList
     */
    public void save(List<ProductBean> productBeanList){
        if(null == productBeanList){
            return  ;
        }
        db.beginTransaction();
        for(ProductBean productBean : productBeanList) {
            boolean isExist = isProductExist(productBean.getId());
            if(isExist){
              Log.e("save","----isExit="+isExist);
              deleteProduct(productBean.getId());
            }else{
                handledb(productBeanList.size());
            }
            db.insert(RecenlyProductColumn.TABLE_NAME
                        ,null,getContentValues(productBean));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        Cursor cursor1  =db.query(RecenlyProductColumn.TABLE_NAME,
                null, null, null, null, null, null);
        if(null != cursor1){
            Log.e("save","---- count="+cursor1.getCount());
            cursor1.close();
            cursor1 = null ;
        }
    }

    /**
     *
     * @return
     */
    public List<ProductBean> getRecentlyChoooseProduct(){
       List<ProductBean> list = null;
       Cursor cursor = db.query(RecenlyProductColumn.TABLE_NAME,
               null, null, null, null, null, null);
       if(cursor!=null && cursor.getCount() >0){
           list = new ArrayList<ProductBean>();
           for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            ProductBean productBean = new ProductBean();
               productBean.setId(cursor.getString(cursor.getColumnIndex(
                           RecenlyProductColumn.id)));
               productBean.setPrice(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.price)));
               productBean.setImageUrl(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.imageUrl)));
               productBean.setAmount(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.amount)));
               productBean.setDescription(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.description)));
               productBean.setListPrice(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.listPrice)));
               productBean.setName(cursor.getString(cursor.getColumnIndex(
                       RecenlyProductColumn.name)));
               productBean.setQuantity(cursor.getInt(cursor.getColumnIndex(
                       RecenlyProductColumn.quantity)));
               productBean.setRecommendTimes(cursor.getInt(cursor.getColumnIndex(
                       RecenlyProductColumn.recommendTimes)));
               productBean.setStarredCount(cursor.getInt(cursor.getColumnIndex(
                       RecenlyProductColumn.starredCount)));
               productBean.setViewCount(cursor.getInt(cursor.getColumnIndex(
                       RecenlyProductColumn.viewCount)));
               list.add(productBean);
           }
           cursor.close();
           cursor = null ;
       }
       if(null != list){
           Collections.reverse(list);
       }
       return list;
    }

    private void handledb(int insertCount){
     int deleCount = 0;
     Cursor cursor  =db.query(RecenlyProductColumn.TABLE_NAME,
             null, null, null, null, null, null);
     if(null != cursor){
         int count = cursor.getCount();
         Log.e("handledb","-----count="+count);
         if(insertCount + count> TOTAL_COUNT){
             deleCount = insertCount + count - TOTAL_COUNT;
             Log.e("handledb","-----deleCount="+deleCount);
         }
         cursor.close();
         cursor = null;
     }
     if(deleCount !=  0){
         Log.e("handledb","-----deleCountx------------");
        /* db.execSQL("SET ROWCOUNT 0");
         db.execSQL("delete from " + RecenlyProductColumn.TABLE_NAME +
                 " ???");
         db.execSQL("SET ROWCOUNT  0;");*/
        String sql= "delete from " + RecenlyProductColumn.TABLE_NAME+
                " where " + RecenlyProductColumn.id +
                " in (select  " +RecenlyProductColumn.id +
                " from " +
                RecenlyProductColumn.TABLE_NAME +
                " limit 1 offset 0)";
         db.execSQL(sql);
         Cursor cursor1  =db.query(RecenlyProductColumn.TABLE_NAME,
                 null, null, null, null, null, null);
         if(null != cursor1){
             Log.e("handledb","----after count="+cursor1.getCount());
             cursor1.close();
             cursor1 = null ;
         }
     }
    }




    /**
     *
     * @param productBean
     * @return
     */
    private ContentValues getContentValues(ProductBean productBean){
      ContentValues values = new ContentValues();
      values.put(RecenlyProductColumn.id,productBean.getId());
      values.put(RecenlyProductColumn.price,productBean.getPrice());
      values.put(RecenlyProductColumn.imageUrl,productBean.getImageUrl());
      values.put(RecenlyProductColumn.listPrice,productBean.getListPrice());
      values.put(RecenlyProductColumn.description,productBean.getDescription());
      values.put(RecenlyProductColumn.name,productBean.getName());
      values.put(RecenlyProductColumn.starredCount,productBean.getStarredCount());
      values.put(RecenlyProductColumn.viewCount,productBean.getViewCount());
      values.put(RecenlyProductColumn.quantity,productBean.getQuantity());
      values.put(RecenlyProductColumn.recommendTimes,productBean.getRecommendTimes());
      values.put(RecenlyProductColumn.amount,productBean.getAmount());
      return values;
    }


    /**
     *
     * 判断某个产品是否已经存在 已选产品列表中
     * @param id
     * @return
     */
    private boolean isProductExist(String id){
     boolean isExist = false;
      Cursor cursor = db.query(RecenlyProductColumn.TABLE_NAME,
              new String[]{RecenlyProductColumn.id},RecenlyProductColumn.id + "= ?",
              new String[]{id},null,null,null);
      if(null != cursor){
          isExist = cursor.getCount() > 0;
          cursor.close();
          cursor = null;
      }
     return isExist;
    }

    /**
     * 删除某一个产品
     * @param id
     */
    private void deleteProduct(String id){
        db.delete(RecenlyProductColumn.TABLE_NAME,RecenlyProductColumn.id+"= ? ",new String[]{id});
    }



    /**
     * AuthTable 表字段名
     */
    @Table(name = RecenlyProductColumn.TABLE_NAME)
    private class RecenlyProductColumn extends AbstractTable {

        public final static String TABLE_NAME = "recentlyProduct";

        @TableColumn(type = TableColumn.Types.TEXT,isNotNull =true)
        public final static String id = "id";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  price = "price";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  imageUrl = "imageUrl";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  listPrice = "listPrice";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  description = "description";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String name  = "name";

        @TableColumn(type = TableColumn.Types.INTEGER)
        public final static String starredCount    = "starredCount";

        @TableColumn(type = TableColumn.Types.INTEGER)
        public final static String viewCount = "viewCount";

        @TableColumn(type = TableColumn.Types.INTEGER)
        public final static String quantity = "quantity";

        @TableColumn(type = TableColumn.Types.INTEGER)
        public final static String recommendTimes= "recommendTimes";

        @TableColumn(type = TableColumn.Types.TEXT)
        public final static String  amount = "amount";
    }
}

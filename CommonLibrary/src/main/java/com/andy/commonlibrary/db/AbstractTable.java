package com.andy.commonlibrary.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class AbstractTable {
    @TableColumn(type = TableColumn.Types.INTEGER, isPrimary = true)
    public final static String _ID = "_id";


    /**
     * 得到创建表信息
     * @param c
     * @param tableName
     * @return
     */
    public List<String>  getCreateTable(Class<? extends AbstractTable> c,String tableName){
        List<String> createStatments = new ArrayList<String>();
        List<String> indexStatments = new ArrayList<String>();
        final StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append("if not exists ");
        builder.append(tableName);
        builder.append(" (");
        for (final Field f : c.getFields()) {
            f.setAccessible(true);
            final String fieldValue = getFieldValue(f);
            if (fieldValue != null) {
                final TableColumn tableColumnAnnotation = f.getAnnotation(TableColumn.class);
                if (tableColumnAnnotation != null) {
                    builder.append(fieldValue);
                    builder.append(" ");
                    if (tableColumnAnnotation.type() == TableColumn.Types.INTEGER) {
                        builder.append(" INTEGER");
                    } else if (tableColumnAnnotation.type() == TableColumn.Types.BLOB) {
                        builder.append(" BLOB");
                    } else if (tableColumnAnnotation.type() == TableColumn.Types.TEXT){
                        builder.append(" TEXT");
                    } else if (tableColumnAnnotation.type() == TableColumn.Types.DATETIME){
                        builder.append(" DATETIME");
                    }else if(tableColumnAnnotation.type() == TableColumn.Types.REAL){
                        builder.append(" REAL");
                    }
                    if (tableColumnAnnotation.isPrimary()) {
                        builder.append(" PRIMARY KEY");
                    } else {
                        if (tableColumnAnnotation.isNotNull()) {
                            builder.append(" NOT NULL");
                        }
                        if (tableColumnAnnotation.isUnique()) {
                            builder.append(" UNIQUE");
                        }
                    }
                    if (tableColumnAnnotation.isIndex()) {
                        indexStatments.add("CREATE INDEX idx_" + fieldValue + "_" + tableName + " ON "
                                + tableName + "(" + fieldValue + ");");
                    }
                    builder.append(", ");
                }
            }
        }
        builder.setLength(builder.length() - 2); // remove last ','
        builder.append(");");

        createStatments.add(builder.toString());
        createStatments.addAll(indexStatments);

        return createStatments;
    }


    /**
     * Get value from given field.
     *
     * @param f
     *            field object. Field must be static.
     * @return field's value or null if value can't be read.
     */
    private static final String getFieldValue(Field f) {
        try {
            return f.get(null).toString();
        } catch (final Exception e) {
            return null;
        }
    }

}

package com.andy.commonlibrary.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andy_lv on 2014/9/6.
 */
   @Retention(RetentionPolicy.RUNTIME)
   public @interface TableColumn {
        public enum Types {
            INTEGER, TEXT, BLOB, DATETIME,REAL
        }

        Types type();

        boolean isPrimary() default false;

        boolean isIndex() default false;

        /**
         * A NOT NULL constraint may only be attached to a column
         * definition, not specified as a table constraint. Not
         * surprisingly, a NOT NULL constraint dictates that the associated
         * column may not contain a NULL value. Attempting to set the column
         * value to NULL when inserting a new row or updating an existing
         * one causes a constraint violation.
         */

        boolean isNotNull() default false;

        /**
         * A UNIQUE constraint is similar to a PRIMARY KEY constraint,
         * except that a single table may have any number of UNIQUE
         * constraints. For each UNIQUE constraint on the table, each row,
         * isPrimary=true must feature a unique combination of values in the
         * columns identified by the UNIQUE constraint. As with PRIMARY KEY
         * constraints, for the purposes of UNIQUE constraints NULL values
         * are considered distinct from all other values (including other
         * NULLs). If an INSERT or UPDATE statement attempts to modify the
         * table content so that two or more rows feature identical values
         * in a set of columns that are subject to a UNIQUE constraint, it
         * is a constraint violation.
         */
        boolean isUnique() default false;
    }

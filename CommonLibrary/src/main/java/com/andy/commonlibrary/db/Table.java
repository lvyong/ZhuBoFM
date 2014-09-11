package com.andy.commonlibrary.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andy_lv on 2014/9/6.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String name();
}
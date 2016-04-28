package com.example.util;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by zyz on 2016/4/14 0014.
 * QQ:344100167
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "AppDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}

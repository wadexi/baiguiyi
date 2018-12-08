package com.example.a073105.baiguiyi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a073105.baiguiyi.bean.AuthorInfo;
import com.example.a073105.baiguiyi.bean.Book;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "BookStore.db";
    public static final int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AuthorInfo.class);
            TableUtils.createTable(connectionSource, Book.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        System.out.println("MyDatabaseHelper.onUpgrade oldVersion=" + oldVersion + "  newVersion=" + newVersion);
        try {

            switch (oldVersion) {
                case 1:
                    getDao(Book.class).executeRaw("alter table Book add column book_type varchar(20)");
                    //在数据库版本1的下一版本，Book表中新添加了 book_type 字段

                case 2:
                    // TableUtils.createTable(connectionSource, MyBean2.class);
                    //在数据库版本2的下一版本，新增加了一张表
                default:
                    break;
            }

            //显然这样处理比较暴力
            //TableUtils.dropTable(connectionSource, Book.class, true);
            //onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static MyDatabaseHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static MyDatabaseHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (MyDatabaseHelper.class) {
                if (instance == null)
                    instance = new MyDatabaseHelper(context);
            }
        }
        return instance;
    }

    private Map<String, Dao> daos = new HashMap<>();

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(clazz);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}

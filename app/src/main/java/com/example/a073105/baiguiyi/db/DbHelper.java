package com.example.a073105.baiguiyi.db;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "mydb";
    private static final int  version = 1;
    private static volatile DbHelper dbHelper;


    //GodFormula
    public static String godFormula = "GodFormula";

    static interface GodFormula{
        String id = "id";//id
        String name = "name";//
        String imgPath = "imgPath";//
        String attackNum = "attackNum";//gongjili
        String lifeNum = "lifeNum";//sheng
        String defenseNum = "defenseNum";//fang
        String crit = "crit";//bao
        String critHurt = "critHurt";//bao shang
        String hit = "hit";//mingzhong
        String resistance = "resistance";//dikang
    }
    private String godFormulaSql = "CREATE TABLE if not exists [GodFormula] (\n" +
            "  [id] NUMBER NOT NULL ON CONFLICT FAIL, \n" +
            "  [name] TEXT NOT NULL ON CONFLICT FAIL, \n" +
            "  [imgPath] TEXT NOT NULL ON CONFLICT FAIL, \n" +
            "  [attackNum] NUMBER NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [lifeNum] NUMBER NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [defenseNum] NUMBER NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [crit] NUMERIC NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [critHurt] NUMERIC NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [hit] NUMERIC NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  [resistance] NUMERIC NOT NULL ON CONFLICT FAIL DEFAULT 0, \n" +
            "  CONSTRAINT [] PRIMARY KEY ([id]) ON CONFLICT FAIL);";




    public static DbHelper getInstance(@NonNull Context context,int version){
        if(dbHelper == null){
            synchronized (DbHelper.class){
                if(dbHelper == null){
                    dbHelper = new DbHelper(context,DBNAME,null,version);
                }
            }
        }
        return dbHelper;
    }

    private DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(godFormulaSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table GodFormula");
        db.execSQL(godFormulaSql);
//        dbHelper.querySql("",)
    }

    /**
     * 查询
     * @param sql 查询sql
     * @param resultClass 返回实体class
     * @param <Result> 返回实体
     * @return
     */
    public <Result> List<Result> querySql(String sql,Class<Result> resultClass){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        List<Result> results = new ArrayList<>();
        try {
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()){
                Constructor<Result> instance = resultClass.getConstructor();
                instance.setAccessible(true);
                Result result = instance.newInstance();

                Field[] declaredFields = result.getClass().getDeclaredFields();
                for(int i = 0; i < declaredFields.length;i++){
                    Field field = declaredFields[i];
                    if(field.isAnnotationPresent(FieldIgnore.class)){
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(result, getFieldValue(cursor,field));
                }
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if(cursor != null){
                cursor.close();
            }
            db.close();
        }


        return results;
    }

    /**
     * 设置成员变量的值
     */
    private Object getFieldValue(Cursor cursor, Field field) throws InstantiationException, IllegalAccessException {
        Class<?> fieldType = field.getType();
        String fieldName = null;
        ColumnName columnName = field.getAnnotation(ColumnName.class);
        if(columnName != null){
            fieldName  = columnName.value();
        }
        if(TextUtils.isEmpty(fieldName)){
            fieldName = field.getName();
        }

        Object returnValue = null;
        if(fieldType.isPrimitive()){
            //java.lang.Byte#TYPE
            //java.lang.Void#TYPE
            if(fieldType == Integer.TYPE){
                returnValue =  cursor.getInt(cursor.getColumnIndex(fieldName));
            }else if(fieldType == Boolean.TYPE){
                returnValue =  cursor.getString(cursor.getColumnIndex(fieldName));
            }else if(fieldType == Character.TYPE){
                returnValue =  cursor.getString(cursor.getColumnIndex(fieldName));
            }else  if(fieldType == Short.TYPE){
                returnValue =  cursor.getShort(cursor.getColumnIndex(fieldName));
            }else if(fieldType == Long.TYPE){
                returnValue =  cursor.getLong(cursor.getColumnIndex(fieldName));
            }else if(fieldType == Float.TYPE){
                returnValue =  cursor.getFloat(cursor.getColumnIndex(fieldName));
            }else if(fieldType == Double.TYPE){
                returnValue =  cursor.getDouble(cursor.getColumnIndex(fieldName));
            }
        }else if(fieldType == String.class){
            returnValue =  cursor.getString(cursor.getColumnIndex(fieldName));
        }
        if(fieldType.isMemberClass()){
            Log.e("db", fieldName + "is member class" );
        }

        Log.e("db","field name ：" + fieldName + "   value:" + returnValue);

        return returnValue;
    }

    /**
     * 增删改操作
     * @param sql
     * @return
     */
    public  boolean execSql(String sql){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }


    /**
     * 增删改事务操作
     * @param sqls
     * @return
     */
    public  boolean execSql(List<String> sqls){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (String sql:sqls){
                db.execSQL(sql);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return true;
    }

}

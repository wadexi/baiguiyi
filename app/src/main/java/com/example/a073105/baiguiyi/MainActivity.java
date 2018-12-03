package com.example.a073105.baiguiyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a073105.baiguiyi.bean.MyBean;
import com.example.a073105.baiguiyi.db.DbHelper;
import com.example.a073105.baiguiyi.db.MyDatabaseHelper;
import com.example.a073105.baiguiyi.entity.GodFormula;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DbHelper dbHelper = DbHelper.getInstance(this.getApplicationContext(),4);
        String sql = "insert into GodFormula(id,name,imgPath,attackNum,lifeNum,defenseNum,crit,critHurt,hit,resistance) values(6,\"茨木童子\",\"图片\",3350,4000,400,1,2.7,1,0.2)";
        boolean flag = dbHelper.execSql(sql);
        Log.e("db","insert successful:" + flag);

        String querySql = "select * from GodFormula";
        List<GodFormula> godFormulas = dbHelper.querySql(querySql,GodFormula.class);
        Log.e("db",godFormulas.toString());


    }
}

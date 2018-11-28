package com.example.a073105.baiguiyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a073105.baiguiyi.db.DbHelper;
import com.example.a073105.baiguiyi.entity.GodFormula;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = DbHelper.getInstance(this.getApplicationContext(),4);
        String sql = "insert into GodFormula(id,name,imgPath,attackNum,lifeNum,defenseNum,crit,critHurt,hit,resistance) values(3,\"茨木童子\",\"1.jpg\",3350,4000,400,1,2.7,1,0.2)";
        boolean flag = dbHelper.execSql(sql);
        Log.e("db","insert successful:" + flag);

        String querySql = "select * from GodFormula";
        List<GodFormula> godFormulas = dbHelper.querySql(querySql,GodFormula.class);
        Log.e("db",godFormulas.toString());

    }
}

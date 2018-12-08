package com.example.a073105.baiguiyi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a073105.baiguiyi.bean.Book;
import com.example.a073105.baiguiyi.dao.MyDataDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        DbHelper dbHelper = DbHelper.getInstance(this.getApplicationContext(),4);
//        String sql = "insert into GodFormula(id,name,imgPath,attackNum,lifeNum,defenseNum,crit,critHurt,hit,resistance) values(6,\"茨木童子\",\"图片\",3350,4000,400,1,2.7,1,0.2)";
//        boolean flag = dbHelper.execSql(sql);
//        Log.e("db","insert successful:" + flag);
//
//        String querySql = "select * from GodFormula";
//        List<GodFormula> godFormulas = dbHelper.querySql(querySql,GodFormula.class);
//        Log.e("db",godFormulas.toString());

        Book bean = new Book();
//        @DatabaseField(generatedId = true)
//        private int id;
//
//        @DatabaseField(columnName = "name")
//        public String name;
//
//        @DatabaseField(columnName = "author")
//        public String author;
//
//        @DatabaseField(columnName = "price")
//        public String price;
//
//        @DatabaseField(columnName = "pages")
//        public int pages;
        bean.name = "name1";
        bean.author = "author1";
        bean.price = "10";
        bean.pages = 1;
        MyDataDao.getInstance(this).insert(bean);
        MyDataDao.getInstance(this).queryAll();
        MyDataDao.getInstance(this).queryAuthor("name1","10");
    }
}

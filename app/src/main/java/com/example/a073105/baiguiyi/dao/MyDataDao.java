package com.example.a073105.baiguiyi.dao;

import android.content.Context;

import com.example.a073105.baiguiyi.bean.Book;
import com.example.a073105.baiguiyi.db.MyDatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDataDao implements MyDataImpl{

    private MyDatabaseHelper mHelper;
    private Dao<Book, Integer> dao;
    private Context mContext;
    private static MyDataDao instance;

    protected MyDataDao(Context context) {
        this.mContext = context;
        try {
            mHelper = MyDatabaseHelper.getHelper(mContext);
            dao = mHelper.getDao(Book.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MyDataDao getInstance(Context context) {
        if (instance == null) {
            synchronized (MyDataDao.class) {
                if (instance == null) {
                    instance = new MyDataDao(context);
                }
            }

        }
        return instance;
    }

    @Override
    public void insert(Book book) {

        try {

            //事务操作
           /* TransactionManager.callInTransaction(mHelper.getConnectionSource(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    return null;
                }
            });*/

            dao.create(book);
            //dao.createOrUpdate(book);//和上一行的方法效果一样
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(ArrayList<Book> beanArrayList) {
        try {
            dao.create(beanArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String name, String price) {
        ArrayList<Book> list = null;
        try {
            list = (ArrayList<Book>) dao.queryForEq("name", name);
            if (list != null) {
                for (Book bean : list) {
                    bean.setPrice(price);
                    dao.update(bean);
                    //dao.createOrUpdate(bean);//和上一行的方法效果一样
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update2(String columnName, String columnValue) {
        try {
            //下面这两个代码的意思一样
            dao.updateBuilder().updateColumnValue(columnName, columnValue).update();
            //dao.updateRaw("update Book set " + columnName + "=?", new String[]{columnValue});
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update3(String queryColumnName, String queryColumnValue, String setColumnName, String setColumnValue) {
        try {
            String sql = "update Book set " + setColumnName + "= '" + setColumnValue + "' where " + queryColumnName + "= '" + queryColumnValue + "'";
            System.out.println("MyDataDao.update3 sql=" + sql);
            dao.updateRaw(sql);

            //dao.updateRaw("update Book set price= '33333元' where name= '西游记'");//等价于上面的写法
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        ArrayList<Book> list = null;
        try {
            list = (ArrayList<Book>) dao.queryForEq("name", name);
            if (list != null) {
                for (Book bean : list) {
                    dao.delete(bean);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return -1:删除数据异常  0：无数据
     */
    @Override
    public int deleteAll() {
        int number = -1;
        try {
            number = dao.deleteBuilder().delete();//返回删除的数据条数  例如：删除1条数据，返回1，依次类推。

            //dao.deleteBuilder().where().eq("name", "记").reset();//????
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }

    @Override
    public ArrayList<String> queryPrice(String name) {
        List<Book> list = null;
        ArrayList<String> strings = null;
        try {
            list = dao.queryForEq("name", name);
            if (list != null) {
                strings = new ArrayList<>();
                for (Book book : list) {
                    strings.add(book.getPrice());
                }
                /*for (int i = 0; i < list.size(); i++) {
                    strings.add(list.get(i).getPrice());
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }

    @Override
    public String queryAuthor(String name1, String price1) {
        List<Book> list = null;
        String author = "";

        try {
            list = dao.queryBuilder().where().eq("book_name", name1).and().eq("price", price1).query();//上述相当与：select * from Book where name = name1 and price = price1 ;
            if (list != null) {
                for (Book book : list) {
                    author = book.getAuthor();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;//说明：如果这个 author 是唯一的，可以这样的返回。如果是多个的话，要返回一个ArrayList<String> 类型
    }

    /**
     * @return 表中数据的个数
     */
    @Override
    public long queryCount() {
        long number = 0;
        try {
            number = dao.queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * @param id 这个id 就是表中，每次插入数据，自己递增的id 字段
     */
    @Override
    public ArrayList<Book> queryId(int id) {
        ArrayList<Book> list = null;

        try {
            Book book = dao.queryForId(id);
            if (book != null) {
                list = new ArrayList<>();
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Book> queryAll() {
        ArrayList<Book> list = null;
        try {
            list = (ArrayList<Book>) dao.queryForAll();

            if (list != null) {
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delteTables(Context context, String DBname) {
        //?????
        return false;
    }

    /**
     * 这个方法可以的
     */
    public boolean delteDatabases(Context context, String DBname) {
        return context.deleteDatabase(DBname);
    }

}

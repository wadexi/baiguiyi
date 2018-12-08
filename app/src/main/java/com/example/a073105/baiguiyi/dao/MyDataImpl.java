package com.example.a073105.baiguiyi.dao;

import com.example.a073105.baiguiyi.bean.Book;

import java.util.ArrayList;

public interface MyDataImpl {

    void insert(ArrayList<Book> beanArrayList);

    void insert(Book book);

    void update(String name, String price);

    void update2(String columnName, String columnValue);

    void update3(String queryColumnName, String queryColumnValue, String setColumnName, String setColumnValue);

    void delete(String name);

    int deleteAll();

    ArrayList<String> queryPrice(String name);

    String queryAuthor(String name, String price);

    long queryCount();

    ArrayList<Book> queryId(int id);

    ArrayList<Book> queryAll();

}

package com.example.a073105.baiguiyi.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 注解
 * @author Jerry
 *tableName="tb_user"表示对应的数据库表叫：tb_user表
 */
@DatabaseTable(tableName="tb_user")
public class User {

    //generatedId=true：表示是主键
    @DatabaseField(generatedId=true)
    private int id;
    //columnName="name"：表示对应数据库表tb_user表里面的name字段
    @DatabaseField(columnName="name")
    private String name;
    //columnName="name"：表示对应数据库表tb_user表里面的age字段
    @DatabaseField(columnName="age")
    private int age;
    //columnName="name"：表示对应数据库表tb_user表里面的sex字段
    @DatabaseField(columnName="sex")
    private String sex;

    public User()
    {

    }
    public User( String name, int age, String sex) {
        //super();
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public User(int id, String name, int age, String sex) {
        //super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {

        return "ID:"+id+"\tNAME:"+name+"\tAGE:"+age+"\tSEX:"+sex;
    }
}

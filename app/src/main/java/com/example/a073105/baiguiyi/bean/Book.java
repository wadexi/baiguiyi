package com.example.a073105.baiguiyi.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Book")
public class Book {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "book_name")
    public String name;

    @DatabaseField(columnName = "author")
    public String author;

    @DatabaseField(columnName = "price")
    public String price;

    @DatabaseField(columnName = "pages")
    public int pages;

    @DatabaseField(foreign = true,foreignColumnName = "name")
    private AuthorInfo authorInfo;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

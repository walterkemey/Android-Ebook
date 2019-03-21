package com.example.admin.androidebook.Item;

import java.io.Serializable;

/**
 * Created by admin on 01-01-2018.
 */

public class AuthorList implements Serializable {

    private String author_id, author_name, author_image;

    public AuthorList(String author_id, String author_name, String author_image) {
        this.author_id = author_id;
        this.author_name = author_name;
        this.author_image = author_image;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }
}

package com.example.admin.androidebook.Item;

import java.io.Serializable;

/**
 * Created by admin on 19-03-2018.
 */

public class CommentList implements Serializable {

    private String book_id,user_name,comment_text,dt_rate;

    public CommentList(String book_id, String user_name, String comment_text, String dt_rate) {
        this.book_id = book_id;
        this.user_name = user_name;
        this.comment_text = comment_text;
        this.dt_rate = dt_rate;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getDt_rate() {
        return dt_rate;
    }

    public void setDt_rate(String dt_rate) {
        this.dt_rate = dt_rate;
    }
}

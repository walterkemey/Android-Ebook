package com.example.admin.androidebook.Item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 19-03-2018.
 */

public class ScdList implements Serializable {

    private String id, book_title, book_description, book_cover_img,book_bg_img, book_file_type, book_file_url, total_rate, rate_avg, book_views, author_name;
    private List<ScdList> scdLists;
    private List<CommentList> commentLists;

    public ScdList() {
    }

    public ScdList(String id, String book_title, String book_description, String book_cover_img, String book_file_type, String total_rate, String rate_avg, String book_views, String author_name) {
        this.id = id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_cover_img = book_cover_img;
        this.book_file_type = book_file_type;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_name = author_name;
    }

    public ScdList(String id, String book_title, String book_description, String book_cover_img, String book_bg_img, String book_file_type, String book_file_url, String total_rate, String rate_avg, String book_views, String author_name) {
        this.id = id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_cover_img = book_cover_img;
        this.book_bg_img = book_bg_img;
        this.book_file_type = book_file_type;
        this.book_file_url = book_file_url;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_name = author_name;
    }

    public ScdList(String id, String book_title, String book_description, String book_cover_img, String book_bg_img, String book_file_type, String book_file_url, String total_rate, String rate_avg, String book_views, String author_name, List<ScdList> scdLists, List<CommentList> commentLists) {
        this.id = id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_cover_img = book_cover_img;
        this.book_bg_img = book_bg_img;
        this.book_file_type = book_file_type;
        this.book_file_url = book_file_url;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_name = author_name;
        this.scdLists = scdLists;
        this.commentLists = commentLists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getBook_cover_img() {
        return book_cover_img;
    }

    public void setBook_cover_img(String book_cover_img) {
        this.book_cover_img = book_cover_img;
    }

    public String getBook_bg_img() {
        return book_bg_img;
    }

    public void setBook_bg_img(String book_bg_img) {
        this.book_bg_img = book_bg_img;
    }

    public String getBook_file_type() {
        return book_file_type;
    }

    public void setBook_file_type(String book_file_type) {
        this.book_file_type = book_file_type;
    }

    public String getBook_file_url() {
        return book_file_url;
    }

    public void setBook_file_url(String book_file_url) {
        this.book_file_url = book_file_url;
    }

    public String getTotal_rate() {
        return total_rate;
    }

    public void setTotal_rate(String total_rate) {
        this.total_rate = total_rate;
    }

    public String getRate_avg() {
        return rate_avg;
    }

    public void setRate_avg(String rate_avg) {
        this.rate_avg = rate_avg;
    }

    public String getBook_views() {
        return book_views;
    }

    public void setBook_views(String book_views) {
        this.book_views = book_views;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public List<ScdList> getScdLists() {
        return scdLists;
    }

    public void setScdLists(List<ScdList> scdLists) {
        this.scdLists = scdLists;
    }

    public List<CommentList> getCommentLists() {
        return commentLists;
    }

    public void setCommentLists(List<CommentList> commentLists) {
        this.commentLists = commentLists;
    }

}

package com.example.admin.androidebook.Item;

import java.io.Serializable;

/**
 * Created by admin on 19-03-2018.
 */

public class SubCategoryList implements Serializable {

    private String id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, category_name;

    //--------------- most popular book latest fragment --------------//

    public SubCategoryList(String id, String cat_id, String book_title, String book_description, String book_cover_img, String book_bg_img, String book_file_type, String total_rate, String rate_avg, String book_views, String author_id, String author_name) {
        this.id = id;
        this.cat_id = cat_id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_cover_img = book_cover_img;
        this.book_bg_img = book_bg_img;
        this.book_file_type = book_file_type;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_id = author_id;
        this.author_name = author_name;
    }

    //--------------- most popular book latest fragment --------------//

    //--------------- home fragment featured_books and latest_books -------------//

    public SubCategoryList(String id, String cat_id, String book_title, String book_cover_img, String book_bg_img, String book_file_type, String total_rate, String rate_avg, String book_views, String author_id, String author_name) {
        this.id = id;
        this.cat_id = cat_id;
        this.book_title = book_title;
        this.book_cover_img = book_cover_img;
        this.book_bg_img = book_bg_img;
        this.book_file_type = book_file_type;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_id = author_id;
        this.author_name = author_name;
    }

    //--------------- home fragment featured_books and latest_books --------------//

    //--------------- sub category --------------//

    public SubCategoryList(String id, String book_title, String book_description, String book_cover_img, String book_file_type, String total_rate, String rate_avg, String book_views, String author_name) {
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

    //--------------- sub category --------------//


    public SubCategoryList(String id, String cat_id, String book_title, String book_description, String book_cover_img, String book_bg_img, String book_file_type, String total_rate, String rate_avg, String book_views, String author_id, String author_name, String category_name) {
        this.id = id;
        this.cat_id = cat_id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_cover_img = book_cover_img;
        this.book_bg_img = book_bg_img;
        this.book_file_type = book_file_type;
        this.total_rate = total_rate;
        this.rate_avg = rate_avg;
        this.book_views = book_views;
        this.author_id = author_id;
        this.author_name = author_name;
        this.category_name = category_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getBook_file_type() {
        return book_file_type;
    }

    public void setBook_file_type(String book_file_type) {
        this.book_file_type = book_file_type;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBook_bg_img() {
        return book_bg_img;
    }

    public void setBook_bg_img(String book_bg_img) {
        this.book_bg_img = book_bg_img;
    }
}

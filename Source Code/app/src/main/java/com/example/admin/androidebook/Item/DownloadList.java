package com.example.admin.androidebook.Item;

import java.io.Serializable;

/**
 * Created by abc on 22-Mar-18.
 */

public class DownloadList implements Serializable {

    private String id,title,image,author,url;

    public DownloadList() {
    }

    public DownloadList(String id, String title, String image, String author, String url) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.example.admin.androidebook.Item;

import java.io.Serializable;

/**
 * Created by admin on 04-07-2017.
 */

public class AboutUsList implements Serializable {

    private String app_name,app_logo,app_version,app_author,app_contact,app_email,app_website,app_description,app_privacy_policy;

    public AboutUsList(String app_name, String app_logo, String app_version, String app_author, String app_contact, String app_email, String app_website, String app_description, String app_privacy_policy) {
        this.app_name = app_name;
        this.app_logo = app_logo;
        this.app_version = app_version;
        this.app_author = app_author;
        this.app_contact = app_contact;
        this.app_email = app_email;
        this.app_website = app_website;
        this.app_description = app_description;
        this.app_privacy_policy = app_privacy_policy;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_author() {
        return app_author;
    }

    public void setApp_author(String app_author) {
        this.app_author = app_author;
    }

    public String getApp_contact() {
        return app_contact;
    }

    public void setApp_contact(String app_contact) {
        this.app_contact = app_contact;
    }

    public String getApp_email() {
        return app_email;
    }

    public void setApp_email(String app_email) {
        this.app_email = app_email;
    }

    public String getApp_website() {
        return app_website;
    }

    public void setApp_website(String app_website) {
        this.app_website = app_website;
    }

    public String getApp_description() {
        return app_description;
    }

    public void setApp_description(String app_description) {
        this.app_description = app_description;
    }

    public String getApp_privacy_policy() {
        return app_privacy_policy;
    }

    public void setApp_privacy_policy(String app_privacy_policy) {
        this.app_privacy_policy = app_privacy_policy;
    }
}

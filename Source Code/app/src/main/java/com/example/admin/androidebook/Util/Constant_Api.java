package com.example.admin.androidebook.Util;

import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.Item.AboutUsList;
import com.example.admin.androidebook.Item.DownloadList;
import com.example.admin.androidebook.Item.SubCategoryList;
import com.example.admin.androidebook.Item.ScdList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 07-09-2017.
 */

public class Constant_Api {

    //main server api url
    public static String url = "http://www.viaviweb.in/envato/cc/e_books_app/";

    //Image url
    public static String image = url + "images/";

    //App info url
    public static String app_info = url + "api.php?app_details";

    //App login
    public static String login = url + "user_login_api.php?";

    //App register
    public static String register = url + "user_register_api.php?";

    //App forget password
    public static String forgetPassword = url + "user_forgot_pass_api.php?email=";

    //App profile
    public static String profile = url + "user_profile_api.php?id=";

    //App profile update
    public static String profileUpdate = url + "user_profile_update_api.php?user_id=";

    //Category
    public static String category = url + "api.php?cat_list";

    //Home
    public static String home = url + "api.php?home";

    //Latest
    public static String latest = url + "api.php?latest";

    //Author
    public static String author = url + "api.php?author_list";

    //Sub Category
    public static String sub_category = url + "api.php?cat_id=";

    //Author list
    public static String author_list = url + "api.php?author_id=";

    //Book detail
    public static String detail= url + "api.php?book_id=";

    //Rating
    public static String ratingApi = url + "api_rating.php?book_id=";

    //Comment
    public static String commentApi= url + "api_comment.php?book_id=";

    //Comment
    public static String searchApi= url + "api.php?search_text=";

    public static int AD_COUNT = 0;
    public static int AD_COUNT_SHOW = 5;

    public static AboutUsList aboutUsList;
    public static List<SubCategoryList> subCategoryLists =new ArrayList<>();
    public static List<ScdList> scdLists=new ArrayList<>();

    public static DatabaseHandler db;
    public static List<DownloadList> downloadLists=new ArrayList<>();

}

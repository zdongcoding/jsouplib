package com.github.zdongcoding.jsoup.demo.home;


import com.github.zdongcoding.jsoup.annotation.Select;
import com.github.zdongcoding.jsoup.demo.WebBean;

/**
 * Created by zoudong on 2017/3/5.
 */

public class NavBean extends WebBean {
    public static final String NAV_MOVIE = "nav_movie";// 电影
    public static final String NAV_JU = "nav_ju";//  电视剧
    public static final String NAV_DM = "nav_dm";//  动漫
    public static final String NAV_ZY = "nav_zy"; //  综艺
    public static final String NAV_HOT = "nav_hot"; //热门影片
    public static final String NAV_ZHUANTI = "nav_zhuanti"; //电影专题
    public static final String NAV_LAST = "nav_last"; //最新更新
    @Select(select = "li:has(strong)")
    public boolean isclassify;
    @Select(select = "a[href]", text = true)
    public String title;


    @Override
    public String toString() {
        return "NavBean{" +
                "isclassify=" + isclassify +
                ", title='" + title + '\'' +
                '}';
    }
}

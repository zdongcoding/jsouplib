package com.github.zdongcoding.jsoup.demo;


import com.github.zdongcoding.jsoup.annotation.Select;

/**
 * Created by zoudong on 2017/3/5.
 */

public class WebBean {
    @Select(select ="a[href]", attr ="href")
    public String url;
    @Select(select ="a[href]", text=true)
    public String title;

    @Override
    public String toString() {
        return "WebBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

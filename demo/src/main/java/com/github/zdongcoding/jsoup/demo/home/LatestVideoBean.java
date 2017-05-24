package com.github.zdongcoding.jsoup.demo.home;


import com.github.zdongcoding.jsoup.annotation.Select;
import com.github.zdongcoding.jsoup.demo.MediaInfoBean;
import com.github.zdongcoding.jsoup.demo.WebBean;

import java.util.List;

/**
 * Created by zoudong on 2017/3/5.
 */

public class LatestVideoBean extends WebBean {
    public String url;
    public String title;

    @Select(select = "ul li")
    public  List<MediaInfoBean>  mediaInfos;

    @Override
    public String toString() {
        return "LatestVideoBean{" +
                "mediaInfos=" + mediaInfos +
                '}';
    }

}

package com.github.zdongcoding.jsoup.demo.home;

import com.zdg.ajsoup.annotation.Select;
import com.github.zdongcoding.jsoup.demo.MediaInfoBean;

import java.util.List;

/**
 * Created by zoudong on 2017/3/5.
 */
public class HotTopTabBean   {
    public static final String  TOP_MM = "tt1"; //热门手机电影
    public static final String  TOP_TV="tt2";  //热门电视剧
    public static final String  TOP_AM="tt3";  //热门动漫
    public static final String  TOP_VA="tt4";  //热门综艺

    @Select(select = "div#tt1 div:not(#list2)")
    public List<MediaInfoBean> top_mm; //热门手机电影
    @Select(select = "div#tt2 div:not(#list2)")
    public List<MediaInfoBean> top_tv;  //热门电视剧
    @Select(select = "div#tt3 div:not(#list2)")
    public List<MediaInfoBean> top_am;  //热门动漫
    @Select(select = "div#tt4 div:not(#list2)")
    public List<MediaInfoBean> top_va;  //热门综艺


    @Override
    public String toString() {
        return "HotTopTabBean{" +
                "top_mm=" + top_mm +
                ", top_tv=" + top_tv +
                ", top_am=" + top_am +
                ", top_va=" + top_va +
                '}';
    }


}

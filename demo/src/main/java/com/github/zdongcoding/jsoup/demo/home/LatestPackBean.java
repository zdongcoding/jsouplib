package com.github.zdongcoding.jsoup.demo.home;

/**
 * Created by zoudong on 2017/3/5.
 */

public class LatestPackBean  {
    public static final int LATEST_MM = 0; //热门手机电影
    public static final int LATEST_TV = 1;  //热门电视剧
    public static final int LATEST_AM = 2;  //热门动漫
    public static final int LATEST_VA = 3;  //热门综艺

//    @Select(select = "div a,div ul,div div")
    public LatestVideoBean latestMms; //最新电影
//    @Select(select = "div.button_more + a")
    public LatestVideoBean latestTvs; //最新电视剧
//    @Select(select = "div.button_more + a")
    public LatestVideoBean latestVas; //最新综艺
//    @Select(select = "div.button_more + a")
    public LatestVideoBean latestAms;//最新动漫


}

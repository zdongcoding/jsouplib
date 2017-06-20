package com.github.zdongcoding.jsoup.demo.home;



import com.github.zdg.ajsoup.annotation.Select;
import com.github.zdongcoding.jsoup.demo.WebBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zoudong on 2017/3/3.
 */
@Select(select = "body")
public class HomeBean {
//     @Select(select = "div div div.listbox div[id]:not(#list2).lpelmt2.me2li",attr = "id")
//     public Map<String, List<MediaInfoBean>> hotTopVideo;
     @Select(select = "div > div > div.listbox")
     public HotTopTabBean hotTopTabBean; //热门数据
     @Select(select = "div#nav > ul > li[id]", attr = "id")
     public Map<String, NavBean> navBeans;
     @Select(select = "div#nav-under > ul >li:has(a)")  //排除无 <a 标签
     public List<NavBean> navUnderBeans;
     @Select(select = "div#body > div.left.noborder.clearfix.block1 > ul")
     public List<LatestVideoBean> latests;
     @Select(select = "div#header-in > div > ul#hot-words > li")
     public List<WebBean> searchbeans;

     @Override
     public String toString() {
          return "HomeBean{" +
                  "hotTopTabBean=" + hotTopTabBean +
                  ", navBeans=" + navBeans +
                  ", navUnderBeans=" + navUnderBeans +
                  ", latests=" + latests +
                  ", searchbeans=" + searchbeans +
                  '}';
     }
}

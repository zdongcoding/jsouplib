# AJsoup
    
[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

> AJsoup 模块是快速将html 转换成bean  类似gson转换 

> 依赖 [jsoup](https://github.com/jhy/jsoup)

> **使用前提你了解jsoup并且了解[jsoup的Select](https://jsoup.org/apidocs/index.html?org/jsoup/select/Selector.html)**
 

使用方法如下：
```
    bean.java

    @Select(select = "body")
    public class HomeBean {
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
    }
   String html=....;

   HomeBean bean=AJsoupReader.deserialize(Jsoup.parse(html), HomeBean.class);

```

# AJsoup---->Converter-Ajsoup   

> 使用过Retrofit  一看这个名字就知道做什么的

使用方法：
```  
    Api.java
     public interface Api {
        @GET("{url}")
        Observable<String> getPage(@Path(value = "url",encoded = true) String url);
     }
    api = new Retrofit.Builder().baseUrl(baseUri)
                .addConverterFactory(JsoupConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(Api.class);
    
    api.getPage("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HomeBean>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(HomeBean homeBean) {
                view.setText(homeBean.toString());
            }
        });

    
```
**不需要服务器 就可以做一个快速做一个客户端**

完毕，就是这么简单

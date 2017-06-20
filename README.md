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

    选择器概要（Selector overview）
    Tagname：通过标签查找元素（例如：a）
    ns|tag：通过标签在命名空间查找元素，例如：fb|name查找<fb:name>元素
    #id：通过ID查找元素，例如#logo
    .class：通过类型名称查找元素，例如.masthead
    [attribute]：带有属性的元素，例如[href]
    [^attr]：带有名称前缀的元素，例如[^data-]查找HTML5带有数据集（dataset）属性的元素
    [attr=value]：带有属性值的元素，例如[width=500]
    [attr^=value]，[attr$=value]，[attr*=value]：包含属性且其值以value开头、结尾或包含value的元素，例如[href*=/path/]
    [attr~=regex]：属性值满足正则表达式的元素，例如img[src~=(?i)\.(png|jpe?g)]
    *：所有元素，例如*
    选择器组合方法
    el#id:：带有ID的元素ID，例如div#logo
    el.class：带类型的元素，例如. div.masthead
    el[attr]：包含属性的元素，例如a[href]
    任意组合：例如a[href].highlight
    ancestor child：继承自某祖（父）元素的子元素，例如.body p查找“body”块下的p元素
    parent > child：直接为父元素后代的子元素，例如: div.content > pf查找p元素，body > * 查找body元素的直系子元素
    siblingA + siblingB：查找由同级元素A前导的同级元素，例如div.head + div
    siblingA ~ siblingX：查找同级元素A前导的同级元素X例如h1 ~ p
    el, el, el：多个选择器组合，查找匹配任一选择器的唯一元素，例如div.masthead, div.logo
    伪选择器（Pseudo selectors）
    :lt(n)：查找索引值（即DOM树中相对于其父元素的位置）小于n的同级元素，例如td:lt(3)
    :gt(n)：查找查找索引值大于n的同级元素，例如div p:gt(2)
    :eq(n) ：查找索引值等于n的同级元素，例如form input:eq(1)
    :has(seletor)：查找匹配选择器包含元素的元素，例如div:has(p)
    :not(selector)：查找不匹配选择器的元素，例如div:not(.logo)
    :contains(text)：查找包含给定文本的元素，大小写铭感，例如p:contains(jsoup)
    :containsOwn(text)：查找直接包含给定文本的元素
    :matches(regex)：查找其文本匹配指定的正则表达式的元素，例如div:matches((?i)login)
    :matchesOwn(regex)：查找其自身文本匹配指定的正则表达式的元素
    注意：上述伪选择器是0-基数的，亦即第一个元素索引值为0，第二个元素index为1等
**不需要服务器 就可以做一个快速做一个客户端**

完毕，就是这么简单

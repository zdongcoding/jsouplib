package com.github.zdongcoding.jsoup.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.zdg.ajsoup.AJsoupConverterFactory;
import com.github.zdongcoding.jsoup.demo.home.HomeBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String baseUri="http://www.80s.tw/";
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView view= (TextView) findViewById(R.id.text);
        api = new Retrofit.Builder().baseUrl(baseUri)
                .addConverterFactory(AJsoupConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(Api.class);
//        api.getPage("").flatMap(new Func1<String, Observable<HomeBean>>() {
//            @Override
//            public Observable<HomeBean> call(String s) {
//                Type genericSuperclass = this.getClass().getGenericSuperclass();
//                if (genericSuperclass instanceof ParameterizedType) {
//                    Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
//                    Log.e("zoudong", ": "+type);
//                }
//                return Observable.just(JsoupReader.deserialize(Jsoup.parse(s), HomeBean.class));
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HomeBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(HomeBean homeBean) {
//                view.setText(homeBean.toString());
//            }
//        });

        api.getPag1("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HomeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("zoudong", "onError====" + "e = [" + e + "]");
            }

            @Override
            public void onNext(HomeBean homeBean) {
                view.setText(homeBean.toString());
            }
        });
    }
}

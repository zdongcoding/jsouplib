package com.github.zdongcoding.jsoup.demo;

import com.github.zdongcoding.converter.jsoup.JSOUP;
import com.github.zdongcoding.jsoup.demo.home.HomeBean;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Api {
    @GET("{url}")
    Observable<String> getPage(@Path(value = "url",encoded = true) String url);

    @GET("{url}")
    Observable<JSOUP<HomeBean>> getPag1(@Path(value = "url",encoded = true) String url);
}

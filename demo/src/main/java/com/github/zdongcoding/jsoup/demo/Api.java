package com.github.zdongcoding.jsoup.demo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Api {
    @GET("{url}")
    Observable<String> getPage(@Path(value = "url",encoded = true) String url);
}

package com.github.zdongcoding.converter.jsoup;


import android.util.Log;

import com.github.zdongcoding.jsoup.JsoupReader;

import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsoupResponseBody2Converter<T> implements Converter<ResponseBody, T> {

    private  T mT;

    public JsoupResponseBody2Converter(T t) {
        this.mT = t;
        Log.e("zoudong", "JsoupResponseBody2Converter====" + "t = [" + t + "]");
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return JsoupReader.deserialize(Jsoup.parse(value.string()), (Class<T>) mT);
        }finally {
            value.close();
        }
    }
}

package com.github.zdongcoding.converter.jsoup;


import com.github.zdongcoding.jsoup.JsoupReader;


import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsoupResponseBodyConverter<T> implements Converter<ResponseBody, JSOUP<T>> {

    private  T mT;

    public JsoupResponseBodyConverter(T t) {
        this.mT = t;
    }

    @Override
    public JSOUP<T> convert(ResponseBody value) throws IOException {
        try {
            return new JSOUP<T>(JsoupReader.deserialize(Jsoup.parse(value.string()), (Class<T>) mT));
        }finally {
            value.close();
        }
    }
}

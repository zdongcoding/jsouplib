package com.github.zdongcoding.converter.jsoup;

import com.github.zdongcoding.jsoup.JsoupReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class DocumentResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private  Class<T> clazz;

    public DocumentResponseBodyConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return JsoupReader.deserialize(Jsoup.parse(value.string()), clazz);
        }finally {
            value.close();
        }
    }
}

package com.github.zdongcoding.converter.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class DocumentResponseBodyConverter implements Converter<ResponseBody, Document> {

    static final DocumentResponseBodyConverter INSTANCE = new DocumentResponseBodyConverter();

    @Override
    public Document convert(ResponseBody value) throws IOException {

        return   Jsoup.parse(value.string());
    }
}

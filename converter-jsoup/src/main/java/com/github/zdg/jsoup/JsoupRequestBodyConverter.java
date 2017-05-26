package com.github.zdg.jsoup;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class JsoupRequestBodyConverter<T> implements Converter<T, RequestBody> {
    static final JsoupRequestBodyConverter<Object> INSTANCE = new JsoupRequestBodyConverter<>();
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

    private JsoupRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, String.valueOf(value));
    }
}

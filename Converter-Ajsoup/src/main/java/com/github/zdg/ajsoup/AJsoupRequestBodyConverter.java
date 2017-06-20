package com.github.zdg.ajsoup;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class AJsoupRequestBodyConverter<T> implements Converter<T, RequestBody> {
    static final AJsoupRequestBodyConverter<Object> INSTANCE = new AJsoupRequestBodyConverter<>();
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

    private AJsoupRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, String.valueOf(value));
    }
}

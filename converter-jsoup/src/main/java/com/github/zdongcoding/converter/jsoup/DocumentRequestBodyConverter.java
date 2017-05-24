package com.github.zdongcoding.converter.jsoup;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class DocumentRequestBodyConverter<T> implements Converter<T, RequestBody> {
    static final DocumentRequestBodyConverter<Object> INSTANCE = new DocumentRequestBodyConverter<>();
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

    private DocumentRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, String.valueOf(value));
    }
}

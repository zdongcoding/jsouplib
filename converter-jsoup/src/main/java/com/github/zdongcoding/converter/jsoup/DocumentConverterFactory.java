package com.github.zdongcoding.converter.jsoup;

import org.jsoup.nodes.Document;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zoudong on 2017/3/3.
 */

public class DocumentConverterFactory  extends Converter.Factory{


    private  DocumentConverterFactory(){
    }

    public static DocumentConverterFactory create() {
        return new DocumentConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type==Document.class) {
            return  DocumentResponseBodyConverter.INSTANCE;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type==Document.class) {
            return  DocumentRequestBodyConverter.INSTANCE;
        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

}

package com.github.zdongcoding.converter.jsoup;


import com.github.zdongcoding.jsoup.kit.ReflectKit;


import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zoudong on 2017/3/3.
 */

public class JsoupConverterFactory extends Converter.Factory{


    private JsoupConverterFactory(){
    }

    public static JsoupConverterFactory create() {
        return new JsoupConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type instanceof ParameterizedType) {
            if (((ParameterizedType) type).getRawType()==JSOUP.class) {
                Type type1 = ReflectKit.chooseImpl(type);
                if (type1 instanceof ParameterizedType) {
                    Type type2 = ((ParameterizedType) type1).getActualTypeArguments()[0];
                    return new JsoupResponseBodyConverter((Class)type2);
                }

            }
        }
        return  null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

}

package com.zdg.converter.ajsoup;


import com.zdg.ajsoup.AJsoupReader;
import com.zdg.ajsoup.annotation.Select;
import com.zdg.ajsoup.kit.ReflectKit;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class AJsoupResponseBodyConverter {

    public static Converter<ResponseBody, ?> create(Type type) {
        if (type instanceof ParameterizedType) {
            if (((ParameterizedType) type).getRawType() == AJSOUP.class) {
                Type type1 = ReflectKit.chooseImpl(type);
                if (type1 instanceof ParameterizedType) {
                    Type type2 = ((ParameterizedType) type1).getActualTypeArguments()[0];
                    return new JSOUPResponseBodyConverter<>((Class) type2);
                }

            }
        }
        if (type instanceof Class) {
            Class aClass = (Class) type;
            if (aClass == Document.class) {
                return new AJsoupResponseBodyConverter.JsoupTResponseBodyConverter<>((Class) type);
            }
            if (aClass.getAnnotations() != null) {
                for (Annotation annotation : aClass.getAnnotations()) {
                    if (annotation instanceof Select) {
                        return new JsoupTResponseBodyConverter<>((Class) type);
                    }
                }
            }
        }
        return null;
    }

    static final class JSOUPResponseBodyConverter<T> implements Converter<ResponseBody, AJSOUP<T>> {

        private T mT;

        public JSOUPResponseBodyConverter(T t) {
            this.mT = t;
        }

        @Override
        public AJSOUP<T> convert(ResponseBody value) throws IOException {
            try {
                return new AJSOUP<T>(AJsoupReader.deserialize(Jsoup.parse(value.string()), (Class<T>) mT));
            } finally {
                value.close();
            }
        }

    }

    static final class JsoupTResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private T mT;

        public JsoupTResponseBodyConverter(T t) {
            this.mT = t;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Document parse = Jsoup.parse(value.string());
            try {
                if (mT == Document.class) {
                    return (T) parse;
                }
                return AJsoupReader.deserialize(parse, (Class<T>) mT);
            } finally {
                value.close();
            }
        }
    }
}

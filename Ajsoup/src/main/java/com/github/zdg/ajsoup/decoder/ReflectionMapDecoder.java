package com.github.zdg.ajsoup.decoder;


import android.text.TextUtils;


import com.github.zdg.ajsoup.AJsoupReaderContext;
import com.github.zdg.ajsoup.data.TypeLiteral;
import com.github.zdg.ajsoup.exception.AJsoupReaderException;
import com.github.zdg.ajsoup.kit.AnalysisDecoder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

class ReflectionMapDecoder implements Decoder {

    private final Constructor ctor;
    private final Decoder valueTypeDecoder;

    public ReflectionMapDecoder(Class clazz, Type[] typeArgs) {
        try {
            ctor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new AJsoupReaderException(e);
        }
        TypeLiteral valueTypeLiteral = TypeLiteral.create(typeArgs[1]);
        valueTypeDecoder = AnalysisDecoder.getDecoder(valueTypeLiteral.getDecoderCacheKey(), typeArgs[1]);
    }

    @Override
    public Object decode(AJsoupReaderContext context) {
            return decode_(context);
    }

    private Object decode_(AJsoupReaderContext context)  {
        Map map = (Map) createMap();
        if (map==null||context.readNull()) {
            return null;
        }
        String key = context.resource.attr(0);
        if (TextUtils.isEmpty(key)) {
            throw new AJsoupReaderException("请设置 attr 表示map 中的 key");
        }
        for (Element element : context.elements) {
            try {
                if (element.hasAttr(key)) {
                    String attr = element.attr(key);
                    Object decode = valueTypeDecoder.decode(new AJsoupReaderContext(new Elements(element), context.resource));
                    map.put(attr, decode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
     private Object createMap(){
        try {
         return ctor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
         return null;
    }
}

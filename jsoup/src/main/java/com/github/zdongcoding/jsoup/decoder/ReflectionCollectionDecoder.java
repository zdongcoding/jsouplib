package com.github.zdongcoding.jsoup.decoder;


import com.github.zdongcoding.jsoup.JsoupReaderContext;
import com.github.zdongcoding.jsoup.data.TypeLiteral;
import com.github.zdongcoding.jsoup.exception.JsoupReaderException;
import com.github.zdongcoding.jsoup.kit.AnalysisDecoder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Collection;

class ReflectionCollectionDecoder implements Decoder {
    private final Constructor ctor;
    private final Decoder compTypeDecoder;

    public ReflectionCollectionDecoder(Class clazz, Type[] typeArgs) {
        try {
            ctor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new JsoupReaderException(e);
        }
        // List<T>  T 的 解析器
        compTypeDecoder = AnalysisDecoder.getDecoder(TypeLiteral.create(typeArgs[0]).getDecoderCacheKey(), typeArgs[0]);
    }

    @Override
    public Object decode(JsoupReaderContext context)  {
            return decode_(context);
    }

    private Object decode_(JsoupReaderContext context)  {
        Collection  col = null;
        try {
            col = (Collection) this.ctor.newInstance();
            for (Element element : context.elements) {   //  bug  当 List<T>  t 为基本类型
                Object decode = compTypeDecoder.decode( new JsoupReaderContext(new Elements(element),context.resource.annotations));
                col.add(decode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsoupReaderException("list decode failure");
        }

        return col;
    }
}

package com.github.zdongcoding.jsoup;



import com.github.zdongcoding.jsoup.data.ClassDescriptor;
import com.github.zdongcoding.jsoup.data.ClassReader;
import com.github.zdongcoding.jsoup.data.TypeLiteral;
import com.github.zdongcoding.jsoup.kit.AnalysisDecoder;
import com.github.zdongcoding.jsoup.kit.AnnotationAnalysis;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by zoudong on 2017/3/10.
 */

public class JsoupReader {

    public static ThreadLocal<JsoupReader> jsp = new ThreadLocal<JsoupReader>() {
        @Override
        protected JsoupReader initialValue() {
            return new JsoupReader();
        }
    };

    public static final <T> T deserialize(Document document, Class<T> clazz) {
        JsoupReader context = jsp.get();
        ClassDescriptor classDescriptor = ClassReader.getClassDescriptor(clazz, true);

        if (classDescriptor.clazz_anno == null)
            throw new RuntimeException(clazz + " you must used  once Annotation ");
//        Log.e("zoudong", "deserialize: "+classDescriptor.clazz_anno[0].toString() );
        Elements elements = AnnotationAnalysis.analysis(document.children(), classDescriptor.clazz_anno);
//        Log.e("zoudong","---->" +elements.html());
        T val = context.read(clazz, new JsoupReaderContext(elements, classDescriptor.clazz_anno));
        return val;
    }

    @SuppressWarnings("unchecked")
    public final <T> T read(Class<T> clazz, JsoupReaderContext iterator) {
        return (T) AnalysisDecoder.getDecoder(TypeLiteral.create(clazz).getDecoderCacheKey(), clazz).decode(iterator);
    }
}

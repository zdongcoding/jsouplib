package com.github.zdg.ajsoup;


import com.github.zdg.ajsoup.data.ClassDescriptor;
import com.github.zdg.ajsoup.data.ClassReader;
import com.github.zdg.ajsoup.data.TypeLiteral;
import com.github.zdg.ajsoup.kit.AnalysisDecoder;
import com.github.zdg.ajsoup.kit.AnnotationAnalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by zoudong on 2017/3/10.
 */

public class AJsoupReader {

    public static ThreadLocal<AJsoupReader> jsp = new ThreadLocal<AJsoupReader>() {
        @Override
        protected AJsoupReader initialValue() {
            return new AJsoupReader();
        }
    };

    public static final <T> T deserialize(Document document, Class<T> clazz) {
        AJsoupReader context = jsp.get();
        ClassDescriptor classDescriptor = ClassReader.getClassDescriptor(clazz, true);

        if (classDescriptor.clazz_anno == null)
            throw new RuntimeException(clazz + " you must used  once Annotation ");
//        Log.e("zoudong", "deserialize: "+classDescriptor.clazz_anno[0].toString() );
        Elements elements = AnnotationAnalysis.analysis(document.children(), classDescriptor.clazz_anno);
//        Log.e("zoudong","---->" +elements.html());
        T val = context.read(clazz, new AJsoupReaderContext(elements, classDescriptor.clazz_anno));
        return val;
    }
    public static final <T> T deserialize(String document, Class<T> clazz) {
        AJsoupReader context = jsp.get();
        ClassDescriptor classDescriptor = ClassReader.getClassDescriptor(clazz, true);
        Document parse = Jsoup.parse(document);
        if (classDescriptor.clazz_anno == null)
            throw new RuntimeException(clazz + " you must used  once Annotation ");
//        Log.e("zoudong", "deserialize: "+classDescriptor.clazz_anno[0].toString() );
        Elements elements = AnnotationAnalysis.analysis(parse.children(), classDescriptor.clazz_anno);
//        Log.e("zoudong","---->" +elements.html());
        T val = context.read(clazz, new AJsoupReaderContext(elements, classDescriptor.clazz_anno));
        return val;
    }
    @SuppressWarnings("unchecked")
    public final <T> T read(Class<T> clazz, AJsoupReaderContext iterator) {
        return (T) AnalysisDecoder.getDecoder(TypeLiteral.create(clazz).getDecoderCacheKey(), clazz).decode(iterator);
    }
}

package com.zdg.ajsoup.decoder;


import com.zdg.ajsoup.AJsoupReaderContext;
import com.zdg.ajsoup.data.TypeLiteral;
import com.zdg.ajsoup.kit.AnalysisDecoder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Constructor;

class ReflectionArrayDecoder implements Decoder {

    private final Class componentType;
    private final Decoder compTypeDecoder;
    private  Constructor ctor;

    public ReflectionArrayDecoder(Class clazz) {
        componentType = clazz.getComponentType();
        try {
            ctor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        compTypeDecoder = AnalysisDecoder.getDecoder(TypeLiteral.create(componentType).getDecoderCacheKey(), componentType);
    }

    @Override
    public Object decode(AJsoupReaderContext context)  {
        Object ctor[] = new Constructor[context.elements.size()];
        for (int i = 0; i < context.elements.size(); i++) {
            Element element = context.elements.get(i);
            ctor[i]=compTypeDecoder.decode( new AJsoupReaderContext(new Elements(element),context.resource));
        }
        return ctor;
    }


}

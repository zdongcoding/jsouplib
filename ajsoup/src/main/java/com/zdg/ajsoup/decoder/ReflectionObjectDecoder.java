package com.zdg.ajsoup.decoder;


import com.zdg.ajsoup.AJsoupReaderContext;
import com.zdg.ajsoup.data.ClassDescriptor;
import com.zdg.ajsoup.data.Resource;
import com.zdg.ajsoup.exception.AJsoupReaderException;
import com.zdg.ajsoup.kit.AnalysisDecoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;


public class ReflectionObjectDecoder {

    protected ClassDescriptor desc;
    public ReflectionObjectDecoder(Class clazz, Annotation[] annotations) {
        desc= AnalysisDecoder.createDecoder(clazz, annotations);
        if (desc==null) throw  new AJsoupReaderException("AnalysisDecoder to ClassDescriptor failure");
    }

    public Decoder create() {
        if (desc.ctor.parameters.isEmpty()) {
            return new OnlyField();
//            if (desc.wrappers.isEmpty()) {
//
//            } else {
//                return new WithSetter();
//            }
        } else {
            return new WithCtor();
        }
    }

    public class OnlyField implements Decoder {

        public Object decode(AJsoupReaderContext context) {
            return decode_(context);
        }

        private Object decode_(AJsoupReaderContext context){
            Object obj =   createNewObject() ;
            if (obj==null) throw new AJsoupReaderException("construction Exception ");
            for (Resource field : desc.fields) {
                context.deserializeChild(field,obj);
            }
            for (Resource setter : desc.setters) {
                context.deserializeChild(setter,obj);
            }
            return obj;
        }

    }

    public class WithCtor implements Decoder {

        @Override
        public Object decode(AJsoupReaderContext context)  {
                return decode_(context);
        }

        private Object decode_(AJsoupReaderContext context){

            return null;
        }
    }

    public class WithSetter implements Decoder {

        @Override
        public Object decode(AJsoupReaderContext context)  {
            try {
                return decode_(context);
            } catch (Exception e) {
                throw new AJsoupReaderException(e);
            }
        }

        private Object decode_(AJsoupReaderContext context)  {
            return  null;
        }
    }


    private Object createNewObject(Object... args)  {
        try {
            if (desc.ctor != null) {
                if (desc.ctor.staticFactory != null) {
                    return desc.ctor.staticFactory.invoke(null, args);
                } else if (desc.ctor.ctor != null) {
                    return desc.ctor.ctor.newInstance(args);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

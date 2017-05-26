package com.github.zdg.jsoup.decoder;


import com.github.zdg.jsoup.JsoupReaderContext;

public interface Decoder {

    Object decode(JsoupReaderContext context) ;


    abstract class BooleanDecoder implements Decoder {
        @Override
        public Object decode(JsoupReaderContext context)  {
            return Boolean.valueOf(decodeBoolean(context));
        }

        public abstract boolean decodeBoolean(JsoupReaderContext context) ;
    }

    abstract class ShortDecoder implements Decoder {
        @Override
        public Object decode(JsoupReaderContext context)  {
            return Short.valueOf(decodeShort(context));
        }

        public abstract short decodeShort(JsoupReaderContext context) ;
    }

    abstract class IntDecoder implements Decoder {
        @Override
        public Object decode(JsoupReaderContext context)  {
            return Integer.valueOf(decodeInt(context));
        }

        public abstract int decodeInt(JsoupReaderContext context) ;
    }

    abstract class LongDecoder implements Decoder {
        @Override
        public Object decode(JsoupReaderContext context)  {
            return Long.valueOf(decodeLong(context));
        }

        public abstract long decodeLong(JsoupReaderContext context) ;
    }

    abstract class FloatDecoder implements Decoder {
        @Override
        public Object decode(JsoupReaderContext context)  {
            return Float.valueOf(decodeFloat(context));
        }

        public abstract float decodeFloat(JsoupReaderContext context) ;
    }

    abstract class DoubleDecoder implements Decoder {

        @Override
        public Object decode(JsoupReaderContext context)  {
            return Double.valueOf(decodeDouble(context));
        }

        public abstract double decodeDouble(JsoupReaderContext context) ;
    }

}

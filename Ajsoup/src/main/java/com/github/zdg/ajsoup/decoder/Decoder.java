package com.github.zdg.ajsoup.decoder;


import com.github.zdg.ajsoup.AJsoupReaderContext;

public interface Decoder {

    Object decode(AJsoupReaderContext context) ;


    abstract class BooleanDecoder implements Decoder {
        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Boolean.valueOf(decodeBoolean(context));
        }

        public abstract boolean decodeBoolean(AJsoupReaderContext context) ;
    }

    abstract class ShortDecoder implements Decoder {
        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Short.valueOf(decodeShort(context));
        }

        public abstract short decodeShort(AJsoupReaderContext context) ;
    }

    abstract class IntDecoder implements Decoder {
        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Integer.valueOf(decodeInt(context));
        }

        public abstract int decodeInt(AJsoupReaderContext context) ;
    }

    abstract class LongDecoder implements Decoder {
        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Long.valueOf(decodeLong(context));
        }

        public abstract long decodeLong(AJsoupReaderContext context) ;
    }

    abstract class FloatDecoder implements Decoder {
        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Float.valueOf(decodeFloat(context));
        }

        public abstract float decodeFloat(AJsoupReaderContext context) ;
    }

    abstract class DoubleDecoder implements Decoder {

        @Override
        public Object decode(AJsoupReaderContext context)  {
            return Double.valueOf(decodeDouble(context));
        }

        public abstract double decodeDouble(AJsoupReaderContext context) ;
    }

}

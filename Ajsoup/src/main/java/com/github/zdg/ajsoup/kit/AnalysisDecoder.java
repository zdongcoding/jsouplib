package com.github.zdg.ajsoup.kit;


import com.github.zdg.ajsoup.AJsoupReaderContext;
import com.github.zdg.ajsoup.data.ClassDescriptor;
import com.github.zdg.ajsoup.data.ClassReader;
import com.github.zdg.ajsoup.data.Resource;
import com.github.zdg.ajsoup.decoder.Decoder;
import com.github.zdg.ajsoup.decoder.ReflectionDecoderFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zoudong on 2017/3/10.
 */

public class AnalysisDecoder {

    static volatile Map<String, Decoder> decoders = new HashMap<>(); //缓存的 decoder
    static volatile Map<Class, Decoder> registerDecoder = new HashMap<>();
    static volatile Map<Class, ClassDescriptor> cacheClassDescriptor = new HashMap<>();

    public static Decoder getDecoder(String cacheKey, Type type) {
        Decoder decoder = getDecoder(cacheKey);
        if (decoder != null) {
            return decoder;
        }
        return gen(cacheKey, type, null);
    }

    public static Decoder getDecoder(String cacheKey, Type type, Annotation[] annotations) {
        Decoder decoder = getDecoder(cacheKey);
        if (decoder != null) {
            return decoder;
        }
        return gen(cacheKey, type, annotations);
    }

    public static void registerDecoder(Class clazz, Decoder decoder) {
        registerDecoder.put(clazz, decoder);
    }

    public static ClassDescriptor createDecoder(Class clazz) {
        return createDecoder(clazz, null);
    }

    /**
     * @param clazz
     * @param anns  这个class 当着 变量时的注解
     * @return
     */
    public static ClassDescriptor createDecoder(Class clazz,  Annotation[] anns) {
        if (cacheClassDescriptor.get(clazz) != null) {
            return cacheClassDescriptor.get(clazz);
        }
        ClassDescriptor desc = ClassReader.getClassDescriptor(clazz, true);
        try {
            if (anns != null && anns.length > 0) {    //变量注解负责给  class 注解
                System.arraycopy(anns, 0, desc.clazz_anno, 0, anns.length - 1);
            }
            for (Resource resource : desc.ctor.parameters) {
                if (!resource.isHasDecoder()) {
                    resource.setDecoder(AnalysisDecoder.getDecoder(resource));
                }
            }
            for (Resource resource : desc.fields) {
                if (!resource.isHasDecoder()) {
                    resource.setDecoder(AnalysisDecoder.getDecoder(resource));
                }
            }
            for (Resource resource : desc.setters) {
                if (!resource.isHasDecoder()) {
                    resource.setDecoder(AnalysisDecoder.getDecoder(resource));
                }
            }

            if (!desc.ctor.parameters.isEmpty()) {
                //            tempCacheKey = "temp@" + clazz.getCanonicalName();
                //            ctorArgsCacheKey = "ctor@" + clazz.getCanonicalName();
            }
        } finally {
            return desc;
        }

    }

    /**
     * 创建解析器
     *
     * @param cacheKey
     * @param type
     * @param annotations
     * @return
     */
    private static Decoder gen(String cacheKey, Type type, Annotation[] annotations) {
        Decoder decoder = getDecoder(cacheKey);
        if (decoder != null) {
            return decoder;
        }

        type = ReflectKit.chooseImpl(type);

        Type[] typeArgs = new Type[0];
        Class clazz;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            clazz = (Class) pType.getRawType();
            typeArgs = pType.getActualTypeArguments();
        } else {
            clazz = (Class) type;
        }
        decoder = NATIVE_DECODERS.get(clazz);//基本数据类型
        if (decoder != null) {
            return decoder;
        }
        decoder = registerDecoder.get(clazz); //自定义的解析器
        if (decoder == null) {
            decoder = ReflectionDecoderFactory.create(clazz, annotations, typeArgs);  //注解解析器
        }
        cacheDecoder(cacheKey, decoder);
        return decoder;
    }

    public static Decoder getDecoder(Resource resource) {
        return getDecoder(resource.decoderCacheKey(), resource.valueType, resource.annotations);
    }

    private static Decoder getDecoder(String cacheKey) {
        return decoders.get(cacheKey);
    }

    public synchronized static void cacheDecoder(String cacheKey, Decoder decoder) {
        decoders.put(cacheKey, decoder);
    }


    final static Map<Class, Decoder> NATIVE_DECODERS = new HashMap<Class, Decoder>() {{
        put(float.class, new Decoder.FloatDecoder() {
            @Override
            public float decodeFloat(AJsoupReaderContext context) {
                return context.readFloat();
            }
        });
        put(Float.class, new Decoder.FloatDecoder() {
            @Override
            public float decodeFloat(AJsoupReaderContext context) {
                return context.readFloat();
            }
        });
        put(double.class, new Decoder.DoubleDecoder() {
            @Override
            public double decodeDouble(AJsoupReaderContext context) {
                return context.readDouble();
            }
        });
        put(Double.class, new Decoder.DoubleDecoder() {
            @Override
            public double decodeDouble(AJsoupReaderContext context) {
                return context.readDouble();
            }
        });
        put(boolean.class, new Decoder.BooleanDecoder() {
            @Override
            public boolean decodeBoolean(AJsoupReaderContext context) {
                return context.readBoolean();
            }
        });
        put(Boolean.class, new Decoder.BooleanDecoder() {
            @Override
            public boolean decodeBoolean(AJsoupReaderContext context) {
                return context.readBoolean();
            }
        });
        put(byte.class, new Decoder.ShortDecoder() {
            @Override
            public short decodeShort(AJsoupReaderContext context) {
                return context.readShort();
            }
        });
        put(Byte.class, new Decoder.ShortDecoder() {
            @Override
            public short decodeShort(AJsoupReaderContext context) {
                return context.readShort();
            }
        });
        put(short.class, new Decoder.ShortDecoder() {
            @Override
            public short decodeShort(AJsoupReaderContext context) {
                return context.readShort();
            }
        });
        put(Short.class, new Decoder.ShortDecoder() {
            @Override
            public short decodeShort(AJsoupReaderContext context) {
                return context.readShort();
            }
        });
        put(int.class, new Decoder.IntDecoder() {
            @Override
            public int decodeInt(AJsoupReaderContext context) {
                return context.readInt();
            }
        });
        put(Integer.class, new Decoder.IntDecoder() {
            @Override
            public int decodeInt(AJsoupReaderContext context) {
                return context.readInt();
            }
        });
        put(char.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.readInt();
            }
        });
        put(Character.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.readInt();
            }
        });
        put(long.class, new Decoder.LongDecoder() {
            @Override
            public long decodeLong(AJsoupReaderContext context) {
                return context.readLong();
            }
        });
        put(Long.class, new Decoder.LongDecoder() {
            @Override
            public long decodeLong(AJsoupReaderContext context) {
                return context.readLong();
            }
        });
        put(BigDecimal.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.readBigDecimal();
            }
        });
        put(BigInteger.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.readBigInteger();
            }
        });
        put(String.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.readString();
            }
        });
        put(Object.class, new Decoder() {
            @Override
            public Object decode(AJsoupReaderContext context) {
                return context.read();
            }
        });
    }};


}

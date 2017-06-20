package com.github.zdg.ajsoup.decoder;//package com.github.videosource.net.html.annotation;
//
//import com.jsoniter.spi.Decoder;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//class ReflectionEnumDecoder implements Decoder{
//
//    private final Map<Slice, Object> enumMap = new HashMap<Slice, Object>();
//    private Class clazz;
//
//    public ReflectionEnumDecoder(Class clazz) {
//        this.clazz = clazz;
//        for (Object e : clazz.getEnumConstants()) {
//            enumMap.put(Slice.make(e.toString()), e);
//        }
//    }
//    @Override
//    public Object decode(JsonIterator context) throws IOException {
//        if (context.readNull()) {
//            return null;
//        }
//        Slice slice = IterImpl.readSlice(context);
//        Object e = enumMap.get(slice);
//        if (e == null) {
//            throw context.reportError("ReflectionEnumDecoder", slice + " is not valid enum for " + clazz);
//        }
//        return e;
//    }
//}

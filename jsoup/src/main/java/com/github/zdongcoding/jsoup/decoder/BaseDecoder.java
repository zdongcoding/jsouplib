package com.github.zdongcoding.jsoup.decoder;


import com.github.zdongcoding.jsoup.data.ClassDescriptor;
import com.github.zdongcoding.jsoup.kit.AnalysisDecoder;

/**
 * Created by zoudong on 2017/3/12.
 */

public abstract class BaseDecoder implements Decoder {
    protected final ClassDescriptor desc;
    public BaseDecoder(Class clazz) {
        desc = AnalysisDecoder.createDecoder(clazz,null);
    }
}

package com.github.zdg.jsoup.decoder;


import com.github.zdg.jsoup.data.ClassDescriptor;
import com.github.zdg.jsoup.kit.AnalysisDecoder;

/**
 * Created by zoudong on 2017/3/12.
 */

public abstract class BaseDecoder implements Decoder {
    protected final ClassDescriptor desc;
    public BaseDecoder(Class clazz) {
        desc = AnalysisDecoder.createDecoder(clazz,null);
    }
}

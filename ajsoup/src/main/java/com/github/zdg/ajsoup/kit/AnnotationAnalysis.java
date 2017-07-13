package com.github.zdg.ajsoup.kit;


import android.text.TextUtils;


import com.github.zdg.ajsoup.annotation.Select;

import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

/**
 * Created by zoudong on 2017/3/12.
 */

public class AnnotationAnalysis {

    public  static Elements analysis(Elements els,Annotation[] ans){
        for (Annotation an : ans) {
            els = select(els, an);
        }
        return els;
    }

    private static Elements select(Elements els, Annotation an) {
        if (an instanceof Select) {
            try {
                String select = ((Select) an).select();
                if (!TextUtils.isEmpty(select)) {
                    els = els.select(select.trim());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return els;
    }
}

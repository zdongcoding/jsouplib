package com.zdg.ajsoup;


import com.zdg.ajsoup.annotation.Select;
import com.zdg.ajsoup.data.Resource;
import com.zdg.ajsoup.kit.AnnotationAnalysis;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by zoudong on 2017/3/10.
 */

public class AJsoupReaderContext {
    public Elements elements;  //数据源
    public Resource resource;
    public Annotation[] clazzans;   //父级的 注解

    public AJsoupReaderContext(Elements elements, Resource resource) {
        this.elements = elements;
        this.resource = resource;
    }

    public AJsoupReaderContext(Elements elements, Annotation[] clazzans) {
        this.elements = elements;
        this.clazzans = clazzans;
    }

    Annotation[] getClazzans() {
        return resource != null && resource.annotations != null ? resource.annotations : clazzans;
    }

    Select getSelect(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Select) {
                return (Select) annotation;
            }
        }
        return null;
    }

    public Float readFloat() {
        return null;
    }

    public Double readDouble() {
        return null;
    }

    public Boolean readBoolean() {
        if (readNull()) {
            return false;
        }
        Annotation[] clazzans = getClazzans();
//        Elements elements1 = AnnotationAnalysis.analysis(elements, clazzans);
        Select select = getSelect(clazzans);
        if (select.text()) {
            for (Element element : elements) {
                return element != null && element.hasText();
            }
        }
        if (!StringUtil.isBlank(select.attr())) {
            for (Element element : elements) {
                return element != null && element.hasAttr(select.attr());
            }
        }
        for (Element element : elements) {
            return element != null;
        }
        return true;
    }

    public boolean readNull() {
        return elements == null || elements.size() == 0;
    }

    public Short readShort() {
        return new Short("0");
    }

    public Integer readInt() {
        return 0;
    }

    public Long readLong() {
        return 0L;
    }

    public BigDecimal readBigDecimal() {
        return null;
    }

    public BigInteger readBigInteger() {
        return null;
    }

    public String readString() {
        if (readNull()) return null;
        Annotation[] clazzans = getClazzans();
//        Elements elements1 = AnnotationAnalysis.analysis(elements, clazzans);
        Select select = getSelect(clazzans);
        if (select.text()) {
            for (Element element : elements) {
                if (element != null && element.hasText()) {
                    return element.text();
                }
            }
        }
        if (!StringUtil.isBlank(select.attr())) {
            for (Element element : elements) {
                if (element != null && element.hasAttr(select.attr())) {
                    return element.attr(select.attr());
                }
            }
        }
        return null;
    }

    public Object read() {
        return null;
    }

    public void deserializeChild(Resource target, Object parant) {
        int anno = target.annotations != null ? target.annotations.length : 0;
        for (int i = 0; i < anno; i++) {
            if (target.annotations[i] instanceof Select) {
                Elements select = AnnotationAnalysis.analysis(elements, target.annotations);
                Object deserialize = target.deserialize(select);
                setToBinding(parant, target, deserialize);
                break;
            }
        }
    }

    private void setToBinding(Object obj, Resource resource, Object slect) {
        if (obj == null) {
            return;
        }
        try {
            if (resource.field != null) {
                resource.field.set(obj, slect);
            } else if (resource.method != null) {
                resource.method.invoke(obj, slect);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}

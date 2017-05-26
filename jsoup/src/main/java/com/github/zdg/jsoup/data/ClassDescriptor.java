package com.github.zdg.jsoup.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassDescriptor {

    public Class clazz;
    public Annotation[] clazz_anno; // 类的注解
    public Map<String, Type> lookup;
    public ConstructorDescriptor ctor; // 构造函数
    public List<Resource> fields;
    public List<Resource> setters;
    public List<Resource> getters;

    public List<Resource> allBindings() {
        ArrayList<Resource> resources = new ArrayList<Resource>(8);
        resources.addAll(fields);
        if (setters != null) {
            resources.addAll(setters);
        }
        if (getters != null) {
            resources.addAll(getters);
        }
        if (ctor != null) {
            resources.addAll(ctor.parameters);
        }
        return resources;
    }
}

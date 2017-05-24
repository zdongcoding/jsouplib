package com.github.zdongcoding.jsoup.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConstructorDescriptor {
    /**
     * set to null if use constructor
     * otherwise use static method
     */
    public String staticMethodName;
    // option 1: use constructor
    public Constructor ctor;
    // option 2: use static method
    public Method staticFactory;
    // option 3: onAttachView by extension
//    public Extension objectFactory;

    /**
     * the parameters to call constructor or static method
     */
    public List<Resource> parameters = new ArrayList<Resource>();

    @Override
    public String toString() {
        return "ConstructorDescriptor{" +
                "staticMethodName='" + staticMethodName + '\'' +
                ", ctor=" + ctor +
                ", staticFactory=" + staticFactory +
                ", parameters=" + parameters +
                '}';
    }
}
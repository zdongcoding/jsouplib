package com.github.zdg.ajsoup.kit;

import com.github.zdg.ajsoup.data.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zoudong on 2017/5/24.
 */

public class ReflectKit {

    /**
     * 获取所有变量
     * @param clazz
     * @param includingPrivate
     * @return
     */
    public static List<Field> getAllFields(Class clazz, boolean includingPrivate) {
        List<Field> temp = Arrays.asList(clazz.getFields());
        List<Field> allFields = new ArrayList<>();
        for (Field field : temp) {
            if (!isContain(allFields,field)) {
                allFields.add(field);
            }
        }
        if (includingPrivate) {
            allFields = new ArrayList<>();
            Class current = clazz;
            while (current != null) {
                List<Field> fields = Arrays.asList(current.getDeclaredFields());
                for (Field field : fields) {
                    if (!isContain(allFields,field)) {
                        allFields.add(field);
                    }
                }
                current = current.getSuperclass();
            }
        }
        return allFields;
    }
    /**检测Field List中是否已经包含了目标field
     * @param fieldList
     * @param field 带检测field
     * @return
     */
    public static boolean isContain(List<Field> fieldList,Field field){
        for(Field temp:fieldList){
            if(temp.getName().equals(field.getName())){
                return true;
            }
        }
        return false;
    }
    /**
     * 获取 所有的 方法
     * @param clazz
     * @param includingPrivate
     * @return
     */
    public  static List<Method> getAllMethods(Class clazz, boolean includingPrivate) {
        List<Method> allMethods = Arrays.asList(clazz.getMethods());
        if (includingPrivate) {
            allMethods = new ArrayList<Method>();
            Class current = clazz;
            while (current != null) {
                allMethods.addAll(Arrays.asList(current.getDeclaredMethods()));
                current = current.getSuperclass();
            }
        }
        return allMethods;
    }
    public static Type chooseImpl(Type type) {
        Type[] typeArgs = new Type[0];
        Class clazz;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            clazz = (Class) pType.getRawType();
            typeArgs = pType.getActualTypeArguments();
        } else {
            clazz = (Class) type;
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            Type compType = Object.class;
            if (typeArgs.length == 0) {
                // default to List<Object>
            } else if (typeArgs.length == 1) {
                compType = typeArgs[0];
            } else {
                throw new IllegalArgumentException(
                        "can not onBindData to generic collection without argument types, " +
                                "try syntax like TypeLiteral<List<Integer>>{}");
            }
            if (clazz == List.class) {
                clazz = ArrayList.class;
            } else if (clazz == Set.class) {
                clazz = HashSet.class;
            }
            return new ParameterizedTypeImpl(new Type[]{compType}, null, clazz);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            Type keyType = String.class;
            Type valueType = Object.class;
            if (typeArgs.length == 0) {
                // default to Map<String, Object>
            } else if (typeArgs.length == 2) {
                keyType = typeArgs[0];
                valueType = typeArgs[1];
            } else {
                throw new IllegalArgumentException(
                        "can not onBindData to generic collection without argument types, " +
                                "try syntax like TypeLiteral<Map<String, String>>{}");
            }
            if (keyType != String.class) {
                throw new IllegalArgumentException("map key must be String");
            }
            if (clazz == Map.class) {
                clazz =  HashMap.class;
            }
            return new ParameterizedTypeImpl(new Type[]{keyType, valueType}, null, clazz);
        }
        return type;
    }

}

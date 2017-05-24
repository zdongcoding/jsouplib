package com.github.zdongcoding.jsoup.data;

import com.github.zdongcoding.jsoup.exception.JsoupReaderException;
import com.github.zdongcoding.jsoup.kit.ReflectKit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassReader {



    /**
     * @param clazz
     * @param includingPrivate
     * @return
     */
    public static ClassDescriptor getClassDescriptor(Class clazz, boolean includingPrivate) {

        ClassDescriptor desc = new ClassDescriptor();
        desc.clazz_anno = clazz.getAnnotations();
        desc.clazz = clazz;
        desc.ctor = getCtor(clazz);

        Map<String, Type> lookup = collectTypeVariableLookup(clazz);
        desc.lookup = lookup;
        desc.fields = getFields(lookup, clazz, includingPrivate);
        desc.setters = getSetters(lookup, clazz, includingPrivate);
       
        for (Resource field : desc.fields) {  //变量
            if (field.valueType instanceof Class) {
                Class valueClazz = (Class) field.valueType;
                if (valueClazz.isArray()) {
                    field.valueCanReuse = false;
                    continue;
                }
            }
        }
        deduplicate(desc);
        if (includingPrivate) {
            if (desc.ctor.ctor != null) {
                desc.ctor.ctor.setAccessible(true);
            }
            if (desc.ctor.staticFactory != null) {
                desc.ctor.staticFactory.setAccessible(true);
            }
           
        }
        for (Resource resource : desc.allBindings()) {
            if (resource.fromNames == null) {
                resource.fromNames = new String[]{resource.name};
            }
            if (resource.field != null && includingPrivate) {
                resource.field.setAccessible(true);
            }
            if (resource.method != null && includingPrivate) {
                resource.method.setAccessible(true);
            }

        }
        return desc;
    }

    /**
     * field 和 seter geter 合并字段
     * @param desc
     */
    private static void deduplicate(ClassDescriptor desc) {
        HashMap<String, Resource> byName = new HashMap<>();
        for (Resource field : desc.fields) {
            if (!byName.containsKey(field.name)) {  //排除重复  变量
                byName.put(field.name, field);
            }
        }
        for (Resource setter : desc.setters) {
            Resource existing = byName.get(setter.name);
            if (existing == null) {
                byName.put(setter.name, setter);
                continue;
            }
            if (desc.fields.remove(existing)) {
                continue;
            }
        }
        for (Resource param : desc.ctor.parameters) {
            Resource existing = byName.get(param.name);
            if (existing == null) {
                byName.put(param.name, param);
                continue;
            }
            if (desc.fields.remove(existing)) {
                continue;
            }
            if (desc.setters.remove(existing)) {
                continue;
            }
        }
    }

    /**
     * 获取 clazz  无参构造方法
     *
     * @param clazz
     * @return
     */
    private static ConstructorDescriptor getCtor(Class clazz) {
        ConstructorDescriptor cctor = new ConstructorDescriptor();
        try {
            cctor.ctor = clazz.getDeclaredConstructor();
        } catch (Exception e) {
            cctor.ctor = null;
        }
        return cctor;
    }

    private static List<Resource> getFields(Map<String, Type> lookup, Class clazz, boolean includingPrivate) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        for (Field field : ReflectKit.getAllFields(clazz, includingPrivate)) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations ==null||annotations.length==0) {//排除未注解变量
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {  //去掉 static  变量
                continue;
            }
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            if (!includingPrivate && !Modifier.isPublic(field.getType().getModifiers())) {
                continue;
            }
            if (includingPrivate) {
                field.setAccessible(true);
            }
            Resource resource = createBindingFromField(lookup, clazz, field);
            resources.add(resource);
        }
        return resources;
    }

    private static Resource createBindingFromField(Map<String, Type> lookup, Class clazz, Field field) {
        try {
            Resource resource = new Resource(clazz, lookup, field.getGenericType());
            resource.fromNames = new String[]{field.getName()};
            resource.name = field.getName();
            resource.annotations = field.getAnnotations();
            resource.field = field;
            return resource;
        } catch (Exception e) {
            throw new RuntimeException("failed to onAttachView resource for field: " + field, e);
        }
    }


    private static List<Resource> getSetters(Map<String, Type> lookup, Class clazz, boolean includingPrivate) {
        ArrayList<Resource> setters = new ArrayList<Resource>();
        for (Method method : ReflectKit.getAllMethods(clazz, includingPrivate)) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations ==null||annotations.length==0) {//排除未注解变量
                continue;
            }
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            String methodName = method.getName();
            if (methodName.length() < 4) {
                continue;
            }
            if (!methodName.startsWith("set")) {
                continue;
            }
            Type[] paramTypes = method.getGenericParameterTypes();
            if (paramTypes.length != 1) {
                continue;
            }
            if (!includingPrivate && !Modifier.isPublic(method.getParameterTypes()[0].getModifiers())) {
                continue;
            }
            if (includingPrivate) {
                method.setAccessible(true);
            }
            try {
                String fromName = translateSetterName(methodName);
                Resource resource = new Resource(clazz, lookup, paramTypes[0]);
                resource.fromNames = new String[]{fromName};
                resource.name = fromName;
                resource.method = method;
                resource.annotations = method.getAnnotations();
                setters.add(resource);
            } catch (Exception e) {
                throw new JsoupReaderException("failed to onAttachView resource from setter: " + method, e);
            }
        }
        return setters;
    }


    private static String translateSetterName(String methodName) {
        if (!methodName.startsWith("set")) {
            return null;
        }
        String fromName = methodName.substring("set".length());
        char[] fromNameChars = fromName.toCharArray();
        fromNameChars[0] = Character.toLowerCase(fromNameChars[0]);
        fromName = new String(fromNameChars);
        return fromName;
    }

    private static Map<String, Type> collectTypeVariableLookup(Type type) {
        HashMap<String, Type> vars = new HashMap<String, Type>();
        if (null == type) {
            return vars;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type[] actualTypeArguments = pType.getActualTypeArguments();
            Class clazz = (Class) pType.getRawType();
            for (int i = 0; i < clazz.getTypeParameters().length; i++) {
                TypeVariable variable = clazz.getTypeParameters()[i];
                vars.put(variable.getName() + "@" + clazz.getCanonicalName(), actualTypeArguments[i]);
            }
            vars.putAll(collectTypeVariableLookup(clazz.getGenericSuperclass()));
            return vars;
        }
        if (type instanceof Class) {
            Class clazz = (Class) type;
            vars.putAll(collectTypeVariableLookup(clazz.getGenericSuperclass()));
            return vars;
        }
        throw new RuntimeException("unexpected type: " + type);
    }


}

package com.github.zdg.ajsoup.data;


import com.github.zdg.ajsoup.AJsoupReaderContext;
import com.github.zdg.ajsoup.annotation.Select;
import com.github.zdg.ajsoup.decoder.Decoder;
import com.github.zdg.ajsoup.exception.AJsoupReaderException;

import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class Resource {
    // input
    public final Class clazz;
    public Annotation[] annotations;
    public Field field;
    public Method method;
    public boolean valueCanReuse;
    // input/output
    public String name;
    public Type valueType;
    // output
    public String[] fromNames; // for decoder
    public TypeLiteral valueTypeLiteral;

    private Decoder decoder;  //
    private TypeLiteral clazzTypeLiteral;

    public Resource(Class clazz, Map<String, Type> lookup, Type valueType) {
        this.clazz = clazz;
        this.valueType = substituteTypeVariables(lookup, valueType);
        this.clazzTypeLiteral = TypeLiteral.create(clazz);
        this.valueTypeLiteral = TypeLiteral.create(this.valueType);
    }

    private static Type substituteTypeVariables(Map<String, Type> lookup, Type type) {
        if (type instanceof TypeVariable) {
            return translateTypeVariable(lookup, (TypeVariable) type);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type[] args = pType.getActualTypeArguments();
            for (int i = 0; i < args.length; i++) {
                args[i] = substituteTypeVariables(lookup, args[i]);
            }
            return new ParameterizedTypeImpl(args, pType.getOwnerType(), pType.getRawType());
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType gaType = (GenericArrayType) type;
            return new GenericArrayTypeImpl(substituteTypeVariables(lookup, gaType.getGenericComponentType()));
        }
        return type;
    }

    private static Type translateTypeVariable(Map<String, Type> lookup, TypeVariable var) {
        GenericDeclaration declaredBy = var.getGenericDeclaration();
        if (!(declaredBy instanceof Class)) {
            // if the <T> is not defined by class, there is no way to get the actual type
            return Object.class;
        }
        Class clazz = (Class) declaredBy;
        Type actualType = lookup.get(var.getName() + "@" + clazz.getCanonicalName());
        if (actualType == null) {
            // should not happen
            return Object.class;
        }
        if (actualType instanceof TypeVariable) {
            // translate to another variable, try again
            return translateTypeVariable(lookup, (TypeVariable) actualType);
        }
        return actualType;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotationClass.isAssignableFrom(annotation.getClass())) {
                return (T) annotation;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (clazz != null ? !clazz.equals(resource.clazz) : resource.clazz != null) return false;
        return name != null ? name.equals(resource.name) : resource.name == null;
    }

    @Override
    public int hashCode() {
        int result = clazz != null ? clazz.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "clazz=" + clazz +
                ", name='" + name + '\'' +
                ", valueType=" + valueType +
                '}';
    }
    public String decoderCacheKey() {
        return this.name + "@" + this.clazzTypeLiteral.getDecoderCacheKey();
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public Object deserialize(AJsoupReaderContext context){
        if (context==null)  throw new AJsoupReaderException(" not decoder");
        if (decoder != null) {
            try {
                return decoder.decode(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  null;
    }
    public Object deserialize(Elements elements){
      return   deserialize(new AJsoupReaderContext(elements, this));
    }
    public boolean isHasDecoder() {
        return decoder!=null;
    }

    private Select getAnnotationIndex(int index) {
        if (annotations == null || annotations.length <= index) {
            return null;
        }
        Annotation annotation = annotations[index];
        return annotation instanceof Select ? (Select) annotation : null;


    }
    public String attr(int index) {
        Select annotationIndex = getAnnotationIndex(index);
        return annotationIndex!=null ? annotationIndex.attr() : null;
    }
    public String key(int index) {
        Select annotationIndex = getAnnotationIndex(index);
        return annotationIndex!=null ? annotationIndex.key() : null;
    }
    public Boolean text(int index) {
        Select annotationIndex = getAnnotationIndex(index);
        return annotationIndex != null && annotationIndex.text();
    }
}

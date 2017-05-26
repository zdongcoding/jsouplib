package com.github.zdongcoding.jsoup.demo;

import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> strings = new ArrayList<String>(){};
        getType(strings.getClass());
        System.out.println(new Type<>(strings).getType());
//        getMethod((Class<?>) new Type<String>("Sglka").getType());
    }

    private void getMethod(Class<?> typeClass) {
        try {
            Field methods = typeClass.getField("data");
            java.lang.reflect.Type genericType = methods.getGenericType();
            if (genericType instanceof ParameterizedType) {
                System.out.println(((ParameterizedType) genericType).getActualTypeArguments()[0]);
            }
            System.out.println(genericType);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private <T> void getType(Class<T> t) {
        System.out.println(new TypeToken<T>() {
        }.getType());
    }
   class Type<T>{
       public List<String> mStrings;
       public T data;

       public Type(T data) {
           this.data = data;
           System.out.println(data.getClass());
       }
     java.lang.reflect.Type getType(){
           return data.getClass();
       }
   }
}
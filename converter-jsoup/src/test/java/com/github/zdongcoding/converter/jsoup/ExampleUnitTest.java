package com.github.zdongcoding.converter.jsoup;

import android.util.Log;

import com.github.zdongcoding.jsoup.kit.ReflectKit;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ArrayList<String> objects = new ArrayList<String>(){};
        JSOUP<String> jsoup = new JSOUP<>("sgs");
        Type type = ReflectKit.chooseImpl(jsoup.getClass());
        if (type instanceof ParameterizedType) {
            System.out.println(((ParameterizedType) type).getActualTypeArguments()[0]);
        }
        System.out.println(type);
    }

}
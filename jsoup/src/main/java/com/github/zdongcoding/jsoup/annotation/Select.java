package com.github.zdongcoding.jsoup.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by user on 2017/3/8.
 */

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {

     String select();  //没有设置  default 必填
     String attr() default ""; //属性

     String key() default "";  //map  使用的
     boolean text() default false;  //text
}

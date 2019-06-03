package com.ibm.interf.rs.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanFieldAnnotation {

    /**
     * 标注该属性的顺序
     * @return 该属性的顺序     标注了改注解反射能拿到，没标注拿不到
     */
    int order();
}

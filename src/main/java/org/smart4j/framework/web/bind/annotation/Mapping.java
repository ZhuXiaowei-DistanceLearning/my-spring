package org.smart4j.framework.web.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE}) // 则表明此注解只能用在注解类型元素的声明上，那么此注解就是一个元注解。
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
}

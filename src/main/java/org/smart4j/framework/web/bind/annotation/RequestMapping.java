package org.smart4j.framework.web.bind.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented // 标记注解,即没有成员的注解,用于描述其它类型的annotation应该被作为被标注的程序成员的公共API
@Mapping
public @interface RequestMapping {
    String name() default "";

    String[] value() default {};

    RequestMethod[] method() default {};
}

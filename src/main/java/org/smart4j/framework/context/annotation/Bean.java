package org.smart4j.framework.context.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    String[] value() default {};

    String[] name() default {};

    String initMethod() default "";

    String destroyMethod();
}

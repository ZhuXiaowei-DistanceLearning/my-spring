package org.smart4j.framework.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexd
/**
 * 注入spring容器交由spring管理
 */
public @interface Component {
    String value() default "";
}

package io.leopard.web4j.permission.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 是否启用权限控制.
 *
 * @author 阿海
 *
 */
public @interface Permission {

	boolean enable() default true;

}

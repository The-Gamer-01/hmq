package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SPI机制的注解，标记此接口可被SPI实现.
 *
 * @author hyx
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    
    /**
     * 默认SPI加载名.
     * @return SPI类名.
     */
    String value() default "";
}

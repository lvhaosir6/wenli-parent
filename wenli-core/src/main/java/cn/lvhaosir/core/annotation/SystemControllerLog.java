package cn.lvhaosir.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lvhaosir on 2018/10/6.
 *  Controller层系统日志注解
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
    /**
     *  描述
     * @return
     */
    String description() default "";

}

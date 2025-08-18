package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yutaoshao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    /**
     * 声明了一个注解的属性（元素）
     * OperationType 是数据类型：
     *     当你使用 @AutoFill 注解时，你必须给它提供一个 OperationType 枚举中的值（比如 INSERT 或 UPDATE）。
     * value 是这个属性的名称，value 是一个非常特殊的属性名。
     * 在Java注解中，如果一个属性被命名为 value，并且它是该注解的唯一属性（或者其他属性都有默认值），那么在使用该注解时，可以省略属性名。
     */
    OperationType value();

}

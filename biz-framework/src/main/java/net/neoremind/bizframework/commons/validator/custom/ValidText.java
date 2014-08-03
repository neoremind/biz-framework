package net.neoremind.bizframework.commons.validator.custom;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 校验字符串长度,正则式,是否允许为空等
 * 
 * @author skyfalling
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StringValidator.class)
public @interface ValidText {

	String message() default "输入参数字符串非法";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String regex() default "";

	int max() default Integer.MAX_VALUE;

	int min() default 0;

	boolean allowEmpty() default false;

}

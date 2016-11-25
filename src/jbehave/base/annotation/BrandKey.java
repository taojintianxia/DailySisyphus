package jbehave.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jbehave.base.model.TestConstants;

/**
 * @author nianjun
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface BrandKey {

	String value() default TestConstants.CONFIG_BRAND;
}

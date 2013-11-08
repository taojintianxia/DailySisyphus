package com.officedepot.test.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.officedepot.test.common.model.TestConstants;

/**
 * 
 * @author hao-chen2
 * @version Feb 8, 2012
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CountryKey {

	String value() default TestConstants.OD_CONFIG_COUNTRY;
}

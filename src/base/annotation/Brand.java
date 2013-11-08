/**
 * 
 */
package com.officedepot.test.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.officedepot.test.common.model.Site;

/**
 * 
 * @author hao-chen2
 * @version Jan 5, 2012
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Brand {
	
	Site value();
	
}

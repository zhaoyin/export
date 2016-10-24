/**
 * 
 */
package com.crt.excel.imports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author UOrder
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ImportAnnotation {
	/**
	 * 设置字段在Excel输出时的顺序，决定在Excel中出现的位置
	 * @return
	 */
	public int index() default 1;
	/**
	 * 设置Excel中该列的列名
	 * @return
	 */
	public String showName() default "";
	/**
	 * 是否是主要列
	 * @return
	 */
	public boolean isPrimaryColumn() default false;
}

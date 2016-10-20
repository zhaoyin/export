/**
 * 
 */
package com.crt.excel.exports;

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
public @interface ExportAnnotation {
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
	 * 设置Excel中该列的对齐方式
	 * @return
	 */
	public short align() default 0;
	/**
	 * 设置该列在excel中是否隐藏
	 * @return
	 */
	public boolean hidden() default false;
	/**
	 * 设置该列在excel中格式化设置
	 * @return
	 */
	public int format() default 0;
	
	public int width() default 150;
}

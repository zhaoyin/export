/**
 * 
 */
package com.crt.export.convert;

/**
 * @author UOrder
 *
 */
interface IConverter<S, T> {
	// ① S是源类型 T是目标类型
	T convert(S element) throws RuntimeException; // ② 转换S类型的source到T目标类型的转换方法
}
/**
 * 
 */
package com.crt.excel.imports;

import com.crt.excel.exceptions.ImportException;

/**
 * @author UOrder
 *
 */
public interface IDataCallBack<T> {
	/**
	 * 如果转换失败，返回失败信息，如果转换成功，返回null
	 * @param t
	 * @return
	 * @throws ImportException
	 */
	String convert(T t) throws ImportException;
}

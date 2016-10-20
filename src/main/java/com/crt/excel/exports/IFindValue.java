/**
 * 
 */
package com.crt.excel.exports;

import com.crt.excel.exceptions.ExportException;

/**
 * @author UOrder
 *
 */
public interface IFindValue {
	public Object getValue(String fieldName)throws ExportException;
}

/**
 * 
 */
package com.crt.excel.exports;

import com.crt.excel.exceptions.ExportException;

/**
 * @author UOrder
 *
 */
public interface IJsonConverter {
	ExportConfig convert(String json) throws ExportException;
}

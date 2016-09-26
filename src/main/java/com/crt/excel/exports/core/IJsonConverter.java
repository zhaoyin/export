/**
 * 
 */
package com.crt.excel.exports.core;

import com.crt.excel.exports.exception.ExportException;

/**
 * @author UOrder
 *
 */
public interface IJsonConverter {
	ExportConfig convert(String json) throws ExportException;
}

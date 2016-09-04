/**
 * 
 */
package com.crt.export.core;

import com.crt.export.exception.ExportException;

/**
 * @author UOrder
 *
 */
public interface IJsonConverter {
	ExportConfig convert(String json) throws ExportException;
}

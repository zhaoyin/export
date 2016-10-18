/**
 * 
 */
package com.crt.excel.imports.models;

/**
 * @author UOrder
 *
 */
public interface IDataImport<WorkBook,T> {

	ImportResult convert();
}

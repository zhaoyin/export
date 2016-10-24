/**
 * 
 */
package com.crt.excel.imports;

import com.crt.excel.exceptions.ImportException;
import com.crt.excel.models.ImportResult;

/**
 * @author UOrder
 *
 */
public interface IDataImport<WorkBook,T> {
	/**
	 * 
	 * @return
	 * @throws ImportException
	 */
	ImportResult convert()throws ImportException;
	/**
	 * 
	 * @param importCallback
	 * @throws ImportException
	 */
	void convert(IImportCallback<ImportResult> importCallback) throws ImportException;
}

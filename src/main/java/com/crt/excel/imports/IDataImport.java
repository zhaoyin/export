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

	ImportResult convert()throws ImportException;
}

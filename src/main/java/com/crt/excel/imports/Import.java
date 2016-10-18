/**
 * 
 */
package com.crt.excel.imports;

import java.io.IOException;
import java.util.List;

import com.crt.excel.core.MetaColumn;
import com.crt.excel.models.ImportResult;

/**
 * @author UOrder
 *
 */
public class Import<T> extends AbstractImport<T> {

	/**
	 * @param clazz
	 * @param workbook
	 * @param callback
	 * @throws IOException 
	 */
	public Import(List<MetaColumn> exportColumns,Class<T> clazz, String  fileName, IDataCallBack<T> callback) throws Exception {
		super(exportColumns,clazz, fileName, callback);
	}

	public ImportResult importData(){
		return convert();
	}
}

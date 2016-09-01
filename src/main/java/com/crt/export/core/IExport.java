package com.crt.export.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.crt.export.exception.ExportException;
import com.crt.export.models.Column;

/**
 * 
 */
interface IExport {
	/**
	 * 
	 * 导出总方法
	 */
	Future<String> asyncExport(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException;
	String export(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException;

}

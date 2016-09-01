package com.crt.export.core;

import java.util.List;
import java.util.Map;

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
	String export(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException;

}

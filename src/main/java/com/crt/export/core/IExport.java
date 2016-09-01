package com.crt.export.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.crt.export.exception.ExportException;
import com.crt.export.models.Column;

/**
 * 输出
 */
interface IExport {
	/**
	 * 异步输出
	 * 
	 * @param columns
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @return
	 * @throws ExportException
	 */
	Future<String> asyncExport(List<Column> columns, List<Map<String, Object>> data, String title,
			String exportDirectory) throws ExportException;

	/**
	 * 同步输出
	 * 
	 * @param columns
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @return
	 * @throws ExportException
	 */
	String export(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException;

	/**
	 * s
	 * 
	 * @param json
	 * @return
	 * @throws ExportException
	 */
	String export(ExportConfig config) throws ExportException;

}

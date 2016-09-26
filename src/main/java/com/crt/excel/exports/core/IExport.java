package com.crt.excel.exports.core;

import java.util.List;
import java.util.Map;

import com.crt.excel.exports.exception.ExportException;
import com.crt.excel.exports.models.Column;

/**
 * 输出
 */
interface IExport {

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

	String export(String jsonConfig) throws ExportException;

	void asyncExport(String jsonConfig, AbstractCallback<String> callback) throws ExportException;

	void asyncExport(ExportConfig config, AbstractCallback<String> callback) throws ExportException;

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
	void asyncExport(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory,
			AbstractCallback<String> callback) throws ExportException;
}

package com.crt.excel.exports;

import java.util.List;
import java.util.Map;

import com.crt.excel.exceptions.ExportException;
import com.crt.excel.models.ExportColumn;

/**
 * 输出
 */
interface IExport {

	/**
	 * 同步输出
	 * 
	 * @param exportColumns
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @return
	 * @throws ExportException
	 */
	String export(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String title, String exportDirectory)
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

	void asyncExport(String jsonConfig, IExportCallback<String> callback) throws ExportException;

	void asyncExport(ExportConfig config, IExportCallback<String> callback) throws ExportException;

	/**
	 * 异步输出
	 * 
	 * @param exportColumns
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @return
	 * @throws ExportException
	 */
	void asyncExport(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String title, String exportDirectory,
			IExportCallback<String> callback) throws ExportException;
}

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
	 * 
	 * @param clazz
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @return
	 * @throws ExportException
	 */
	String export(Class<IFindValue> clazz, List<IFindValue> data, String title) throws ExportException;

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
	String export(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String title)
			throws ExportException;

	/**
	 * s
	 * 
	 * @param json
	 * @return
	 * @throws ExportException
	 */
	String export(ExportConfig config) throws ExportException;

	/**
	 * 根据json导出数据
	 * 
	 * @param jsonConfig
	 * @see com.crt.excel.exports.ExportConfig
	 * @return
	 * @throws ExportException
	 */
	String export(String jsonConfig) throws ExportException;

	/**
	 * 根据json导出数据
	 * 
	 * @param jsonConfig
	 * @see com.crt.excel.exports.ExportConfig
	 * @param callback
	 * @throws ExportException
	 */
	void asyncExport(String jsonConfig, IExportCallback<String> callback) throws ExportException;

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
	void asyncExport(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String title,
			IExportCallback<String> callback) throws ExportException;

	/**
	 * 
	 * @param clazz
	 * @param data
	 * @param title
	 * @param exportDirectory
	 * @throws ExportException
	 */
	void asyncExport(Class<IFindValue> clazz, List<IFindValue> data, String title, IExportCallback<String> callback)
			throws ExportException;
}

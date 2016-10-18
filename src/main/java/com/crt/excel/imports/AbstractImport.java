/**
 * 
 */
package com.crt.excel.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.excel.core.MetaColumn;
import com.crt.excel.exceptions.ImportException;
import com.crt.excel.exports.Export;
import com.crt.excel.imports.models.IDataImport;
import com.crt.excel.imports.models.ImportResult;

/**
 * @author UOrder
 *
 */
abstract class AbstractImport<T> extends AbstractExcel implements IDataImport<Workbook, T> {
	private static Logger log = LoggerFactory.getLogger(Export.class);
	private final Workbook workbook;
	private final IDataCallBack<T> callback;
	private final Class<T> clazz;
	private final Map<Integer, MetaColumn> metaData;

	public AbstractImport(List<MetaColumn> exportColumns, Class<T> clazz, String fileName, IDataCallBack<T> callback)
			throws Exception {
		FileInputStream stream = null;
		try {
			File file = new File(fileName);
			stream = new FileInputStream(file);
			this.workbook = new HSSFWorkbook(stream);
			this.callback = callback;
			this.clazz = clazz;
			metaData = new HashMap<Integer, MetaColumn>();
			if (null != exportColumns && !exportColumns.isEmpty()) {
				for (MetaColumn exportColumn : exportColumns) {
					metaData.put(exportColumn.getIndex(), exportColumn);
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(fileName + "不存在!");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (stream != null) {
				stream.close();
				stream = null;
			}
		}
	}

	/*
	 * 2016年9月30日 上午1:13:08
	 * 
	 * @see com.crt.excel.imports.models.IDataImport#parse()
	 */
	public ImportResult convert() {

		ImportResult result = new ImportResult();
		int sheetLength = this.workbook == null ? 0 : this.workbook.getNumberOfSheets();
		if (sheetLength <= 0) {
			result.addResult("无效的数据文件！");
		} else {
			for (int i = 0; i < sheetLength; i++) {
				Sheet sheet = this.workbook.getSheetAt(i);
				convertSheet(sheet, result);
			}
		}
		return result;
	}

	private String getErrMsg(String sheetName, int rowIndex, String msg) {
		return String.format("工作薄%s的第%s行转换失败:%s", sheetName, rowIndex, msg);
	}

	private void convertSheet(Sheet sheet, ImportResult result) {
		String sheetName = sheet.getSheetName();
		Integer noOfEntries = getNumberOfRows(sheet, 0);
		for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
			try {
				Row row = sheet.getRow(rowIndex);
				T t = convertRow(row);
				if (t != null) {
					try {
						String convertResult = callback.convert(t);
						if (convertResult != null && convertResult.trim().length() > 0 && !convertResult.isEmpty()) {
							result.addResult(getErrMsg(sheetName, rowIndex, convertResult));
						} else {
							result.addResult(null);
						}
					} catch (ImportException e) {
						result.addResult(getErrMsg(sheetName, rowIndex, e.getMessage()));
						continue;
					}
				} else {
					result.addResult(getErrMsg(sheetName, rowIndex, "无法将行数据转换为目标对象！"));
				}

			} catch (RuntimeException re) {
				result.addResult(getErrMsg(sheetName, rowIndex, re.getMessage()));
			}
		}
	}

	private T convertRow(Row row) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport.convertRow", e);
			}
		} catch (IllegalAccessException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport.convertRow", e);
			}
		}
		try {
			for (Integer index : metaData.keySet()) {
				MetaColumn exportColumn = metaData.get(index);
				Field field = clazz.getDeclaredField(exportColumn.getFieldName());
				// 1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal
				// 8:none 9:unknow
				switch (exportColumn.getDataType()) {
				case 1:
					field.set(t, readAsString(index, row));
					break;
				case 2:
					field.setInt(t, readAsInt(index, row));
					break;
				case 3:
					field.setDouble(t, readAsDouble(index, row));
					break;
				case 4:
					field.set(t, readAsDate(index, row));
					break;
				case 5:
					field.set(t, readAsInt(index, row));
					break;
				case 6:
					field.setBoolean(t, readAsBoolean(index, row));
					break;
				case 7:
					field.set(t, readAsDouble(index, row));
					break;
				case 8:
				case 9:
				default:
					field.set(t, readAsString(index, row));
					break;
				}
			}
		} catch (IllegalAccessException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport.convertRow", e);
			}
		} catch (NoSuchFieldException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport.convertRow", e);
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport.convertRow", e);
			}
		}
		return t;
	}

}

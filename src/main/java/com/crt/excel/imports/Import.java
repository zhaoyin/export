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
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.excel.core.MetaColumn;
import com.crt.excel.exceptions.ImportException;
import com.crt.excel.exports.Export;
import com.crt.excel.models.ImportResult;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * @author UOrder
 *
 */
public class Import<T> extends AbstractExcel implements IDataImport<Workbook, T> {
	private static Logger log = LoggerFactory.getLogger(Export.class);

	// 创建线程池
	final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	private final Workbook workbook;
	private final IDataCallBack<T> callback;
	private final Class<T> clazz;
	private final Map<Integer, MetaColumn> metaData;
	private int primaryColumn = 1;

	public Import(Class<T> clazz, String fileName, IDataCallBack<T> callback) throws ImportException {
		FileInputStream stream = null;
		try {
			File file = new File(fileName);
			stream = new FileInputStream(file);
			this.workbook = new HSSFWorkbook(stream);
			this.callback = callback;
			this.clazz = clazz;
			this.metaData = getMetaColumns(clazz);
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport", e);
			}
			throw new ImportException(e);
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport", e);
			}
			throw new ImportException(e);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("AbstractImport", e);
			}
			throw new ImportException(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					if (log.isErrorEnabled()) {
						log.error("AbstractImport", e);
					}
					throw new ImportException(e);
				}
				stream = null;
			}
		}
	}
	/**
	 * 内部转换
	 * @return
	 * @throws ImportException
	 */
	private ImportResult internalConvert() throws ImportException{
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
	
	/*
	 * 2016年9月30日 上午1:13:08
	 * 
	 * @see com.crt.excel.imports.models.IDataImport#convert()
	 */
	public void convert(IImportCallback<ImportResult> importCallback) throws ImportException {
		ListenableFuture<ImportResult> result = service.submit(new Callable<ImportResult>() {
			public ImportResult call() {
				return internalConvert();
			}
		});
		Futures.addCallback(result, importCallback);
	}
	/**
	 * @see com.crt.excel.imports.models.IDataImport#convert()
	 */
	public ImportResult convert() throws ImportException{
		return internalConvert();
	}

	/**
	 * 获取转换错误信息
	 * 
	 * @param sheetName
	 * @param rowIndex
	 * @param msg
	 * @return
	 */
	private String getErrMsg(String sheetName, int rowIndex, String msg) {
		return String.format("工作薄%s的第%s行转换失败:%s", sheetName, rowIndex, msg);
	}

	/**
	 * 转换工作薄
	 * 
	 * @param sheet
	 * @param result
	 */
	private void convertSheet(Sheet sheet, ImportResult result) {
		String sheetName = sheet.getSheetName();
		int realPrimaryColumn = primaryColumn - 1;
		Integer noOfEntries = getNumberOfRows(sheet, realPrimaryColumn);
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

	/**
	 * 将某一行数据转换为给定的实体对象
	 * 
	 * @param row
	 * @return
	 */
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
				if (field == null) {
					continue;
				}
				field.setAccessible(true);
				// 1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal
				// 8:none 9:unknow
				int realIndex = index >= 1 ? index - 1 : index;
				switch (exportColumn.getDataType()) {
				case 1:
					field.set(t, readAsString(realIndex, row));
					break;
				case 2:
					field.setInt(t, readAsInt(realIndex, row));
					break;
				case 3:
					field.setDouble(t, readAsDouble(realIndex, row));
					break;
				case 4:
					field.set(t, readAsDate(realIndex, row));
					break;
				case 5:
					field.set(t, readAsInt(realIndex, row));
					break;
				case 6:
					field.setBoolean(t, readAsBoolean(realIndex, row));
					break;
				case 7:
					field.set(t, readAsDouble(realIndex, row));
					break;
				case 8:
				case 9:
				default:
					field.set(t, readAsString(realIndex, row));
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

	/**
	 * 根据class获取T
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<Integer, MetaColumn> getMetaColumns(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Map<Integer, MetaColumn> columns = new HashMap<Integer, MetaColumn>();
		int index = 0;
		for (Field field : fields) {
			if (field.getModifiers() == 25) {
				continue;
			}
			String fieldName = field.getName();
			String typeString = field.getType().getSimpleName();
			short type = 1;
			// 1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal
			// 8:none 9:unknow
			if (typeString.equalsIgnoreCase("string")) {
				type = 1;
			} else if (typeString.equalsIgnoreCase("int") || typeString.equalsIgnoreCase("integer")) {
				type = 2;
			} else if (typeString.equalsIgnoreCase("double")) {
				type = 3;
			} else if (typeString.equalsIgnoreCase("date")) {
				type = 4;
			} else if (typeString.equalsIgnoreCase("short")) {
				type = 5;
			} else if (typeString.equalsIgnoreCase("boolean")) {
				type = 6;
			} else if (typeString.equalsIgnoreCase("bigdecimal")) {
				type = 7;
			} else {
				type = 9;
			}
			ImportAnnotation columnDefine = field.getAnnotation(ImportAnnotation.class);
			String showName = fieldName;
			index += 1;
			if (columnDefine != null) {
				index = columnDefine.index();
				if (columnDefine.showName() != null && columnDefine.showName().trim().length() > 0) {
					showName = columnDefine.showName();
				}
				if (columnDefine.isPrimaryColumn() == true) {
					primaryColumn = index;
				}
			}
			MetaColumn column = new MetaColumn(index, showName, fieldName, type);
			columns.put(index, column);
		}
		return columns;
	}

}

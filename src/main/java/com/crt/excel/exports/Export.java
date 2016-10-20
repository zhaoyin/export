package com.crt.excel.exports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.excel.core.ExcelProperties;
import com.crt.excel.draw.DrawContext;
import com.crt.excel.draw.DrawWorkBook;
import com.crt.excel.exceptions.ExportException;
import com.crt.excel.exceptions.ExportExceptionEnum;
import com.crt.excel.models.ExportColumn;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * @author UOrder
 * @version 1.0.1b
 */
public class Export implements IExport {

	private Export() {

	}

	private static class ExportHolder {
		public static Export exporter = new Export();
	}

	public static Export getInstance() {
		return ExportHolder.exporter;
	}

	private static Logger log = LoggerFactory.getLogger(Export.class);

	// 创建线程池
	final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	public IJsonConverter getJsonConverter() {
		String clazz=ExcelProperties.getProperty("export.converter");
		if(clazz!=null && !clazz.isEmpty()){
			try {
				IJsonConverter converter=(IJsonConverter)Class.forName(clazz).newInstance();
				return converter;
			} catch (InstantiationException e) {
				if(log.isErrorEnabled()){
					log.error("Export.getJsonConverter", e);
				}
			} catch (IllegalAccessException e) {
				if(log.isErrorEnabled()){
					log.error("Export.getJsonConverter", e);
				}
			} catch (ClassNotFoundException e) {
				if(log.isErrorEnabled()){
					log.error("Export.getJsonConverter", e);
				}
			}
		}
		return null;
	}

	/*
	 * 2016年8月31日 下午1:47:38
	 * 
	 * @see com.crt.export.api.IExport#export(java.util.List, java.util.List,
	 * java.util.List, java.lang.String)
	 */
	public void asyncExport(final List<ExportColumn> exportColumns, final List<Map<String, Object>> data, final String title,
			final String exportDirectory, IExportCallback<String> callback) throws ExportException {
		ExportConfig config = new ExportConfig(exportColumns, data, exportDirectory);
		config.setTitle(title);
		asyncExport(config, callback);
	}

	
	/**
	 * 
	 */
	public String export(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException {
		ExportConfig config = new ExportConfig(exportColumns, data, exportDirectory);
		config.setTitle(title);
		return export(config);
	}

	
	/*
	 * 2016年9月1日 下午2:02:27
	 * 
	 * @see com.crt.export.core.IExport#export(java.lang.String)
	 */
	public String export(ExportConfig config) throws ExportException {
		if (log.isDebugEnabled()) {
			log.debug("excel export start...");
		}
		if (log.isDebugEnabled()) {
			log.debug("excel : columns:" + config.getColumns());
		}
		FileOutputStream fs = null;
		File file = null;
		try {
			DrawContext context = new DrawContext(config);

			DrawWorkBook draw = new DrawWorkBook();
			draw.build(context);

			String strGuid = UUID.randomUUID().toString();
			String fileName = context.getTitle() != null ? context.getTitle() + "_" + strGuid : strGuid;
			// 定义文件名格式并创建
			file = File.createTempFile(fileName, ".xls", context.getExportDirectory());

			file.mkdirs();
			fs = new FileOutputStream(file);
			context.getWorkBook().write(fs);
			fs.close();
		} catch (ExportException e) {
			if (log.isErrorEnabled()) {
				log.error("excel.Export--error", e);
			}
			throw e;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("excel.Export--error", e);
			}
			throw new ExportException(e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					fs = null;
					throw new ExportException(e);
				}
			}
			fs = null;
		}
		return file.getAbsolutePath();
	}

	/*
	 * 2016年9月4日 上午11:30:39
	 * 
	 * @see com.crt.export.core.IExport#export(java.lang.String)
	 */
	public String export(String jsonConfig) throws ExportException {
		IJsonConverter converter = getJsonConverter();
		ExportConfig config = converter.convert(jsonConfig);
		if (config != null) {
			return export(config);
		}
		return null;
	}

	/*
	 * 2016年9月4日 上午11:30:39
	 * 
	 * @see com.crt.export.core.IExport#asyncExport(java.lang.String)
	 */
	public void asyncExport(String jsonConfig, IExportCallback<String> callback) throws ExportException {
		IJsonConverter converter = getJsonConverter();
		if(converter==null){
			throw new ExportException(ExportExceptionEnum.ConvertJson);
		}
		ExportConfig config = converter.convert(jsonConfig);
		if (config != null) {
			asyncExport(config, callback);
		}
	}

	/*
	 * 2016年9月4日 上午11:34:39
	 * 
	 * @see
	 * com.crt.export.core.IExport#asyncExport(com.crt.export.core.ExportConfig)
	 */
	public void asyncExport(final ExportConfig config, IExportCallback<String> callback) throws ExportException {
		ListenableFuture<String> result = service.submit(new Callable<String>() {
			public String call() {
				try {
					return export(config);
				} catch (ExportException e) {
					throw e;
				}
			}
		});
		Futures.addCallback(result, callback);
	}

	/* 2016年10月20日
	 * 下午1:31:05
	 * @see com.crt.excel.exports.IExport#asyncExport(java.lang.Class, java.util.List, java.lang.String, java.lang.String)
	 */
	public <T> void asyncExport(Class<T> clazz, List<T> data, String title, String exportDirectory)
			throws ExportException {
		// TODO Auto-generated method stub
		
	}

	/* 2016年10月20日
	 * 下午1:31:23
	 * @see com.crt.excel.exports.IExport#export(java.lang.Class, java.util.List, java.lang.String, java.lang.String)
	 */
	public <T> String export(Class<T> clazz, List<IFindValue> data, String title, String exportDirectory)
			throws ExportException {
		Map<String,ExportColumn> columns=getExportColumns(clazz);
		List<Map<String,Object>> mapData=new ArrayList<Map<String,Object>>();
		for(IFindValue t:data){
			Set<String> fields=columns.keySet();
			Map<String,Object> rowData=new HashMap<String,Object>();
			for(String field:fields){
				rowData.put(field, t.getValue(field));
			}
			mapData.add(rowData);
		}
		List<ExportColumn> columnData=new ArrayList<ExportColumn>();
		columnData.addAll(columns.values());
		return export(columnData,mapData,title,exportDirectory);
	}
	/**
	 * 根据class获取T
	 * @param clazz
	 * @return
	 */
	private <T> Map<String,ExportColumn> getExportColumns(Class<T> clazz){
		Field[] fields=clazz.getDeclaredFields();
		Map<String,ExportColumn> columns=new HashMap<String,ExportColumn>();
		int index=0;
		for(Field field:fields){
			if(field.getModifiers()==25){
				continue;
			}
			String fieldName=field.getName();
			String typeString=field.getType().getSimpleName();
			short type=1;
			//1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal 8:none 9:unknow
			if(typeString.equalsIgnoreCase("string")){
				type=1;
			}else if(typeString.equalsIgnoreCase("int") || typeString.equalsIgnoreCase("integer")){
				type=2;
			}else if(typeString.equalsIgnoreCase("double")){
				type=3;
			}else if(typeString.equalsIgnoreCase("date")){
				type=4;
			}else if(typeString.equalsIgnoreCase("short")){
				type=5;
			}else if(typeString.equalsIgnoreCase("boolean")){
				type=6;
			}else if(typeString.equalsIgnoreCase("bigdecimal")){
				type=7;
			}else{
				type=9;
			}
			ExportAnnotation columnDefine=field.getAnnotation(ExportAnnotation.class);
			String showName=fieldName;
			index+=1;
			int width=150;
			short align=0;
			boolean hidden=false;
			int format=0;
			if(columnDefine!=null){
				index=columnDefine.index();
				if(columnDefine.showName()!=null && columnDefine.showName().trim().length()>0){
					showName=columnDefine.showName();
				}
				width=columnDefine.width();
				align=columnDefine.align();
				hidden=columnDefine.hidden();
				format=columnDefine.format();
			}
			ExportColumn column=new ExportColumn(index,showName,fieldName,width,type,align,hidden,format);
			columns.put(fieldName, column);
		}
		return columns;
	}

}

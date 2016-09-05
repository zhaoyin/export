package com.crt.export.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.export.draw.DrawContext;
import com.crt.export.draw.DrawWorkBook;
import com.crt.export.exception.ExceptionEnum;
import com.crt.export.exception.ExportException;
import com.crt.export.models.Column;
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

	IJsonConverter getJsonConverter() {
		return null;
	}

	/*
	 * 2016年8月31日 下午1:47:38
	 * 
	 * @see com.crt.export.api.IExport#export(java.util.List, java.util.List,
	 * java.util.List, java.lang.String)
	 */
	public void asyncExport(final List<Column> columns, final List<Map<String, Object>> data, final String title,
			final String exportDirectory, AbstractCallback<String> callback) throws ExportException {
		ExportConfig config = new ExportConfig(columns, data, exportDirectory);
		config.setTitle(title);
		asyncExport(config, callback);
	}

	/**
	 * 
	 */
	public String export(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory)
			throws ExportException {
		ExportConfig config = new ExportConfig(columns, data, exportDirectory);
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
	public void asyncExport(String jsonConfig, AbstractCallback<String> callback) throws ExportException {
		IJsonConverter converter = getJsonConverter();
		if(converter==null){
			throw new ExportException(ExceptionEnum.ConvertJson);
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
	public void asyncExport(final ExportConfig config, AbstractCallback<String> callback) throws ExportException {
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

}

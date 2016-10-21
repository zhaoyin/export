/**
 * 
 */
package com.crt.excel.draw;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.crt.excel.exceptions.ExportException;
import com.crt.excel.exceptions.ExportExceptionEnum;
import com.crt.excel.exports.ExportConfig;
import com.crt.excel.models.ExportColumn;

/**
 * @author UOrder
 *
 */
public class DrawContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8732655585756456840L;

	public DrawContext(ExportConfig config) {
		if (config == null) {
			throw new ExportException(ExportExceptionEnum.Initialize);
		}
		this.exportColumns = config.getColumns();
		if (config.getTitle() != null && config.getTitle().trim().length() > 0) {
			try {
				this.title = new String(config.getTitle().getBytes("UTF-8"), "UTF-8");
			} catch (Exception e) {
				this.title = "Excel Data Report";
			}
		}
		if (config.getData() != null && !config.getData().isEmpty()) {
			this.data = config.getData();
		}
		this.exportDir = config.exportDirectory;
		
		if (config.getHasSumRow() != null) {
			this.hasSumRow = config.getHasSumRow();
		}
		if (config.getHasRowNo() != null) {
			this.hasRowNo = config.getHasRowNo();
		}
		if (config.getDataRowHeight() != null && config.getDataRowHeight()>0) {
			this.dataRowHeight = config.getDataRowHeight();
		}
		if(config.getHeadRowHeight()!=null && config.getHeadRowHeight()>0){
			this.headRowHeight=config.getHeadRowHeight();
		}
		this.initialize();
	}

	/**
	 * 是否有合计行
	 */
	private Boolean hasSumRow = false;

	/**
	 * 获取是否有合计行信息
	 * 
	 * @return
	 */
	public Boolean getHasSumRow() {
		return this.hasSumRow;
	}

	/**
	 * 是否有行号列
	 */
	private Boolean hasRowNo = true;

	/**
	 * 获取是否有行号信息
	 * 
	 * @return
	 */
	public Boolean getHasRowNo() {
		return this.hasRowNo;
	}

	/**
	 * 数据行高
	 */
	private Short dataRowHeight = 15;

	/**
	 * 获取数据行高设置
	 * 
	 * @return
	 */
	public Short getDataRowHeight() {
		return this.dataRowHeight;
	}
	/**
	 * 表头行高
	 */
	private Short headRowHeight = 15;
	/**
	 * 获取表头行高
	 * @return
	 */
	public short getHeadRowHeight() {
		return this.headRowHeight;
	}

	private String exportDir = null;

	public File getExportDirectory() {
		if (this.exportDir == null || this.exportDir.trim().length() <= 0) {
			throw new ExportException(ExportExceptionEnum.UnKnownExportDirectory);
		}
		File file = new File(this.exportDir);
		if (!file.exists() || !file.isDirectory()) {
			throw new ExportException(ExportExceptionEnum.UnKnownExportDirectory);
		}
		return file;
	}

	/**
	 * 垂直比例数
	 */
	protected final Short VRATIO = 20;

	/**
	 * 水平比例数
	 */
	protected final Short HRATIO = 40;

	/**
	 * Excels文档
	 */
	private HSSFWorkbook WORKBOOK = null;

	public HSSFWorkbook getWorkBook() {
		if (this.WORKBOOK == null) {
			throw new ExportException(ExportExceptionEnum.UnKnownBook);
		}
		return this.WORKBOOK;
	}

	private HSSFSheet sheet = null;

	protected HSSFSheet getCurrentSheet() {
		if (this.sheet == null) {
			throw new ExportException(ExportExceptionEnum.UnKnownSheet);
		}
		return this.sheet;
	}

	/**
	 * 当前行号
	 */
	private int currentRow = -1;

	/**
	 * 当前页号
	 */
	private int currentSheet = 0;

	/**
	 * 单页最大行，默认65536
	 */
	private final int MAXROW = 65536;

	/**
	 * 列及列头信息
	 */
	private List<ExportColumn> exportColumns = null;

	protected List<ExportColumn> getColumns() {
		return this.exportColumns;
	}

	// protected void setColumns(List<Column> columns) {
	// this.columns = columns;
	// }

	private int columnCount = 0;

	/**
	 * 得到列数
	 */
	protected int getColumnCount() {
		if (this.columnCount > 0) {
			return this.columnCount;
		}
		if (this.exportColumns == null || this.exportColumns.isEmpty()) {
			return 0;
		}
		if (this.hasRowNo) {
			this.columnCount = this.exportColumns.size() + 1;
		} else {
			this.columnCount = this.exportColumns.size();
		}
		return this.columnCount;
	}

	private String title;

	public String getTitle() {
		return this.title;
	}

	// protected void setTitle(String title) {
	// this.title = title;
	// }

	/**
	 * 结果集
	 */
	private List<Map<String, Object>> data = null;

	protected List<Map<String, Object>> getData() {
		return this.data;
	}

	// protected void setData(List<Map<String, Object>> result) {
	// this.data = result;
	// }

	/**
	 * 初始化Excel文档
	 * 
	 * @return 成功返回非负值
	 */
	private void initialize() {
		if (this.exportColumns.size() == 0) {
			throw new ExportException(ExportExceptionEnum.NoColumn);
		}
		if (this.WORKBOOK == null) {
			this.WORKBOOK = new HSSFWorkbook();
			this.sheet = this.WORKBOOK.createSheet();
		}
		this.currentRow = -1;
		this.currentSheet = 0;
	}

	/**
	 * 增加行
	 * 
	 * @return HSSFRow
	 */
	protected HSSFRow addRow() {
		if (this.currentRow == MAXROW - 1) {
			this.currentRow = 0;
			this.currentSheet++;
			this.sheet = WORKBOOK.createSheet();
		} else {
			this.currentRow++;
		}
		return WORKBOOK.getSheetAt(this.currentSheet).createRow(this.currentRow);
	}
}

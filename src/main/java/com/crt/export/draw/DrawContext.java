/**
 * 
 */
package com.crt.export.draw;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.crt.export.exception.ExceptionEnum;
import com.crt.export.exception.ExportException;
import com.crt.export.models.Column;
import com.crt.export.models.DataBlock;
import com.crt.export.models.TypeEnum;

/**
 * @author UOrder
 *
 */
public class DrawContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8732655585756456840L;

	public DrawContext(List<Column> columns, List<Map<String, Object>> data, String title, String exportDirectory) {
		this.columns = columns;
		try {
			this.title = new String(title.getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {
			this.title = "Excel Data Report";
		}
		if (data != null && !data.isEmpty()) {
			this.data = data;
		}
		this.exportDir = exportDirectory;
		this.initialize();
	}

	private String exportDir = null;

	public File getExportDirectory() {
		if (this.exportDir == null || this.exportDir.trim().length() <= 0) {
			throw new ExportException(ExceptionEnum.UnKnownExportDirectory);
		}
		File file = new File(this.exportDir);
		if (!file.exists() || !file.isDirectory()) {
			throw new ExportException(ExceptionEnum.UnKnownExportDirectory);
		}
		return file;
	}

	/**
	 * 垂直比例数
	 */
	protected final short VRATIO = 20;

	/**
	 * 水平比例数
	 */
	protected final short HRATIO = 40;

	/**
	 * Excels文档
	 */
	private HSSFWorkbook WORKBOOK = null;

	public HSSFWorkbook getWorkBook() {
		if (this.WORKBOOK == null) {
			throw new ExportException(ExceptionEnum.UnKnownBook);
		}
		return this.WORKBOOK;
	}

	private HSSFSheet sheet = null;

	protected HSSFSheet getCurrentSheet() {
		if (this.sheet == null) {
			throw new ExportException(ExceptionEnum.UnKnownSheet);
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
	private List<Column> columns = null;

	protected List<Column> getColumns() {
		return this.columns;
	}

	// protected void setColumns(List<Column> columns) {
	// this.columns = columns;
	// }

	/**
	 * 数据块信息
	 */
	private DataBlock dataBlock;

	protected DataBlock getDataBlock() {
		if (this.dataBlock == null) {
			this.dataBlock = new DataBlock();
		}
		return this.dataBlock;
	}

	private int columnCount = 0;

	/**
	 * 得到列数
	 */
	protected int getColumnCount() {
		if (this.columnCount > 0) {
			return this.columnCount;
		}
		if (this.columns == null || this.columns.isEmpty()) {
			return 0;
		}
		if (this.getDataBlock().bHasRowNo) {
			this.columnCount = this.columns.size() + 1;
		} else {
			this.columnCount = this.columns.size();
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
		if (this.columns.size() == 0) {
			throw new ExportException(ExceptionEnum.NoColumn);
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

	/**
	 * 增加合计行
	 */
	protected HSSFRow addSumRow() {
		HSSFRow row = addRow();
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle cs = this.WORKBOOK.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cs.setHidden(false);
		HSSFFont font = this.WORKBOOK.createFont();
		font.setStrikeout(false);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cs.setFont(font);
		cell.setCellValue(new HSSFRichTextString("合计："));
		cell.setCellStyle(cs);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		boolean hasRowNo = this.dataBlock.bHasRowNo;
		for (Column column : this.columns) {
			if (hasRowNo) {
				cell = row.createCell(column.getIndex() + 1);
			} else {
				cell = row.createCell(column.getIndex());
			}
			if (column.getDataType() == TypeEnum.Double.getIndex()
					|| column.getDataType() == TypeEnum.BigDecimal.getIndex()
					|| column.getDataType() == TypeEnum.Int.getIndex()
					|| column.getDataType() == TypeEnum.Short.getIndex()) {
				cs = WORKBOOK.createCellStyle();
				cs.setAlignment(column.getAlign());
				cs.setHidden(false);
				cs.setFont(this.dataBlock.getColumnFont(this.WORKBOOK));
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

				if (column.getDataType() == TypeEnum.Int.getIndex()) {
					cs.setDataFormat(column.getGeneralFormat());
				} else {
					cs.setDataFormat(column.getNumericFormat());
				}
				cell.setCellValue(column.getSumValue());
			}
			cell.setCellStyle(cs);
		}
		return row;
	}

	/**
	 * 设置列类型
	 * 
	 * @return
	 */
	protected short getColumnType(Object obj, Column column) {
		if (obj == null) {
			return TypeEnum.None.getIndex();
		}
		if (obj instanceof java.lang.Integer) {
			return TypeEnum.Int.getIndex();
		}
		if (obj.getClass().equals(java.math.BigInteger.class)) {
			return TypeEnum.Int.getIndex();
		}
		// 百分比-double
		else if ((obj instanceof java.lang.Double) && (column.getShowName().indexOf("%") != -1)
				&& (column.getShowName().indexOf("%") == column.getShowName().lastIndexOf("%"))) {
			return TypeEnum.Percent.getIndex();
		}
		// 百分比-BigDecimal
		else if ((obj instanceof java.math.BigDecimal) && (column.getShowName().indexOf("%") != -1)
				&& (column.getShowName().indexOf("%") == column.getShowName().lastIndexOf("%"))) {
			return TypeEnum.Percent.getIndex();
		} else if (obj instanceof java.lang.Double) {
			return TypeEnum.Double.getIndex();
		} else if (obj instanceof java.math.BigDecimal) {
			return TypeEnum.BigDecimal.getIndex();
		} else if (obj instanceof java.util.Date) {
			return TypeEnum.Date.getIndex();
		} else if (obj instanceof java.lang.Boolean) {
			return TypeEnum.Boolean.getIndex();
		} else if (obj instanceof java.lang.String) {
			return TypeEnum.String.getIndex();
		} else if (obj instanceof java.lang.Short) {
			return TypeEnum.Short.getIndex();
		} else {
			return TypeEnum.Unknown.getIndex();
		}

	}
}

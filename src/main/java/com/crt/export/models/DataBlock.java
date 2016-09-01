package com.crt.export.models;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author UOrder
 */
public class DataBlock {
	/**
	 * 是否添加合计行，默认为true
	 */
	private boolean hasSumRow = false;

	public Boolean getHasSumRow() {
		return this.hasSumRow;
	}

	public void setHasSumRow(Boolean hasSumRow) {
		if (hasSumRow != null) {
			this.hasSumRow = hasSumRow;
		}
	}

	/**
	 * 是否添加列序号，默认为false
	 */
	private boolean hasRowNo = true;

	public Boolean getHasRowNo() {
		return this.hasRowNo;
	}

	public void setHasRowNo(Boolean hasRowNo) {
		if (hasRowNo != null) {
			this.hasRowNo = hasRowNo;
		}
	}

	/**
	 * 数据块行高
	 */
	private short iDataRowHeight = 15;

	public Short getDataRowHeight() {
		return this.iDataRowHeight;
	}

	public void setDataRowHeight(Short rowHeight) {
		if (rowHeight != null) {
			this.iDataRowHeight = rowHeight;
		}
	}

	private HSSFFont Font = null;

	/**
	 * 数据块字体
	 */
	public HSSFFont getColumnFont(HSSFWorkbook wb) {
		if (Font == null) {
			Font = wb.createFont();
			Font.setFontHeightInPoints((short) 10);
			Font.setBoldweight(org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_NORMAL);
			Font.setFontName("宋体");
			Font.setItalic(false);
			Font.setStrikeout(false);
		}
		return Font;
	}

	/**
	 * 得到主体数据样式,包含默认字体
	 */
	public HSSFCellStyle getDataCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle listCS = wb.createCellStyle();
		listCS.setFont(this.getColumnFont(wb));
		return listCS;
	}
}

package com.crt.excel.exports.models;

import org.apache.poi.hssf.usermodel.HSSFFont;

public class DataCell {

	/**
	 * 单元格的字体样式
	 */
	private HSSFFont Font;

	protected void setFont(HSSFFont font) {
		this.Font = font;
	}

	public HSSFFont getFont() {
		return this.Font;
	}
	
	/**
	 * 单元格对齐方式
	 */
	private short colAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_LEFT;

	protected void setColAlign(short colAlign) {
		this.colAlign = colAlign;
	}

	public short getColAlign() {
		return this.colAlign;
	}
	
	/**
	 * 单元格的填充内容
	 */
	private String cellText = "";

	protected void setCellText(String cellText) {
		this.cellText = cellText;
	}

	public String getCellText() {
		return this.cellText;
	}

}

package com.crt.export.models;

import org.apache.poi.hssf.usermodel.HSSFFont;

public class Cell {

	/**
	 * 单元格的字体样式
	 */
	private HSSFFont Font;

	/**
	 * 单元格对齐方式
	 */
	private short iColAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_LEFT;

	/**
	 * 单元格的填充内容
	 */
	private String cCellText = "";

	protected void setFont(HSSFFont font) {
		this.Font = font;
	}

	public HSSFFont getFont() {
		return this.Font;
	}

	protected void setColAlign(short colAlign) {
		this.iColAlign = colAlign;
	}

	public short getColAlign() {
		return this.iColAlign;
	}

	protected void setCellText(String cellText) {
		this.cCellText = cellText;
	}

	public String getCellText() {
		return this.cCellText;
	}

}

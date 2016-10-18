package com.crt.excel.exports.models;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class TableHead {

	/**
	 * 构造函数
	 * 
	 * @param index:序号
	 * @param row:表头行
	 * @param col:表头列
	 * @param rowspan:单元格合并行数
	 * @param colspan:单元格合并列数
	 * @param colAlign:列对齐方式
	 * @param headName:单元格填充内容
	 */
	public TableHead(int index, int row, int rowSpan, int colSpan, short colAlign, String headName) {
		this.index = index;
		this.row = row;
		this.rowSpan = rowSpan;
		this.colAlign = colAlign;
		this.headName = headName;
	}

	/**
	 * 列序号
	 */
	private int index = 0;

	public Integer getIndex() {
		return this.index;
	}

	protected void setIndex(Integer index) {
		if (index != null) {
			this.index = index;
		}
	}

	/**
	 * 表头行
	 */
	private int row;

	public Integer getRow() {
		return this.row;
	}

	protected void setRow(Integer row) {
		if (row != null) {
			this.row = row;
		}
	}

	/**
	 * 单元格合并行数
	 */
	private int rowSpan;

	public Integer getRowSpan() {
		return this.rowSpan;
	}

	protected void setRowSpan(Integer rowSpan) {
		if (rowSpan != null) {
			this.rowSpan = rowSpan;
		}
	}

	/**
	 * 单元格合并列数
	 */
	private int colSpan = 0;

	public Integer getColSpan() {
		return this.colSpan;
	}

	protected void setColSpan(Integer colSpan) {
		if (colSpan != null) {
			this.colSpan = colSpan;
		}
	}

	/**
	 * 单元格对齐方式
	 */
	private short colAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_LEFT;

	public Short getColAlign() {
		return this.colAlign;
	}

	protected void setColAlign(Short colAlign) {
		if (colAlign != null) {
			this.colAlign = colAlign;
		}
	}

	/**
	 * 单元格填充内容
	 */
	private String headName = "undefined";

	public String getHeadName() {
		return this.headName;
	}

	protected void setHeadName(String headName) {
		if (headName != null) {
			this.headName = headName;
		}
	}

	// ==================================
	/**
	 * 每列的样式
	 */
	private HSSFCellStyle oCellStyle;

	/**
	 * 每列的样式
	 */
	public HSSFCellStyle getCellStyle() {
		return this.oCellStyle;
	}

	/**
	 * 每列的样式
	 */
	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.oCellStyle = cellStyle;
	}
}

package com.crt.export.models;

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
	public TableHead(int index, int row, int col, int rowSpan, int colSpan, short colAlign, String headName) {
		this.index = index;
		this.iRow = row;
		this.iCol = col;
		this.iRowSpan = rowSpan;
		this.iColAlign = colAlign;
		this.cHeadName = headName;
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
	private int iRow;

	public Integer getRow() {
		return this.iRow;
	}

	protected void setRow(Integer row) {
		if (row != null) {
			this.iRow = row;
		}
	}

	/**
	 * 表头列
	 */
	private int iCol;

	public Integer getCol() {
		return this.iCol;
	}

	protected void setCol(Integer col) {
		if (col != null) {
			this.iCol = col;
		}
	}

	/**
	 * 单元格合并行数
	 */
	private int iRowSpan;

	public Integer getRowSpan() {
		return this.iRowSpan;
	}

	protected void setRowSpan(Integer rowSpan) {
		if (rowSpan != null) {
			this.iRowSpan = rowSpan;
		}
	}

	/**
	 * 单元格合并列数
	 */
	private int iColSpan = 0;

	public Integer getColSpan() {
		return this.iColSpan;
	}

	protected void setColSpan(Integer colSpan) {
		if (colSpan != null) {
			this.iColSpan = colSpan;
		}
	}

	/**
	 * 单元格对齐方式
	 */
	private short iColAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_LEFT;

	public Short getColAlign() {
		return this.iColAlign;
	}

	protected void setColAlign(Short colAlign) {
		if (colAlign != null) {
			this.iColAlign = colAlign;
		}
	}

	/**
	 * 单元格填充内容
	 */
	private String cHeadName = "undefined";

	public String getHeadName() {
		return this.cHeadName;
	}

	protected void setHeadName(String headName) {
		if (headName != null) {
			this.cHeadName = headName;
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

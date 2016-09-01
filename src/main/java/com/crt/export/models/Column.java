package com.crt.export.models;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author UOrder
 */

public class Column {

	private HSSFFont Font;

	/**
	 * 构造函数
	 * 
	 * @param index:序号
	 * @param colName:列名
	 * @param colWidth:列宽,默认100
	 * @param colDataType:数据类型
	 * @param colAlign:列对齐方式
	 * @param colHidden:是否隐藏列
	 */
	public Column(int index, String colShowName, String colFieldName, short colDataType) {
		this.index = index;
		this.cShowName = colShowName;
		this.iDataType = colDataType;
		this.cFieldName = colFieldName;
	}

	/**
	 * 构造函数
	 * 
	 * @param index
	 * @param colShowName
	 *            列名(显示)
	 * @param colFieldName
	 *            列名
	 * @param colWidth
	 *            列宽,默认150
	 * @param colDataType
	 *            类型
	 * @param colAlign
	 *            排列
	 * @param colHidden
	 *            是否隐藏
	 */
	public Column(int index, String colShowName, String colFieldName, int colWidth, short colDataType, short colAlign,
			boolean colHidden) {
		this.index = index;
		this.cShowName = colShowName;
		this.cFieldName = colFieldName;
		this.iWidth = colWidth;
		this.iDataType = colDataType;
		this.iAlign = colAlign;
		this.bHidden = colHidden;
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
	 * 列名
	 */
	private String cShowName = "undefined";

	public String getShowName() {
		return this.cShowName;
	}

	protected void setShowName(String name) {
		if (name != null) {
			this.cShowName = name;
		}
	}

	/**
	 * 列取数值
	 */
	private String cFieldName = "";

	public String getFieldName() {
		return this.cFieldName;
	}

	protected void setFieldName(String data) {
		if (data != null) {
			this.cFieldName = data;
		}
	}

	/**
	 * 列宽
	 */
	private int iWidth = 150;

	public Integer getWidth() {
		return this.iWidth;
	}

	protected void setWidth(Integer width) {
		if (width != null) {
			this.iWidth = width;
		}
	}

	/**
	 * 列对齐方式
	 */
	private short iAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_GENERAL;

	public Short getAlign() {
		return this.iAlign;
	}

	protected void setAlign(Short align) {
		if (align != null) {
			this.iAlign = align;
		}
	}

	/**
	 * 列是否隐藏
	 */
	private boolean bHidden = false;

	public Boolean getHidden() {
		return this.bHidden;
	}

	protected void setHidden(Boolean hidden) {
		this.bHidden = hidden;
	}

	/**
	 * 合计值
	 */
	private double fSumValue = 0;

	public Double getSumValue() {
		return this.fSumValue;
	}

	public void setSumValue(Double sumValue) {
		if (sumValue != null) {
			this.fSumValue += sumValue;
		}
	}

	/**
	 * 列数字型格式("#,##0.00")
	 */
	private short iNumericFomat = 4;

	public Short getNumericFormat() {
		return this.iNumericFomat;
	}

	protected void setNumericFormat(Short numericFormat) {
		if (numericFormat != null) {
			this.iNumericFomat = numericFormat;
		}
	}

	/**
	 * 列普通型("General")
	 */
	private short iGeneralFomat = 0;

	public Short getGeneralFormat() {
		return this.iGeneralFomat;
	}

	protected void setGeneralFormat(Short generalFormat) {
		if (generalFormat != null) {
			this.iGeneralFomat = generalFormat;
		}
	}

	/**
	 * 列日期型("d-mmm-yy")
	 */
	private short iDateFomat = 0xe;

	public Short getDateFormat() {
		return this.iDateFomat;
	}

	protected void setDateFormat(Short dateFormat) {
		if (dateFormat != null) {
			this.iDateFomat = dateFormat;
		}
	}

	/**
	 * 百分比0.00%
	 */
	private String cPercentFomat = "0.00%";

	public String getPercentFormat() {
		return this.cPercentFomat;
	}

	protected void setPercentFormat(String percentFormat) {
		if (percentFormat != null) {
			this.cPercentFomat = percentFormat;
		}
	}

	/**
	 * 列数据类型,默认String型
	 * 
	 * @详细信息 1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal
	 *       8:none 9:unknow
	 */
	private short iDataType = TypeEnum.None.getIndex();

	public Short getDataType() {
		return this.iDataType;
	}

	public void setDataType(Short dataType) {
		this.iDataType = dataType;
	}

	/**
	 * 列头对齐方式
	 */
	private short iHeadAlign = org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_CENTER;

	public Short getHeadAlign() {
		return this.iHeadAlign;
	}

	protected void setHeadAlign(Short headAlign) {
		if (headAlign != null) {
			this.iHeadAlign = headAlign;
		}
	}

	/**
	 * 列头文字格式("General")
	 */
	private short iHeadFomat = 0;

	public Short getHeadFormat() {
		return this.iHeadFomat;
	}

	protected void setHeadFormat(Short headStringDataFormat) {
		if (headStringDataFormat != null) {
			this.iHeadFomat = headStringDataFormat;
		}
	}

	/**
	 * 列头行高
	 */
	private short iHeadRowHeight = 15;

	public Short getHeadRowHeight() {
		return this.iHeadRowHeight;
	}

	protected void setHeadRowHeight(Short headRowHeight) {
		if (headRowHeight != null) {
			this.iHeadRowHeight = headRowHeight;
		}
	}

	/**
	 * 列头字体
	 */
	public HSSFFont getHeadFont(HSSFWorkbook wb) {
		if (this.Font == null) {
			this.Font = wb.createFont();
			this.Font.setFontHeightInPoints((short) 12);
			this.Font.setFontName("宋体");
			this.Font.setBoldweight(org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_BOLD);
			this.Font.setItalic(false);
			this.Font.setStrikeout(false);
		}
		return this.Font;
	}

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

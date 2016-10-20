package com.crt.excel.models;

import com.crt.excel.core.MetaColumn;

/**
 * @author UOrder
 */

public class ExportColumn extends MetaColumn {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7604590821703092974L;

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
	public ExportColumn(int index, String colShowName, String colFieldName, short colDataType) {
		super(index, colShowName, colFieldName, colDataType);
	}

	public ExportColumn(int index,String colShowName,String colFieldName,int colWidth,short colDataType,short colAlign){
		super(index,colShowName,colFieldName,colDataType);
		this.width=colWidth;
		this.align=colAlign;
	}
	
	public ExportColumn(int index,String colShowName,String colFieldName,int colWidth,short colDataType,short colAlign,boolean bHidden,int formatType){
		super(index,colShowName,colFieldName,colDataType);
		this.width=colWidth;
		this.align=colAlign;
		this.hidden=bHidden;
		this.formatType=formatType;
	}

	/**
	 * 列宽
	 */
	private Integer width = 150;

	public Integer getWidth() {
		if(this.width==null || this.width<=0) return 150;
		return this.width;
	}

	protected void setWidth(Integer width) {
		if (width != null && width > 0) {
			this.width = width;
		}
	}

	/**
	 * 列对齐方式
	 */
	private short align = AlignEnum.GENERAL.getIndex();

	public Short getAlign() {
		return this.align;
	}

	protected void setAlign(Short align) {
		if (align != null) {
			AlignEnum alignEnum=AlignEnum.getAlignEnum(align);
			this.align = alignEnum.getIndex();
		}
	}

	/**
	 * 列是否隐藏
	 */
	private boolean hidden = false;

	public Boolean getHidden() {
		return this.hidden;
	}

	protected void setHidden(Boolean hidden) {
		if (hidden != null) {
			this.hidden = hidden;
		}
	}
	/**
	 * 普通型("General")
	 */
	private int formatType=0;
	
	public int getFormatType(){
		return this.formatType;
	}
	
	
	/**
	 * Utility to identify built-in formats.  The following is a list of the formats as
	 * returned by this class.<p/>
	 *<p/>
	 *       0, "General"<br/>
	 *       1, "0"<br/>
	 *       2, "0.00"<br/>
	 *       3, "#,##0"<br/>
	 *       4, "#,##0.00"<br/>
	 *       5, "$#,##0_);($#,##0)"<br/>
	 *       6, "$#,##0_);[Red]($#,##0)"<br/>
	 *       7, "$#,##0.00);($#,##0.00)"<br/>
	 *       8, "$#,##0.00_);[Red]($#,##0.00)"<br/>
	 *       9, "0%"<br/>
	 *       0xa, "0.00%"<br/>
	 *       0xb, "0.00E+00"<br/>
	 *       0xc, "# ?/?"<br/>
	 *       0xd, "# ??/??"<br/>
	 *       0xe, "m/d/yy"<br/>
	 *       0xf, "d-mmm-yy"<br/>
	 *       0x10, "d-mmm"<br/>
	 *       0x11, "mmm-yy"<br/>
	 *       0x12, "h:mm AM/PM"<br/>
	 *       0x13, "h:mm:ss AM/PM"<br/>
	 *       0x14, "h:mm"<br/>
	 *       0x15, "h:mm:ss"<br/>
	 *       0x16, "m/d/yy h:mm"<br/>
	 *<p/>
	 *       // 0x17 - 0x24 reserved for international and undocumented
	 *       0x25, "#,##0_);(#,##0)"<br/>
	 *       0x26, "#,##0_);[Red](#,##0)"<br/>
	 *       0x27, "#,##0.00_);(#,##0.00)"<br/>
	 *       0x28, "#,##0.00_);[Red](#,##0.00)"<br/>
	 *       0x29, "_(*#,##0_);_(*(#,##0);_(* \"-\"_);_(@_)"<br/>
	 *       0x2a, "_($*#,##0_);_($*(#,##0);_($* \"-\"_);_(@_)"<br/>
	 *       0x2b, "_(*#,##0.00_);_(*(#,##0.00);_(*\"-\"??_);_(@_)"<br/>
	 *       0x2c, "_($*#,##0.00_);_($*(#,##0.00);_($*\"-\"??_);_(@_)"<br/>
	 *       0x2d, "mm:ss"<br/>
	 *       0x2e, "[h]:mm:ss"<br/>
	 *       0x2f, "mm:ss.0"<br/>
	 *       0x30, "##0.0E+0"<br/>
	 *       0x31, "@" - This is text format.<br/>
	 *       0x31  "text" - Alias for "@"<br/>
	 * <p/>
	 *
	 * @author Yegor Kozlov
	 *
	 * Modified 6/17/09 by Stanislav Shor - positive formats don't need starting '('
	 *
	 */
	
	public void setFormatType(int formatType){
		this.formatType=formatType;
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
}

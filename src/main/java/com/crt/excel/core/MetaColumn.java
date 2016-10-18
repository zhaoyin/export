/**
 * 
 */
package com.crt.excel.core;

import java.io.Serializable;

/**
 * @author UOrder
 *
 */
public class MetaColumn implements Serializable {
	/**
	 * @param index : 序号
	 * @param colShowName : 显示名称
	 * @param colFieldName : 列名
	 * @param colDataType : 数据类型
	 */
	public MetaColumn(int index, String colShowName, String colFieldName, short colDataType){
		if(index<=0){
			throw new RuntimeException("列序号必须从1开始!");
		}
		this.index = index;
		this.showName = colShowName;
		this.dataType = colDataType;
		this.fieldName = colFieldName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5871847369166005107L;
	
	/**
	 * 列序号
	 */
	private int index = 1;

	public Integer getIndex() {
		return this.index;
	}

	/**
	 * 列名
	 */
	private String showName = "undefined";

	public String getShowName() {
		return this.showName;
	}

	/**
	 * 列取数值
	 */
	private String fieldName = "";

	public String getFieldName() {
		return this.fieldName;
	}
	
	/**
	 * 列数据类型,默认String型
	 * 
	 * @详细信息 1:String 2:int 3:double 4:Date 5:short 6:boolean 7:bigDecimal
	 *       8:none 9:unknow
	 */
	private short dataType = TypeEnum.None.getIndex();

	public Short getDataType() {
		return this.dataType;
	}
	
	public void setDataType(Short type){
		if(type!=null && type!=0){
			this.dataType=type;
		}
	}

}

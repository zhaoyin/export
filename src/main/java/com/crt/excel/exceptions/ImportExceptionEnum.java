/**
 * 
 */
package com.crt.excel.exceptions;

/**
 * @author UOrder
 *
 */
public enum ImportExceptionEnum {
	Initialize(-1001,"初始化失败!")
	,ConvertData(-1002,"转换数据失败!")
	,ConvertJson(-1016,"转换json失败！")
	,Unknown(-1099,"未知异常!");
	
	private int index;
	private String caption;
	private ImportExceptionEnum(int index,String caption){
		this.index=index;
		this.caption=caption;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public String getCaption(){
		return this.caption;
	}
}

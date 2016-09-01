/**
 * 
 */
package com.crt.export.exception;

/**
 * @author UOrder
 *
 */
public enum ExceptionEnum {
	
	Initialize(-1001,"初始化失败!")
	,DrawTitle(-1002,"构建标题失败!")
	,DrawHead(-1003,"构建列头失败!")
	,DrawDataBlock(-1004,"构建数据块失败!")
	,DrawFoot(-1005,"构建表尾失败!")
	,ConvertColumn(-1007,"转换列失败!")
	,DrawBorder(-1008,"构建边框失败!")
	,NoColumn(-1009,"缺少列信息!")
	,CreateColumnStyle(-1010,"构建列样式失败!")
	,UnKnownBook(-1011,"未知的Excel对象!")
	,UnKnownSheet(-1012,"未知的Sheet对象!")
	,UnKnownBlock(-1013,"未知的数据区块!")
	,UnKnownRow(-1014,"未知的行信息!")
	,UnKnownExportDirectory(-1015,"无效的输出目录!")
	,Unknown(-1099,"未知异常!");
	
	private int index;
	private String caption;
	private ExceptionEnum(int index,String caption){
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

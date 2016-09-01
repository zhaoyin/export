/**
 * 
 */
package com.crt.export.exception;

/**
 * @author UOrder
 *
 */
public enum ExceptionEnum {
/*
	public static final int ERR_INITIALIZE = -1001;
	public static final int ERR_DRAWTITLE = -1002;
	public static final int ERR_DRAWHEAD = -1003;
	public static final int ERR_DRAWMAINDATABLOCK = -1004;
	public static final int ERR_DRAWFOOTER = -1005;
	public static final int ERR_WRITEFILE = -1006;
	public static final int ERR_COLUMNTYPE = -1007;
	public static final int ERR_DRAWBORDER = -1008;
	public static final int ERR_NOCOLUMNINFO = -1009;
	public static final int ERR_CREATECOLUMNSYTLE = -1010;
	
	*/
	
	Initialize(-1001,"初始化失败!")
	,DrawTitle(-1002,"构建标题失败!")
	,DrawHead(-1003,"构建列头失败!")
	,DrawDataBlock(-1004,"构建数据块失败!")
	,DrawFoot(-1005,"构建表尾失败!")
	,DrawBorder(-1008,"构建边框失败!")
	,CreateColumnStyle(-1010,"构建列样式失败!")
	,NoColumn(-1009,"缺少列信息!")
	,ConvertColumn(-1007,"转换列失败!")
	,UnKnownBook(-1011,"未知的Excel对象!")
	,UnKnownSheet(-1012,"未知的Sheet对象!")
	,UnKnownBlock(-1013,"未知的数据区块!")
	,UnKnownRow(-1012,"未知的行信息!")
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

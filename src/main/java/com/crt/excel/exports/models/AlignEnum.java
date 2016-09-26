/**
 * 
 */
package com.crt.excel.exports.models;

/**
 * @author UOrder
 *
 */
public enum AlignEnum {
	/*
	 * 
	protected static final short LEFT = 1;
	protected static final short CENTER = 2;
	protected static final short RIGHT = 3;
	 */
	
	Left((short)1,"左")
	,Center((short)2,"中")
	,Right((short)3,"右");
	
	private short index;
	private String capton;
	private AlignEnum(short index,String caption){
		this.index=index;
		this.capton=caption;
	}
	
	public short getIndex(){
		return this.index;
	}
	
	public String getCaption(){
		return this.capton;
	}
}

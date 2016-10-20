/**
 * 
 */
package com.crt.excel.models;

/**
 * @author UOrder
 *
 */
public enum AlignEnum {
	GENERAL((short) 0, "通用"),
	LEFT((short) 1, "左对齐"),
	CENTER((short) 2, "居中对齐"),
	RIGHT((short) 3, "右对齐"),
	FILL((short) 4,"对齐文本"),
	JUSTIFY((short) 5, "两端对齐"),
	CENTER_SELECTION((short) 6,"选区居中");

	private short index;
	private String caption;

	private AlignEnum(short index, String caption) {
		this.index = index;
		this.caption = caption;
	}

	public short getIndex() {
		return this.index;
	}

	public String caption() {
		return this.caption;
	}

	public static AlignEnum getAlignEnum(short index) {
		switch (index) {
		case 0:
			return AlignEnum.GENERAL;
		case 1:
			return AlignEnum.LEFT;
		case 2:
			return AlignEnum.CENTER;
		case 3:
			return AlignEnum.RIGHT;
		case 4:
			return AlignEnum.FILL;
		case 5:
			return AlignEnum.JUSTIFY;
		case 6:
			return AlignEnum.CENTER_SELECTION;
		default:
			return AlignEnum.GENERAL;
		}
	}
}

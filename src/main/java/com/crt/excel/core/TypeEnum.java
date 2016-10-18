/**
 * 
 */
package com.crt.excel.core;

/**
 * @author UOrder
 *
 */
public enum TypeEnum {
	String((short) 1, "字符型"),
	Int((short) 2, "整型"),
	Double((short) 3, "双精度"),
	Date((short) 4, "日期"),
	Short((short) 5,"短整型"),
	Boolean((short) 6, "布尔型"),
	BigDecimal((short) 7,"货币"),
	None((short) 8, "空"),
    Unknown((short) 9, "未知"),
    Percent((short) 10, "百分比");

	private short index;
	private String caption;

	private TypeEnum(short index, String caption) {
		this.index = index;
		this.caption = caption;
	}

	public short getIndex() {
		return this.index;
	}

	public String caption() {
		return this.caption;
	}

	public static TypeEnum getTypeEnum(short index) {
		switch (index) {
		case 1:
			return TypeEnum.String;
		case 2:
			return TypeEnum.Int;
		case 3:
			return TypeEnum.Double;
		case 4:
			return TypeEnum.Date;
		case 5:
			return TypeEnum.Short;
		case 6:
			return TypeEnum.Boolean;
		case 7:
			return TypeEnum.BigDecimal;
		case 8:
			return TypeEnum.None;
		case 9:
			return TypeEnum.Unknown;
		case 10:
			return TypeEnum.Percent;
		default:
			return TypeEnum.Unknown;
		}
	}
}

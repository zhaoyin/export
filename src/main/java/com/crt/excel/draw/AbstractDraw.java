/**
 * 
 */
package com.crt.excel.draw;

import com.crt.excel.exceptions.ExportException;

/**
 * @author UOrder
 *
 */
abstract class AbstractDraw implements IDrawer {

	/*
	 * 2016年7月18日 下午3:09:18
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	abstract boolean hasNext();

	/*
	 * 2016年7月18日 下午3:09:18
	 * 
	 * @see java.util.Iterator#next()
	 */
	abstract IDrawer next();

	/*
	 * 2016年8月31日 下午4:08:45
	 * 
	 * @see com.crt.export.draw.IDrawer#draw(com.crt.export.draw.DrawContext)
	 */
	public abstract void draw(DrawContext context) throws ExportException;

	public void build(DrawContext context) throws ExportException {
		this.draw(context);
		if (hasNext()) {
			next().build(context);
		}
	}
	
	/*
	putFormat(m, 0, "General");
	putFormat(m, 1, "0");
	putFormat(m, 2, "0.00");
	putFormat(m, 3, "#,##0");
	putFormat(m, 4, "#,##0.00");
	putFormat(m, 5, "\"$\"#,##0_);(\"$\"#,##0)");
	putFormat(m, 6, "\"$\"#,##0_);[Red](\"$\"#,##0)");
	putFormat(m, 7, "\"$\"#,##0.00_);(\"$\"#,##0.00)");
	putFormat(m, 8, "\"$\"#,##0.00_);[Red](\"$\"#,##0.00)");
	putFormat(m, 9, "0%");
	putFormat(m, 0xa, "0.00%");
	putFormat(m, 0xb, "0.00E+00");
	putFormat(m, 0xc, "# ?/?");
	putFormat(m, 0xd, "# ??/??");
	putFormat(m, 0xe, "m/d/yy");
	putFormat(m, 0xf, "d-mmm-yy");
	putFormat(m, 0x10, "d-mmm");
	putFormat(m, 0x11, "mmm-yy");
	putFormat(m, 0x12, "h:mm AM/PM");
	putFormat(m, 0x13, "h:mm:ss AM/PM");
	putFormat(m, 0x14, "h:mm");
	putFormat(m, 0x15, "h:mm:ss");
	putFormat(m, 0x16, "m/d/yy h:mm");

	// 0x17 - 0x24 reserved for international and undocumented
	for (int i=0x17; i<=0x24; i++) {
		// TODO - one junit relies on these values which seems incorrect
		putFormat(m, i, "reserved-0x" + Integer.toHexString(i));
	}

	putFormat(m, 0x25, "#,##0_);(#,##0)");
	putFormat(m, 0x26, "#,##0_);[Red](#,##0)");
	putFormat(m, 0x27, "#,##0.00_);(#,##0.00)");
	putFormat(m, 0x28, "#,##0.00_);[Red](#,##0.00)");
	putFormat(m, 0x29, "_(\"$\"* #,##0_);_(\"$\"* (#,##0);_(\"$\"* \"-\"_);_(@_)");
	putFormat(m, 0x2a, "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)");
	putFormat(m, 0x2b, "_(\"$\"* #,##0.00_);_(\"$\"* (#,##0.00);_(\"$\"* \"-\"??_);_(@_)");
	putFormat(m, 0x2c, "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)");
	putFormat(m, 0x2d, "mm:ss");
	putFormat(m, 0x2e, "[h]:mm:ss");
	putFormat(m, 0x2f, "mm:ss.0");
	putFormat(m, 0x30, "##0.0E+0");
	putFormat(m, 0x31, "@");
	*/
	
	/**
	 * 列数字型格式("#,##0.00")
	 */
	private short numericFomat = 4;

	public Short getNumericFormat() {
		return this.numericFomat;
	}

	/**
	 * 列普通型("General")
	 */
	private short generalFomat = 0;

	public Short getGeneralFormat() {
		return this.generalFomat;
	}

	/**
	 * 列日期型("d-mmm-yy")
	 */
	private short dateFomat = 0xf;

	public Short getDateFormat() {
		return this.dateFomat;
	}

	/**
	 * 百分比0.00%
	 */
	private String percentFomat = "0.00%";

	public String getPercentFormat() {
		return this.percentFomat;
	}

	/**
	 * 列头文字格式("General")
	 */
	private short headFomat = 0;

	public Short getHeadFormat() {
		return this.headFomat;
	}
}

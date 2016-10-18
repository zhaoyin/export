/**
 * 
 */
package com.crt.excel.draw;

import com.crt.excel.exceptions.ExportException;

/**
 * @author UOrder
 *
 */
class DrawFoot extends AbstractDraw {

	/*
	 * 2016年8月31日 下午3:52:25
	 * 
	 * @see com.crt.export.draw.IDraw#draw(com.crt.export.draw.DrawContext)
	 */
	public void draw(DrawContext context) throws ExportException {
	}

	/*
	 * 2016年8月31日 下午4:22:02
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		return true;
	}

	/*
	 * 2016年8月31日 下午4:22:02
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		return new DrawBorder();
	}

}

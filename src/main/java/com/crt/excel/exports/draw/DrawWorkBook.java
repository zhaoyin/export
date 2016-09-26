/**
 * 
 */
package com.crt.excel.exports.draw;

import com.crt.excel.exports.exception.ExportException;

/**
 * @author UOrder
 *
 */
public class DrawWorkBook extends AbstractDraw {

	/*
	 * 2016年8月31日 下午5:36:15
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 2016年8月31日 下午5:36:15
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		// TODO Auto-generated method stub
		return new DrawTitle();
	}

	/*
	 * 2016年8月31日 下午5:36:15
	 * 
	 * @see
	 * com.crt.export.draw.AbstractDraw#draw(com.crt.export.draw.DrawContext)
	 */
	@Override
	public void draw(DrawContext context) throws ExportException {
	}

}

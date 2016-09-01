/**
 * 
 */
package com.crt.export.draw;

import com.crt.export.exception.ExportException;

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
}

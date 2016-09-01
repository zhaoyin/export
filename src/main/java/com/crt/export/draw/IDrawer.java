/**
 * 
 */
package com.crt.export.draw;

import com.crt.export.exception.ExportException;

/**
 * @author UOrder
 *
 */
interface IDrawer {
	void draw(DrawContext context) throws ExportException;

	void build(DrawContext context) throws ExportException;
}

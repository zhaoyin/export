/**
 * 
 */
package com.crt.excel.draw;

import com.crt.excel.exceptions.ExportException;

/**
 * @author UOrder
 *
 */
interface IDrawer {
	
	void draw(DrawContext context) throws ExportException;

	void build(DrawContext context) throws ExportException;
}

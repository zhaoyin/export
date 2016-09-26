/**
 * 
 */
package com.crt.excel.exports.draw;

import com.crt.excel.exports.exception.ExportException;

/**
 * @author UOrder
 *
 */
interface IDrawer {
	void draw(DrawContext context) throws ExportException;

	void build(DrawContext context) throws ExportException;
}

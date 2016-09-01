/**
 * 
 */
package com.crt.export.draw;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import com.crt.export.exception.ExceptionEnum;
import com.crt.export.exception.ExportException;
import com.crt.export.models.Column;

/**
 * @author UOrder
 *
 */
class DrawColumn extends AbstractDraw {

	/*
	 * 2016年8月31日 下午3:20:54
	 * 
	 * @see com.crt.export.draw.IDraw#draw(com.crt.export.draw.DrawContext)
	 */
	public void draw(DrawContext context) throws ExportException {
		try {
			for (Column column : context.getColumns()) {
				HSSFCellStyle cs = context.getWorkBook().createCellStyle();
				cs.setFont(context.getDataBlock().getColumnFont(context.getWorkBook()));
				column.setCellStyle(cs);
			}
		} catch (Exception e) {
			throw new ExportException(ExceptionEnum.CreateColumnStyle);
		}
	}

	/*
	 * 2016年8月31日 下午4:19:56
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		return true;
	}

	/*
	 * 2016年8月31日 下午4:19:56
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		return new DrawDataBlock();
	}

}

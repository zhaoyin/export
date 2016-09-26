/**
 * 
 */
package com.crt.excel.exports.draw;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;

import com.crt.excel.exports.exception.ExceptionEnum;
import com.crt.excel.exports.exception.ExportException;
import com.crt.excel.exports.models.Column;

/**
 * @author UOrder
 *
 */
class DrawHead extends AbstractDraw {

	/*
	 * 2016年8月31日 下午3:07:10
	 * 
	 * @see com.crt.export.draw.IDraw#draw(com.crt.export.draw.DrawContext)
	 */
	public void draw(DrawContext context) throws ExportException {
		try {
			HSSFRow row = context.addRow();
			boolean hasSetHeight = false;
			boolean hasRowNo = context.getDataBlock().getHasRowNo();
			if (hasRowNo) {
				HSSFCell cell = row.createCell(0);
				HSSFCellStyle cs = context.getWorkBook().createCellStyle();
				cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cs.setHidden(false);
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				cs.setFont(context.getDataBlock().getColumnFont(context.getWorkBook()));
				cell.setCellValue(new HSSFRichTextString("序号"));
				cell.setCellStyle(cs);
			}
			for (Column column : context.getColumns()) {
				HSSFCell cell;
				if (hasRowNo) {
					cell = row.createCell(column.getIndex() + 1);
					context.getCurrentSheet().setColumnWidth(column.getIndex() + 1, column.getWidth() * context.HRATIO);
				} else {
					cell = row.createCell(column.getIndex());
					context.getCurrentSheet().setColumnWidth(column.getIndex(), column.getWidth() * context.HRATIO);
				}

				cell.setCellValue(new HSSFRichTextString(column.getShowName().replaceAll("<br>", "")));
				/** 这里额外加入对列名的处理，去掉列中的br标签 */

				HSSFCellStyle cs = context.getWorkBook().createCellStyle();
				cs.setAlignment(column.getHeadAlign());
				cs.setDataFormat(column.getHeadFormat());
				cs.setHidden(column.getHidden());
				cs.setFont(column.getHeadFont(context.getWorkBook()));
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				if (!hasSetHeight) {
					row.setHeight((short) (column.getHeadRowHeight() * context.VRATIO));
					hasSetHeight = true;
				}
				cell.setCellStyle(cs);
			}
		} catch (Exception e) {
			throw new ExportException(ExceptionEnum.DrawHead);
		}
	}

	/*
	 * 2016年8月31日 下午4:18:30
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 2016年8月31日 下午4:18:30
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		return new DrawColumn();
	}

}

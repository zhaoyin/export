/**
 * 
 */
package com.crt.export.draw;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.crt.export.exception.ExceptionEnum;
import com.crt.export.exception.ExportException;

/**
 * @author UOrder
 *
 */
class DrawTitle extends AbstractDraw {

	/*
	 * 2016年8月31日 下午2:25:24
	 * 
	 * @see com.crt.export.draw.IDraw#draw(org.apache.poi.hssf.usermodel.
	 * HSSFWorkbook)
	 */
	public void draw(DrawContext context) throws ExportException {
		//未设置标题则不画标题
		if (context.getTitle() == null || context.getTitle().trim().length() <= 0) {
			return;
		}
		try {
			HSSFRow row = context.addRow();
			// 创建风格
			HSSFCellStyle cellStyle = context.getWorkBook().createCellStyle();

			// 设置样式,居中
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 设置样式,居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 设置字体
			HSSFFont font = context.getWorkBook().createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("宋体");
			font.setBoldweight(org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_BOLD);

			// 关联到style
			cellStyle.setFont(font);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

			// 创建单元格并设置风格
			for (int i = 0; i < context.getColumnCount(); i++) {
				HSSFCell curCell = row.createCell(i);
				curCell.setCellStyle(cellStyle);
			}

			// 写入信息
			row.getCell(0).setCellValue(new HSSFRichTextString(context.getTitle()));

			// 合并
			context.getCurrentSheet().addMergedRegion(
					new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, context.getColumnCount()));
		} catch (Exception e) {
			throw new ExportException(ExceptionEnum.DrawTitle);
		}
	}

	/*
	 * 2016年8月31日 下午4:15:28
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 2016年8月31日 下午4:15:28
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		return new DrawHead();
	}

}

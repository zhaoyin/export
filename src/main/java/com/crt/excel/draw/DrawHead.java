/**
 * 
 */
package com.crt.excel.draw;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.crt.excel.exceptions.ExportExceptionEnum;
import com.crt.excel.models.ExportColumn;
import com.crt.excel.exceptions.ExportException;

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
			
			if (context.getHasRowNo()) {
				setRowNoCell(context.getWorkBook(),row);
			}
			row.setHeight((short) (context.getHeadRowHeight() * context.VRATIO));
			for (ExportColumn exportColumn : context.getColumns()) {
				int index=context.getHasRowNo()?exportColumn.getIndex():exportColumn.getIndex()-1;
				HSSFCell cell = row.createCell(index);
				context.getCurrentSheet().setColumnWidth(index, exportColumn.getWidth() * context.HRATIO);

				cell.setCellValue(new HSSFRichTextString(exportColumn.getShowName().replaceAll("<br>", "")));
				/** 这里额外加入对列名的处理，去掉列中的br标签 */

				HSSFCellStyle cs = getHeadColumnCellStyle(context.getWorkBook(),exportColumn);
				
				cell.setCellStyle(cs);
			}
		} catch (Exception e) {
			throw new ExportException(ExportExceptionEnum.DrawHead);
		}
	}
	/**
	 * 
	 * @param workbook
	 * @param column
	 * @return
	 */
	private HSSFCellStyle getHeadColumnCellStyle(HSSFWorkbook workbook,ExportColumn column){
		HSSFCellStyle cs = workbook.createCellStyle();
		cs.setAlignment(org.apache.poi.hssf.usermodel.HSSFCellStyle.ALIGN_GENERAL);  //
		cs.setDataFormat(getHeadFormat());
		cs.setHidden(column.getHidden());
		cs.setFont(getHeadFont(workbook));
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		return cs;
	}
	
	/**
	 * 设置行号列样式
	 * @return
	 */
	private HSSFCell setRowNoCell(HSSFWorkbook workbook,HSSFRow row){
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle cs = workbook.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setHidden(false);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cs.setFont(getHeadFont(workbook));
		cell.setCellValue(new HSSFRichTextString("序号"));
		cell.setCellStyle(cs);
		return cell;
	}
	
	private HSSFFont font;
	/**
	 * 列头字体
	 */
	public HSSFFont getHeadFont(HSSFWorkbook workbook) {
		if (this.font == null) {
			this.font = workbook.createFont();
			this.font.setFontHeightInPoints((short) 12);
			this.font.setFontName("宋体");
			this.font.setBoldweight(org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_BOLD);
			this.font.setItalic(false);
			this.font.setStrikeout(false);
		}
		return this.font;
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
		return new DrawDataBlock();
	}

}

/**
 * 
 */
package com.crt.export.draw;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.crt.export.exception.ExceptionEnum;
import com.crt.export.exception.ExportException;

/**
 * @author UOrder
 *
 */
class DrawBorder extends AbstractDraw {

	/*
	 * 2016年8月31日 下午3:52:55
	 * 
	 * @see com.crt.export.draw.IDraw#draw(com.crt.export.draw.DrawContext)
	 */
	public void draw(DrawContext context) throws ExportException {
		try {
			int sheetcount = context.getWorkBook().getNumberOfSheets();
			for (int sheet = 0; sheet < sheetcount; sheet++) {
				HSSFSheet currentSheet = context.getWorkBook().getSheetAt(sheet);
				int firstRow = currentSheet.getFirstRowNum();
				int lastRow = currentSheet.getLastRowNum();
				for (int row = firstRow; row < lastRow; row++) {
					if (currentSheet.getRow(row) == null) {
						continue;
					}
					HSSFRow currentRow = currentSheet.getRow(row);
					for (int cellIndex = 0; cellIndex < context.getColumnCount(); cellIndex++) {
						HSSFCell currentCell = currentRow.getCell(cellIndex);
						if (null == currentCell) {
							currentCell = currentRow.createCell(cellIndex);
						}
						if (null == currentCell.getCellStyle()) {
							HSSFCellStyle cs = context.getWorkBook().createCellStyle();
							cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
							cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
							cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
							cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
							currentCell.setCellStyle(cs);
							continue;
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ExportException(ExceptionEnum.DrawBorder);
		}
	}

	/*
	 * 2016年8月31日 下午4:22:43
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * 2016年8月31日 下午4:22:43
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		// TODO Auto-generated method stub
		return null;
	}

}

/**
 * 
 */
package com.crt.excel.exports.draw;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.crt.excel.exports.exception.ExceptionEnum;
import com.crt.excel.exports.exception.ExportException;
import com.crt.excel.exports.models.Column;
import com.crt.excel.exports.models.TypeEnum;

/**
 * @author UOrder
 *
 */
class DrawDataBlock extends AbstractDraw {

	/*
	 * 2016年8月31日 下午3:24:35
	 * 
	 * @see com.crt.export.draw.IDraw#draw(com.crt.export.draw.DrawContext)
	 */
	public void draw(DrawContext context) throws ExportException {
		try {
			int columnNum = 1;
			// 序列号
			HSSFCellStyle cs = context.getWorkBook().createCellStyle();
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cs.setHidden(false);
			cs.setFont(context.getDataBlock().getColumnFont(context.getWorkBook()));
			boolean hasRowNo = context.getDataBlock().getHasRowNo();
			// 根据列索引
			for (Map<String, Object> itRow : context.getData()) {
				HSSFRow row = context.addRow();

				if (hasRowNo) {
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(columnNum++);
					cell.setCellStyle(cs);
				}

				row.setHeight((short) (context.getDataBlock().getDataRowHeight() * context.VRATIO));

				for (Column column : context.getColumns()) {
					String key = column.getFieldName();
					if (itRow.containsKey(key)) {
						setDataCell(context, row, column, itRow.get(key));
					}
				}
			}
			if (context.getDataBlock().getHasSumRow()) {
				context.addSumRow();
			}
		} catch (Exception e) {
			throw new ExportException(ExceptionEnum.DrawDataBlock);
		}
	}

	/**
	 * 设数据块单元值
	 * 
	 * @param row
	 *            HSSFRow 当前操作行
	 * @param obj
	 *            数据
	 * @param info
	 *            ColumnInfo 列信息
	 */
	private void setDataCell(DrawContext context, HSSFRow row, Column column, Object rowData) {
		HSSFCell cell;
		if (context.getDataBlock().getHasRowNo()) {
			cell = row.createCell(column.getIndex() + 1);
		} else {
			cell = row.createCell(column.getIndex());
		}
		HSSFCellStyle cs = column.getCellStyle();
		cs.setAlignment(column.getAlign());
		cs.setHidden(column.getHidden());
		// cs.setFont(this.DATABLOCKINFO.getColumnFont(WORKBOOK));

		if (column.getDataType() == TypeEnum.None.getIndex() || column.getDataType() == TypeEnum.Unknown.getIndex()) {
			column.setDataType(context.getColumnType(rowData, column));
		}
		String sVal = "";
		Date dtVal = null;
		double dVal = 0.0;
		int iVal = 0;
		boolean bVal = false;
		short shVal = (short) 0;

		switch (TypeEnum.getTypeEnum(column.getDataType())) {
		case String:
			sVal = (rowData != null ? rowData.toString() : "");
			sVal = sVal.replaceAll("&nbsp;", " ");
			cell.setCellValue(new HSSFRichTextString(sVal));
			break;
		case Boolean:
			bVal = (rowData != null ? Boolean.valueOf(rowData.toString()).booleanValue() : false);
			cell.setCellValue(bVal);
			cs.setDataFormat(column.getGeneralFormat());
			break;
		case Date:
			dtVal = ((rowData != null && !rowData.equals("")) ? (Date) rowData : null);
			if (dtVal != null) {
				cell.setCellValue(dtVal);
				cs.setDataFormat(column.getDateFormat());
			} else {
				cell.setCellValue(new HSSFRichTextString(""));
			}
			break;
		case Double:
			dVal = (rowData != null ? Double.parseDouble(rowData.toString()) : 0.0);
			cell.setCellValue(dVal);
			cs.setDataFormat(column.getNumericFormat());
			column.setSumValue(dVal);
			break;
		case BigDecimal:
			dVal = (rowData != null ? Double.parseDouble(((BigDecimal) (rowData)).toString()) : (double) 0.0);
			cell.setCellValue(dVal);
			cs.setDataFormat(column.getNumericFormat());
			column.setSumValue(dVal);
			break;
		case Percent:
			dVal = (rowData != null ? Double.parseDouble(rowData.toString()) : (double) 0.0);
			cell.setCellValue(dVal / 100);
			cs.setDataFormat(HSSFDataFormat.getBuiltinFormat(column.getPercentFormat()));
			break;
		case Int:
			iVal = (rowData != null ? Integer.parseInt(rowData.toString()) : 0);
			cell.setCellValue(iVal);
			cs.setDataFormat(column.getGeneralFormat());
			column.setSumValue((double) iVal);
			break;
		case Short:
			shVal = (rowData != null ? Short.parseShort(rowData.toString()) : (short) 0);
			cell.setCellValue(shVal);
			cs.setDataFormat(column.getGeneralFormat());
			column.setSumValue((double) shVal);
			break;
		case None:
			sVal = (rowData != null ? rowData.toString() : "");
			cell.setCellValue(new HSSFRichTextString(sVal));
			cs.setDataFormat(column.getGeneralFormat());
			break;
		case Unknown:
			sVal = (rowData != null ? rowData.toString() : "");
			cell.setCellValue(new HSSFRichTextString(sVal));
			cs.setDataFormat(column.getGeneralFormat());
			break;
		}
		cell.setCellStyle(cs);
	}

	/*
	 * 2016年8月31日 下午4:21:11
	 * 
	 * @see com.crt.export.draw.AbstractDraw#hasNext()
	 */
	@Override
	boolean hasNext() {
		return true;
	}

	/*
	 * 2016年8月31日 下午4:21:11
	 * 
	 * @see com.crt.export.draw.AbstractDraw#next()
	 */
	@Override
	IDrawer next() {
		return new DrawFoot();
	}

}

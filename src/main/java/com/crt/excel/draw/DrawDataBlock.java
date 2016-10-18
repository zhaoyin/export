/**
 * 
 */
package com.crt.excel.draw;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.crt.excel.core.TypeEnum;
import com.crt.excel.exceptions.ExportException;
import com.crt.excel.exceptions.ExportExceptionEnum;
import com.crt.excel.models.ExportColumn;

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
			cs.setFont(getColumnFont(context.getWorkBook()));
			boolean hasRowNo = context.getHasRowNo();
			// 根据列索引
			for (Map<String, Object> itRow : context.getData()) {
				HSSFRow row = context.addRow();

				if (hasRowNo) {
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(columnNum++);
					cell.setCellStyle(cs);
				}

				row.setHeight((short) (context.getDataRowHeight() * context.VRATIO));

				for (ExportColumn exportColumn : context.getColumns()) {
					String key = exportColumn.getFieldName();
					if (itRow.containsKey(key)) {
						setDataCell(context, row, exportColumn, itRow.get(key));
					}
				}
			}
			if (context.getHasSumRow()) {
				this.addSumRow(context);
			}
		} catch (Exception e) {
			throw new ExportException(ExportExceptionEnum.DrawDataBlock);
		}
	}

	/**
	 * 增加合计行
	 */
	private HSSFRow addSumRow(DrawContext context) {
		HSSFRow row = context.addRow();
		HSSFCell cell = row.createCell(0);
		HSSFCellStyle cs = getDataCellStyle(context.getWorkBook());
		cell.setCellValue(new HSSFRichTextString("合计："));
		cell.setCellStyle(cs);

//		boolean hasRowNo = context.getHasRowNo();
		for (ExportColumn exportColumn : context.getColumns()) {
			int index=context.getHasRowNo()?exportColumn.getIndex():exportColumn.getIndex()-1;
			cell= row.createCell(index);

			if (exportColumn.getDataType() == TypeEnum.Double.getIndex()
					|| exportColumn.getDataType() == TypeEnum.BigDecimal.getIndex()
					|| exportColumn.getDataType() == TypeEnum.Int.getIndex()
					|| exportColumn.getDataType() == TypeEnum.Short.getIndex()) {
				cs = context.getWorkBook().createCellStyle();
				cs.setAlignment(exportColumn.getAlign());
				cs.setHidden(false);
				cs.setFont(this.getColumnFont(context.getWorkBook()));
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

				if (exportColumn.getDataType() == TypeEnum.Int.getIndex()) {
					cs.setDataFormat(getGeneralFormat());
				} else {
					cs.setDataFormat(getNumericFormat());
				}
				cell.setCellValue(exportColumn.getSumValue());
			}
			cell.setCellStyle(cs);
		}
		return row;
	}
	
	private HSSFCellStyle getDataCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle cs = workbook.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cs.setHidden(false);
		HSSFFont font = workbook.createFont();
		font.setStrikeout(false);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cs.setFont(font);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		return cs;
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
	private void setDataCell(DrawContext context, HSSFRow row, ExportColumn exportColumn, Object rowData) {
		int index=context.getHasRowNo()?exportColumn.getIndex():exportColumn.getIndex()-1;
		HSSFCell cell = row.createCell(index);
		HSSFCellStyle cs = context.getWorkBook().createCellStyle();
		cs.setAlignment(exportColumn.getAlign());
		cs.setHidden(exportColumn.getHidden());
		// cs.setFont(this.DATABLOCKINFO.getColumnFont(WORKBOOK));

		if (exportColumn.getDataType() == TypeEnum.None.getIndex() || exportColumn.getDataType() == TypeEnum.Unknown.getIndex()) {
			exportColumn.setDataType(getColumnType(rowData, exportColumn));
		}
		String sVal = "";
		Date dtVal = null;
		double dVal = 0.0;
		int iVal = 0;
		boolean bVal = false;
		short shVal = (short) 0;

		switch (TypeEnum.getTypeEnum(exportColumn.getDataType())) {
		case String:
			sVal = (rowData != null ? rowData.toString() : "");
			sVal = sVal.replaceAll("&nbsp;", " ");
			cell.setCellValue(new HSSFRichTextString(sVal));
			break;
		case Boolean:
			bVal = (rowData != null ? Boolean.valueOf(rowData.toString()).booleanValue() : false);
			cell.setCellValue(bVal);
			cs.setDataFormat(getGeneralFormat());
			break;
		case Date:
			dtVal = ((rowData != null && !rowData.equals("")) ? (Date) rowData : null);
			if (dtVal != null) {
				cell.setCellValue(dtVal);
				cs.setDataFormat(getDateFormat());
			} else {
				cell.setCellValue(new HSSFRichTextString(""));
			}
			break;
		case Double:
			dVal = (rowData != null ? Double.parseDouble(rowData.toString()) : 0.0);
			cell.setCellValue(dVal);
			cs.setDataFormat(getNumericFormat());
			exportColumn.setSumValue(dVal);
			break;
		case BigDecimal:
			dVal = (rowData != null ? Double.parseDouble(((BigDecimal) (rowData)).toString()) : (double) 0.0);
			cell.setCellValue(dVal);
			cs.setDataFormat(getNumericFormat());
			exportColumn.setSumValue(dVal);
			break;
		case Percent:
			dVal = (rowData != null ? Double.parseDouble(rowData.toString()) : (double) 0.0);
			cell.setCellValue(dVal / 100);
			cs.setDataFormat(HSSFDataFormat.getBuiltinFormat(getPercentFormat()));
			break;
		case Int:
			iVal = (rowData != null ? Integer.parseInt(rowData.toString()) : 0);
			cell.setCellValue(iVal);
			cs.setDataFormat(getGeneralFormat());
			exportColumn.setSumValue((double) iVal);
			break;
		case Short:
			shVal = (rowData != null ? Short.parseShort(rowData.toString()) : (short) 0);
			cell.setCellValue(shVal);
			cs.setDataFormat(getGeneralFormat());
			exportColumn.setSumValue((double) shVal);
			break;
		case None:
			sVal = (rowData != null ? rowData.toString() : "");
			cell.setCellValue(new HSSFRichTextString(sVal));
			cs.setDataFormat(getGeneralFormat());
			break;
		case Unknown:
			sVal = (rowData != null ? rowData.toString() : "");
			cell.setCellValue(new HSSFRichTextString(sVal));
			cs.setDataFormat(getGeneralFormat());
			break;
		}
		cell.setCellStyle(cs);
	}

	private HSSFFont Font;
	/**
	 * 数据块字体
	 */
	public HSSFFont getColumnFont(HSSFWorkbook wb) {
		if (Font == null) {
			Font = wb.createFont();
			Font.setFontHeightInPoints((short) 10);
			Font.setBoldweight(org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_NORMAL);
			Font.setFontName("宋体");
			Font.setItalic(false);
			Font.setStrikeout(false);
		}
		return Font;
	}
	
	/**
	 * 设置列类型
	 * 
	 * @return
	 */
	private short getColumnType(Object obj, ExportColumn exportColumn) {
		if (obj == null) {
			return TypeEnum.None.getIndex();
		}
		if (obj instanceof java.lang.Integer) {
			return TypeEnum.Int.getIndex();
		}
		if (obj.getClass().equals(java.math.BigInteger.class)) {
			return TypeEnum.Int.getIndex();
		}
		// 百分比-double
		else if ((obj instanceof java.lang.Double) && (exportColumn.getShowName().indexOf("%") != -1)
				&& (exportColumn.getShowName().indexOf("%") == exportColumn.getShowName().lastIndexOf("%"))) {
			return TypeEnum.Percent.getIndex();
		}
		// 百分比-BigDecimal
		else if ((obj instanceof java.math.BigDecimal) && (exportColumn.getShowName().indexOf("%") != -1)
				&& (exportColumn.getShowName().indexOf("%") == exportColumn.getShowName().lastIndexOf("%"))) {
			return TypeEnum.Percent.getIndex();
		} else if (obj instanceof java.lang.Double) {
			return TypeEnum.Double.getIndex();
		} else if (obj instanceof java.math.BigDecimal) {
			return TypeEnum.BigDecimal.getIndex();
		} else if (obj instanceof java.util.Date) {
			return TypeEnum.Date.getIndex();
		} else if (obj instanceof java.lang.Boolean) {
			return TypeEnum.Boolean.getIndex();
		} else if (obj instanceof java.lang.String) {
			return TypeEnum.String.getIndex();
		} else if (obj instanceof java.lang.Short) {
			return TypeEnum.Short.getIndex();
		} else {
			return TypeEnum.Unknown.getIndex();
		}

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

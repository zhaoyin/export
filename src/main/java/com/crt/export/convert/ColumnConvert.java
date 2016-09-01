package com.crt.export.convert;

import java.util.ArrayList;

import com.crt.export.models.Column;

/**
 *
 * @author UOrder
 *
 */
public class ColumnConvert implements IConverter<String, ArrayList<Column>> {

	public ArrayList<Column> convert(String source) {
		return ColumnConvert.parseCols(source);
	}

	public static ArrayList<Column> parseCols(String json) {
		if (json == null || json.equals("")) {
			return null;
		}

		ArrayList<Column> list = new ArrayList<Column>();
//
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject cols = array.getJSONObject(i);
//
//			int index = cols.getInt("index");
//			String colName = cols.getString("colName");
//			String colData = cols.getString("colData");
//
//			String colWidths = cols.getString("colWidth");
//			colWidths = colWidths.replace("px", "");
//			if (colWidths.indexOf(".") != -1) {
//				colWidths = colWidths.substring(0, colWidths.indexOf("."));
//			}
//			int colWidth = Integer.valueOf(colWidths);
//			String colDataType = cols.getString("colDataType");
//			short dtype = Column.TYPE_STRING;
//			if (colDataType.equals("string")) {
//				dtype = Column.TYPE_STRING;
//			}
//			if (colDataType.equals("number")) {
//				dtype = Column.TYPE_INT;
//			}
//			if (colDataType.equals("double")) {
//				dtype = Column.TYPE_DOUBLE;
//			}
//			if (colDataType.equals("boolean")) {
//				dtype = Column.TYPE_BOOLEAN;
//			}
//			if (colDataType.equals("percent")) {
//				dtype = Column.TYPE_PERCENT;
//			}
//			String colAlign = cols.getString("colAlign");
//			short align = Column.LEFT;
//			if (colAlign.equals("CENTER")) {
//				align = Column.CENTER;
//			}
//			if (colAlign.equals("LEFT")) {
//				align = Column.LEFT;
//			}
//			if (colAlign.equals("RIGHT")) {
//				align = Column.RIGHT;
//			}
//			boolean colHidden = cols.getBoolean("colHidden");
//
//			Column columnInfo = new Column(index, colName, colData, colWidth, dtype, align, colHidden);
//
//			list.add(columnInfo);
//		}

		return list;
	}
}

/**
 * 
 */
package com.crt.export.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.crt.export.models.Column;

/**
 * @author UOrder
 *
 */
public class ExportConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7228802994814293292L;

	public ExportConfig(List<Column> columns, List<Map<String, Object>> data, String exportDirectory) {
		this.columns = columns;
		this.data = data;
		this.exportDirectory = exportDirectory;
	}

	private String exportDirectory;

	public String getExportDirectory() {
		return this.exportDirectory;
	}

	private List<Column> columns;

	public List<Column> getColumns() {
		return this.columns;
	}

	private List<Map<String, Object>> data;

	public List<Map<String, Object>> getData() {
		return this.data;
	}

	private String title;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		if (title != null) {
			this.title = title;
		}
	}

	private boolean hasSumRow = false;

	public Boolean getHasSumRow() {
		return this.hasSumRow;
	}

	public void setHasSumRow(Boolean hasSumRow) {
		if (hasSumRow != null) {
			this.hasSumRow = hasSumRow;
		}
	}

	/**
	 * 是否添加列序号，默认为false
	 */
	private boolean hasRowNo = true;

	public Boolean getHasRowNo() {
		return this.hasRowNo;
	}

	public void setHasRowNo(Boolean hasRowNo) {
		if (hasRowNo != null) {
			this.hasRowNo = hasRowNo;
		}
	}

	/**
	 * 数据块行高
	 */
	private short iDataRowHeight = 15;

	public Short getDataRowHeight() {
		return this.iDataRowHeight;
	}

	public void setDataRowHeight(Short rowHeight) {
		if (rowHeight != null) {
			this.iDataRowHeight = rowHeight;
		}
	}
}

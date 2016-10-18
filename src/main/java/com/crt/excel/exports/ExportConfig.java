/**
 * 
 */
package com.crt.excel.exports;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.crt.excel.exports.models.ExportColumn;

/**
 * @author UOrder
 *
 */
public class ExportConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7228802994814293292L;

	public ExportConfig(List<ExportColumn> exportColumns, List<Map<String, Object>> data, String exportDirectory) {
		this.exportColumns = exportColumns;
		this.data = data;
		this.exportDirectory = exportDirectory;
	}

	private String exportDirectory;

	public String getExportDirectory() {
		return this.exportDirectory;
	}

	private List<ExportColumn> exportColumns;

	public List<ExportColumn> getColumns() {
		return this.exportColumns;
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

	/**
	 * 是否有合计行
	 */
	private Boolean hasSumRow = false;

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
	private Boolean hasRowNo = true;

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
	private Short dataRowHeight = 15;

	public Short getDataRowHeight() {
		return this.dataRowHeight;
	}

	public void setDataRowHeight(Short rowHeight) {
		if (rowHeight != null && rowHeight > 0) {
			this.dataRowHeight = rowHeight;
		}
	}

	/**
	 * 表头行行高
	 */
	private Short headRowHeight = 15;

	public Short getHeadRowHeight() {
		return this.headRowHeight;
	}

	public void setHeadRowHeight(Short rowHeight) {
		if (rowHeight != null && rowHeight > 0) {
			this.headRowHeight = rowHeight;
		}
	}
}

/**
 * 
 */
package com.crt.excel.imports.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UOrder
 *
 */
public class ImportResult {

	private long totalCount;

	private long successCount;

	private List<String> errors = new ArrayList<String>();

	public void addResult(String message) {
		if (null != message && !message.trim().isEmpty()) {
			errors.add(message);
		} else {
			successCount += 1;
		}
		totalCount += 1;
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean isSuccess() {
		return errors.isEmpty();
	}

	public Long getTotalCount() {
		return this.totalCount;
	}

	public Long getSuccessCount() {
		return this.successCount;
	}
}

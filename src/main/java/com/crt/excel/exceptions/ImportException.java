/**
 * 
 */
package com.crt.excel.exceptions;

/**
 * @author UOrder
 *
 */
public class ImportException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5006650435397642529L;

	public ImportException(ImportExceptionEnum exEnum) {
        super(exEnum.getCaption());
    }

    public ImportException(Throwable exception) {
        super(ImportExceptionEnum.Unknown.getCaption(), exception);
    }
    
}

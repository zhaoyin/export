package com.crt.excel.exceptions;

import com.crt.excel.exceptions.ExportExceptionEnum;
/**
 * An unexpected exception
 */
public class ExportException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5006650435397642529L;

	public ExportException(ExportExceptionEnum exEnum) {
        super(exEnum.getCaption());
    }

    public ExportException(Throwable exception) {
        super(ExportExceptionEnum.Unknown.getCaption(), exception);
    }
    
}


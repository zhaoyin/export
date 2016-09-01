package com.crt.export.exception;

import com.crt.export.exception.ExceptionEnum;
/**
 * An unexpected exception
 */
public class ExportException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5006650435397642529L;

	public ExportException(ExceptionEnum exEnum) {
        super(exEnum.getCaption());
    }

    public ExportException(Throwable exception) {
        super(ExceptionEnum.Unknown.getCaption(), exception);
    }
    
}


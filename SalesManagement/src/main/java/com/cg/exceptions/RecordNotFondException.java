package com.cg.exceptions;

public class RecordNotFondException extends RuntimeException {
	
	public RecordNotFondException() {
		super("Record not found.");
	}
	
	public RecordNotFondException(String errorMsg) {
		super(errorMsg);
	}
}

package com.cg.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.bean.APIResponse;
import com.cg.exceptions.RecordNotFondException;
import com.cg.utils.APIConstants;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public APIResponse exception(Exception exception) {		
		return new APIResponse(APIConstants.API_RESPONSE_STATUS_FAILD, exception.getMessage());
	}
	
	@ExceptionHandler(value = RecordNotFondException.class)
	@ResponseBody
	public APIResponse recordNotFound(RecordNotFondException exception) {	
		logger.error(exception.getMessage());
		return new APIResponse(APIConstants.API_RESPONSE_STATUS_FAILD, "Record not found.");
	}
}

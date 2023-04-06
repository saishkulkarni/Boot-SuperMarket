package org.jsp.super_market.exception;

import org.jsp.super_market.helper.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler 
{
	@ExceptionHandler(value = AllException.class)
	public ResponseEntity<ResponseStructure<String>> allExceptionHandler(AllException ie){
		ResponseStructure<String> responseStructure = new ResponseStructure<String>() ;
		responseStructure.setStatuscode(HttpStatus.NOT_ACCEPTABLE.value());
		responseStructure.setMessage("There is an Exception");
		responseStructure.setData(ie.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.NOT_ACCEPTABLE);
	}
}

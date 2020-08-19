package com.faire.lab.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.faire.lab.exception.BadRequestExcepion;

@ControllerAdvice
public class WeatherReportControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<BadRequestExcepion> exception(Exception exception) {
		BadRequestExcepion exceptionResponse= new BadRequestExcepion();
		return new ResponseEntity<BadRequestExcepion>(HttpStatus.BAD_REQUEST);
	}
}
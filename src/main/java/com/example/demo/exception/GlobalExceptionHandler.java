package com.example.demo.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails error = new ErrorDetails(new Date(), request.getDescription(false), details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), details);
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<?> handleInputValidationException(MethodArgumentNotValidException ex,
				WebRequest request) {
			List<String> details = new ArrayList<>();
			for(ObjectError error : ex.getBindingResult().getAllErrors()) {
	            details.add(error.getDefaultMessage());
	        }
			ErrorDetails errorDetails = new ErrorDetails(new Date(), "Input Validation Exception",
				details);
			return new ResponseEntity(errorDetails, HttpStatus.NOT_ACCEPTABLE);
		}
}

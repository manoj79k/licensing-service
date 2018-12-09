package com.mk.licenses.controllers;

import java.util.Optional;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mk.license.util.ExceptionResponse;
import com.mk.license.util.LicenseNotFoundException;


@ControllerAdvice(assignableTypes = LicenseServiceController.class)
@RequestMapping(produces = "application/vnd.error+json")
@RestController
public class LicenseControllerAdvice extends ResponseEntityExceptionHandler{
	
  @ExceptionHandler(LicenseNotFoundException.class) public ResponseEntity < VndErrors > notFoundException(final LicenseNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getId().toString());
    }

  private ResponseEntity < VndErrors > error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        System.out.println("manoj kumar message:::::::::::::::::::::::::::::::::::::::::::::::::::"+message);
        return new ResponseEntity < > (new VndErrors(logRef, message), httpStatus);
    }
	  
 @ExceptionHandler(Exception.class)
    public  ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("internale server Error");
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	 
/* @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
     BindingResult result = ex.getBindingResult();
     ExceptionResponse response = new ExceptionResponse();
     response.setErrorCode("Validation Error");
     response.setErrorMessage("Invalid inputs.");
    response.setErrors(ValidationUtil.fromBindingErrors(result));
     return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
 }
 */
 
 @Override
protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
	 BindingResult result = ex.getBindingResult();
     ExceptionResponse response = new ExceptionResponse();
     response.setErrorCode("Validation Error in Request");
     //response.setErrorMessage(result.toString());
     response.setErrors(ValidationUtil.fromBindingErrors(result));
     return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
}
}

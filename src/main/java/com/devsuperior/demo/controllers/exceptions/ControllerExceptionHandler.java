package com.devsuperior.demo.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> constraintViolationException(MethodArgumentNotValidException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		var err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		e.getBindingResult().getFieldErrors().stream().forEach(er -> {
			err.errors.add(new FieldMessage(er.getField(), er.getDefaultMessage()));
		});

		return ResponseEntity.status(status).body(err);
    }
}

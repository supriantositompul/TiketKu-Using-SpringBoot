package com.kelempok7.serverapp.handler;

import com.kelempok7.serverapp.models.dto.response.CustomResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class NotFoundError {
    @ExceptionHandler(NotFoundExceptionHandler.class)
    public ResponseEntity<CustomResponseError> studioNotFoundException(NotFoundExceptionHandler exception, WebRequest webRequest) {
        CustomResponseError error = new CustomResponseError();
        error.setCode(101);
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

package com.zoho.organization.advice;


import com.zoho.organization.exception.EmptyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zoho.organization.constrain.GlobalConstrain.NO_SUCH_METHOD_ERROR;
import static com.zoho.organization.constrain.GlobalConstrain.VALUE_CANNOT_BE_NULL;


@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(EmptyValueException.class)
    public ResponseEntity<?> handleEmptyValueException(EmptyValueException emptyValueException){
        return new ResponseEntity<>(VALUE_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchMethodError.class)
    public ResponseEntity<?> handleNoSuchMethodError(NoSuchMethodError noSuchMethodError){
        return new ResponseEntity<>(NO_SUCH_METHOD_ERROR,HttpStatus.NOT_FOUND);
    }
}

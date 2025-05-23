package com.zoho.userservice.advice;

import com.zoho.userservice.controller.UserController;
import com.zoho.userservice.exception.EmptyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zoho.userservice.constrain.GlobalConstrain.NO_SUCH_METHOD_ERROR;
import static com.zoho.userservice.constrain.GlobalConstrain.VALUE_CANNOT_BE_NULL;

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

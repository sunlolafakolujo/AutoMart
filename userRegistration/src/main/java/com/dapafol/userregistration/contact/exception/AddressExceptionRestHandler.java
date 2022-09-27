package com.dapafol.userregistration.contact.exception;

import com.dapafol.userregistration.errormessage.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class AddressExceptionRestHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ValidateErrorMessage> addressExceptionHandler(AddressException addressException,
                                                                        WebRequest request){

        ValidateErrorMessage vem =new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                addressException.getMessage(),
                request.getDescription(false),
                new Date());

        return ResponseEntity.ok(vem);
    }
}

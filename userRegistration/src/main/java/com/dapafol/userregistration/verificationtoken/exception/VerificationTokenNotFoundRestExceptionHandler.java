package com.dapafol.userregistration.verificationtoken.exception;

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
public class VerificationTokenNotFoundRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus
    @ExceptionHandler(VerificationTokenNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> validateErrorMessageResponseEntity(
            VerificationTokenNotFoundException verificationTokenNotFoundException, WebRequest request){

        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                verificationTokenNotFoundException.getMessage(),
                request.getDescription(false),
                new Date());
        return new ResponseEntity<>(vem, HttpStatus.OK);
    }
}

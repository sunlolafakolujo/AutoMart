package com.dapafol.userregistration.role.exception;

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
public class UserRoleRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus
    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> validateErrorMessageResponseEntity(UserRoleNotFoundException userRoleException,
                                                                                   WebRequest request){
        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                userRoleException.getMessage(),
                request.getDescription(false),
                new Date());
        return ResponseEntity.ok(vem);
    }
}

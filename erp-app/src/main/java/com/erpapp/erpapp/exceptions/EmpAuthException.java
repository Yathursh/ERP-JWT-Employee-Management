package com.erpapp.erpapp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EmpAuthException extends RuntimeException {

    public EmpAuthException(String message){
        super(message);
    }
}

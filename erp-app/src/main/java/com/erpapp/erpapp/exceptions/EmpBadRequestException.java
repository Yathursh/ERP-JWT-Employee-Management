package com.erpapp.erpapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmpBadRequestException extends RuntimeException{


    public  EmpBadRequestException(String message){
        super(message);
    }
}

package com.app.zinkworks.atm.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ATMApplicationException extends Exception {
    public ATMApplicationException(String message) {
        super(message);
    }
}

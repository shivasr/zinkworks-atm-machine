package com.app.zinkworks.accountservice.exceptions;

public class AccountsServiceException extends Exception {

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    private String returnCode;

    public AccountsServiceException(String returnCode, String message) {
        super(message);
        this.returnCode = returnCode;
    }
}

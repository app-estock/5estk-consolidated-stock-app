package com.market.stock.exception;

public class NoDataFoundException extends Exception{
    String message;

    public NoDataFoundException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NoDataFoundException: Company Code does not exist["+"message="+message+"]";
    }
}

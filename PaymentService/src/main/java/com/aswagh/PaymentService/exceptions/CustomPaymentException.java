package com.aswagh.PaymentService.exceptions;

public class CustomPaymentException extends RuntimeException{
    private String statusCode;
    public CustomPaymentException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

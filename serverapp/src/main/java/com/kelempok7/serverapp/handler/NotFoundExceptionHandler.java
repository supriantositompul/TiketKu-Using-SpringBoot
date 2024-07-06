package com.kelempok7.serverapp.handler;

public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(String message) {
        super(message);
    }
}

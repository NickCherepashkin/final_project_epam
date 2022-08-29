package com.drozdova.library.dao;

public class ConnectionPoolException extends Exception{
    private static final long serialVersionUID = -1447455815430894053L;

    public ConnectionPoolException (String message, Exception e) {
        super(message, e);
    }
}

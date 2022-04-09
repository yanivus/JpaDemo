package com.jpa2.demo.exception;

public class DbResultNotFoundException extends RuntimeException {
    public DbResultNotFoundException(String message) {
        super(message);
    }
}

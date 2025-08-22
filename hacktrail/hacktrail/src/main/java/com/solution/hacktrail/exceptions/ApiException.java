package com.solution.hacktrail.exceptions;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String categoryName,String massage) {
        super(String.format("Category %s Is %s",categoryName,massage));

    }
}

package com.quizwhale.apiserver.util;

public class CustomServiceException extends RuntimeException {

    public CustomServiceException(String msg) {
        super(msg);
    }

}

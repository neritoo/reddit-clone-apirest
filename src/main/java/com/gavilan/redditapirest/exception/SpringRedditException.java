package com.gavilan.redditapirest.exception;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}

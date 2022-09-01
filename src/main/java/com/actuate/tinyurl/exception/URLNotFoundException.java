package com.actuate.tinyurl.exception;

public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException(String id) {
        super("Could not find url " + id);
    }
}
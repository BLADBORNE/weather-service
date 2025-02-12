package com.weatherservice.application.backend.exceprion;

public final class APIKeyIsNotValidException extends RuntimeException {

    public APIKeyIsNotValidException(String message) {

        super(message);
    }
}

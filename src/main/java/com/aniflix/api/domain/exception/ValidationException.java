package com.aniflix.api.domain.exception;

import java.util.List;

import com.aniflix.api.domain.model.web.error.FieldError;

public class ValidationException extends RuntimeException {
    
    private final List<FieldError> fieldErrors;
    
    public ValidationException(final List<FieldError> fieldErrors) {

        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {

        return fieldErrors;
    }    
}

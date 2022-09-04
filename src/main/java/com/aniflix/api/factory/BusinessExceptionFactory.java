package com.aniflix.api.factory;

import org.springframework.http.HttpStatus;

import com.aniflix.api.domain.exception.BusinessException;

import static java.lang.String.format;

public class BusinessExceptionFactory {

    public static BusinessException createBusinessException(final HttpStatus httpStatus,
                                                            final String message,
                                                            final Object ... values) {

        return new BusinessException(httpStatus, format(message, values));
    }

    public static BusinessException createBusinessException(final String message, final Object ... values) {

        return createBusinessException(HttpStatus.INTERNAL_SERVER_ERROR, message, values);
    }    
}

package com.rocketsoft.admin.catalogo.domain.exceptions;

import com.rocketsoft.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final List<Error> anyErrors) {

        final var message = anyErrors.get(0).message();
        return new DomainException(message, anyErrors);
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(String aMessage, final Error anError) {
        return new DomainException(aMessage, List.of(anError));
    }

    public List<Error> getErrors() {
        return errors;
    }

}

package com.rocketsoft.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(final Error anError);

    ValidationHandler append(final ValidationHandler anHandler);

    ValidationHandler validade(final Validation aValidation);

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    List<Error> getErrors();


    default Error firstError() {

        if (hasErrors())
            return getErrors().get(0);

        return null;
    }

    ;

    interface Validation {
        void validate();
    }

}
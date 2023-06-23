package com.rocketsoft.admin.catalogo.domain.validation.handler;

import com.rocketsoft.admin.catalogo.domain.exceptions.DomainException;
import com.rocketsoft.admin.catalogo.domain.validation.Error;
import com.rocketsoft.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error error) {
        return create().append(error);
    }

    public static Notification create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validade(final Validation aValidation) {

        try {
            aValidation.validate();
        } catch (final DomainException e) {
            this.errors.addAll(e.getErrors());
        } catch (final Exception e) {
            this.errors.add(new Error(e.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

}

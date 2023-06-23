package com.rocketsoft.admin.catalogo.application.category.retrive.get;

import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;
import com.rocketsoft.admin.catalogo.domain.exceptions.DomainException;
import com.rocketsoft.admin.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var from = CategoryID.from(anIn);
        return this.categoryGateway.findByID(from)
                .map(CategoryOutput::from)
                .orElseThrow();
    }

    private Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(
                new Error("Category with ID %s not found".formatted(anId))
        );
    }
}

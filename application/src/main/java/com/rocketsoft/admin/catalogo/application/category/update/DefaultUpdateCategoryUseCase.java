package com.rocketsoft.admin.catalogo.application.category.update;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;
import com.rocketsoft.admin.catalogo.domain.exceptions.DomainException;
import com.rocketsoft.admin.catalogo.domain.validation.Error;
import com.rocketsoft.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    private static Supplier<DomainException> notFount(CategoryID anId) {
        return () -> DomainException.with(new Error("Category with IS %s not found".formatted(anId)));
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var name = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.active();

        var aCategory = categoryGateway.findByID(anId).orElseThrow(notFount(anId));

        final var notification = Notification.create();

        aCategory
                .updateCategory(name, description, isActive)
                .validate(notification);

        return notification.hasErrors() ? Either.left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {

        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);

    }
}

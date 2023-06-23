package com.rocketsoft.admin.catalogo.application.category.create;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {

        var notification = Notification.create();

        var aCategory = Category.newCategory(aCommand.name(), aCommand.description(), aCommand.isActive());
        aCategory.validate(notification);

        return notification.hasErrors() ? Either.left(notification) : getRight(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> getRight(Category aCategory) {

        return Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::with);

    }
}

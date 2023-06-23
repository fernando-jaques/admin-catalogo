package com.rocketsoft.admin.catalogo.application.category.create;

import com.rocketsoft.admin.catalogo.application.UseCase;
import com.rocketsoft.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}

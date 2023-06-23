package com.rocketsoft.admin.catalogo.application.category.update;

import com.rocketsoft.admin.catalogo.application.UseCase;
import com.rocketsoft.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}

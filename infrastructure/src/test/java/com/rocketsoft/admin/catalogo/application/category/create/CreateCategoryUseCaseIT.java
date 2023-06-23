package com.rocketsoft.admin.catalogo.application.category.create;

import com.rocketsoft.admin.catalogo.IntegrationTest;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private DefaultCreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldCreateCategory() {

        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;

        var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        final var actualOutput = useCase.execute(aCommand);

    }
}

package com.rocketsoft.admin.catalogo.application.category.create;

import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenValidCommand_whenCallsCreateCategory_thenShouldReturnCategoryId() {
        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;

        var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.get().id());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory -> {
            Assertions.assertEquals(expectedName, aCategory.getName());
            Assertions.assertEquals(expectedDescription, aCategory.getDescription());
            Assertions.assertEquals(expectedActive, aCategory.isActive());
            Assertions.assertNotNull(aCategory.getId());
            Assertions.assertNotNull(aCategory.getCreatedAt());
            Assertions.assertNotNull(aCategory.getUpdatedAt());
            Assertions.assertNull(aCategory.getDeletedAt());
            return true;
        }));

    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {

        final var expectedDescription = "Films description";
        final var expectedActive = true;
        final var expectedExceptionMessage = "'name' should be not null";
        final var expectedErrorCount = 1;

        var aCommand = CreateCategoryCommand.with(null, expectedDescription, expectedActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedExceptionMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateway, Mockito.never()).create(any());
    }

    @Test
    public void givenInactiveCategory_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = false;

        var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.get().id());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory -> {
            Assertions.assertEquals(expectedName, aCategory.getName());
            Assertions.assertEquals(expectedDescription, aCategory.getDescription());
            Assertions.assertEquals(expectedActive, aCategory.isActive());
            Assertions.assertNotNull(aCategory.getId());
            Assertions.assertNotNull(aCategory.getCreatedAt());
            Assertions.assertNotNull(aCategory.getUpdatedAt());
            Assertions.assertNotNull(aCategory.getDeletedAt());
            return true;
        }));
    }

    @Test
    public void givenValidCommand_whenCallsCreateCategory_thenShouldReturnException() {
        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;
        final var expectedExceptionMessage = "Error on create category gateway";

        var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedExceptionMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedExceptionMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory -> {
            Assertions.assertEquals(expectedName, aCategory.getName());
            Assertions.assertEquals(expectedDescription, aCategory.getDescription());
            Assertions.assertEquals(expectedActive, aCategory.isActive());
            Assertions.assertNotNull(aCategory.getId());
            Assertions.assertNotNull(aCategory.getCreatedAt());
            Assertions.assertNotNull(aCategory.getUpdatedAt());
            Assertions.assertNull(aCategory.getDeletedAt());
            return true;
        }));
    }

    @Test
    public void givenValidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;
        final var expectedExceptionMessage = "Error on create category gateway";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        Mockito.when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedExceptionMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedExceptionMessage, notification.firstError().message());

    }
}

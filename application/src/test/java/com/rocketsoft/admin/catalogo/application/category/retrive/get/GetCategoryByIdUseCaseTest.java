package com.rocketsoft.admin.catalogo.application.category.retrive.get;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;
import com.rocketsoft.admin.catalogo.domain.exceptions.DomainException;
import com.rocketsoft.admin.catalogo.domain.validation.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidaId_whenCallsGetCategory_thenShouldReturnCategory() {

        final var category = Category.newCategory("Actions Films", "Desc Category", true);
        final var expectedId = category.getId();

        Mockito.when(categoryGateway.findByID(expectedId)).thenReturn(Optional.of(category.clone()));


        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(category.getName(), actualCategory.name());
        Assertions.assertEquals(category.getDescription(), actualCategory.description());
        Assertions.assertEquals(category.isActive(), actualCategory.isActive());

        Mockito.verify(categoryGateway, Mockito.times(1)).findByID(expectedId);

    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_thenShouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedMessageException = "Category with ID %s not found".formatted(expectedId.getValue());

        Mockito.when(categoryGateway.findByID(expectedId))
                .thenThrow(DomainException.with(new Error(expectedMessageException)));


        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        Mockito.verify(categoryGateway, Mockito.times(1)).findByID(expectedId);
    }

    @Test
    public void givenAInvalidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedId = CategoryID.from("123");
        final var expectedMessageException = "Category with ID %s not found".formatted(expectedId.getValue());

        Mockito.when(categoryGateway.findByID(expectedId))
                .thenThrow(DomainException.with(new Error(expectedMessageException)));

        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedMessageException, actualException.getMessage());

        Mockito.verify(categoryGateway, Mockito.times(1)).findByID(expectedId);
    }
}

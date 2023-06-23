package com.rocketsoft.admin.catalogo.application.category.delete;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidaId_whenCallsDeleteCategory_thenShouldthenShouldDeletedCategory() {

        final var category = Category.newCategory("Actions", "Desc Category", true);
        final var expectedId = category.getId();

        doNothing()
                .when(categoryGateway)
                .deleteByID(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteByID(eq(expectedId));

    }

    @Test
    public void givenAInvalidaId_whenGatewayThrowsError_thenShouldReturnException() {

        final var category = Category.newCategory("Actions", "Desc Category", true);
        final var expectedId = category.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteByID(eq(expectedId));

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteByID(eq(expectedId));
    }
}

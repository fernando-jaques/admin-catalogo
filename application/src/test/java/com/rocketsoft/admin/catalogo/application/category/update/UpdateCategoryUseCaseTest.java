package com.rocketsoft.admin.catalogo.application.category.update;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() {

        final var aCategory = Category.newCategory("Actions", null, true);

        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedActive
        );

        when(categoryGateway.findByID(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutPut = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutPut);
        Assertions.assertEquals(expectedId, actualOutPut.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findByID(eq(expectedId));

        Mockito.verify(categoryGateway, Mockito.times(1)).update(argThat(
                updateCategory -> {
                    Assertions.assertEquals(expectedName, updateCategory.getName());
                    Assertions.assertEquals(expectedDescription, updateCategory.getDescription());
                    Assertions.assertEquals(expectedActive, updateCategory.isActive());
                    Assertions.assertNotNull(updateCategory.getId());
                    Assertions.assertNotNull(updateCategory.getCreatedAt());
                    Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(updateCategory.getUpdatedAt()));
                    Assertions.assertNull(updateCategory.getDeletedAt());
                    return true;
                }));
    }
}
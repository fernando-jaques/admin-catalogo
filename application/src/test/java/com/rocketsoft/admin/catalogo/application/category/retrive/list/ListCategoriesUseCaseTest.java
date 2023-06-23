package com.rocketsoft.admin.catalogo.application.category.retrive.list;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.paginarion.CategorySearchQuery;
import com.rocketsoft.admin.catalogo.domain.paginarion.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }

    @Test
    public void giverAValidQuery_whenCallsListCategories_thenShouldReturnCategories() {

        final var categories = List.of(
                Category.newCategory("Films", null, true),
                Category.newCategory("Series", null, true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = "status";
        final var expectedTerm = "";
        final var expectedSort = "acreatedAt";
        final var expectedDirection = "ASC";

        final var aQuery = new CategorySearchQuery(
                expectedName,
                expectedDescription,
                expectedStatus,
                expectedTerm,
                expectedSort,
                expectedDirection,
                expectedPage,
                expectedPerPage
        );


        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItemsCount = 2;
        final var expectedResults = expectedPagination.map(CategoryListOutput::from);

        when(categoryGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPagination.currentPage(), actualResult.currentPage());
        Assertions.assertEquals(expectedPagination.perPage(), actualResult.perPage());
        Assertions.assertEquals(expectedPagination.total(), actualResult.total());
        Assertions.assertEquals(expectedPagination.total(), actualResult.total());
    }

    @Test
    public void giverAInvalidQuery_whenHasNoResults_thenShouldReturnCategories() {
        final var categories = List.<Category>of(
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = "status";
        final var expectedTerm = "";
        final var expectedSort = "acreatedAt";
        final var expectedDirection = "ASC";

        final var aQuery = new CategorySearchQuery(
                expectedName,
                expectedDescription,
                expectedStatus,
                expectedTerm,
                expectedSort,
                expectedDirection,
                expectedPage,
                expectedPerPage
        );


        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItemsCount = 0;
        final var expectedResults = expectedPagination.map(CategoryListOutput::from);

        when(categoryGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPagination.currentPage(), actualResult.currentPage());
        Assertions.assertEquals(expectedPagination.perPage(), actualResult.perPage());
        Assertions.assertEquals(expectedPagination.total(), actualResult.total());
        Assertions.assertEquals(expectedPagination.total(), actualResult.total());
    }

    @Test
    public void giverAValidQuery_whenGatewayThrowException_shouldReturnException() {

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = "status";
        final var expectedTerm = "";
        final var expectedSort = "acreatedAt";
        final var expectedDirection = "ASC";
        final var expectedErrorMessage = "Gateway error";

        final var aQuery = new CategorySearchQuery(
                expectedName,
                expectedDescription,
                expectedStatus,
                expectedTerm,
                expectedSort,
                expectedDirection,
                expectedPage,
                expectedPerPage
        );


        when(categoryGateway.findAll(eq(aQuery)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

}

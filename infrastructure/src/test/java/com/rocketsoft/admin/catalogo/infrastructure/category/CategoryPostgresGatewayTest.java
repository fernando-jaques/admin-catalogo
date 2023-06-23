package com.rocketsoft.admin.catalogo.infrastructure.category;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;
import com.rocketsoft.admin.catalogo.domain.paginarion.CategorySearchQuery;
import com.rocketsoft.admin.catalogo.PostgresGatewayTest;
import com.rocketsoft.admin.catalogo.infrastructure.category.CategoryPostgresGateway;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@PostgresGatewayTest
public class CategoryPostgresGatewayTest {

    @Autowired
    private CategoryPostgresGateway categoryGateway;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void givenAValidCategory_whenCallsCreate_thenShouldReturnANewCategory() {

        final var expectedName = "Films";
        final var expectedDescription = "Films description";
        final var expectedActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, repository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var category = categoryGateway.findByID(aCategory.getId()).orElseThrow();

        Assertions.assertEquals(aCategory.getId(), category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), category.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_thenShouldReturnCategoryUpdated() {

        final var expectedName = "Films";
        final var expectedDescription = "";
        final var expectedActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, repository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var category = categoryGateway.findByID(aCategory.getId()).orElseThrow();

        Assertions.assertEquals(aCategory.getId(), category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), category.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());


        Category categoryUpdate = category.updateCategory("Films", "Films description", true).clone();

        final var actualCategoryUpdated = categoryGateway.update(categoryUpdate);

        Assertions.assertNotNull(actualCategoryUpdated);
        Assertions.assertEquals(aCategory.getId(), actualCategoryUpdated.getId());
        Assertions.assertEquals(expectedName, actualCategoryUpdated.getName());
        Assertions.assertEquals("Films description", actualCategoryUpdated.getDescription());
        Assertions.assertEquals(expectedActive, actualCategoryUpdated.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategoryUpdated.getCreatedAt());
        Assertions.assertNotEquals(aCategory.getUpdatedAt(), actualCategoryUpdated.getUpdatedAt());
        Assertions.assertNull(actualCategoryUpdated.getDeletedAt());

    }

    @Test
    public void givenAValidCategoryId_whenCallsDelete_thenShouldDeleteCategory() {

        final var expectedName = "Films";
        final var expectedDescription = "";
        final var expectedActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, repository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var category = categoryGateway.findByID(aCategory.getId()).orElseThrow();

        Assertions.assertEquals(aCategory.getId(), category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), category.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

        CategoryID categoryId = category.getId();

        categoryGateway.deleteByID(categoryId);

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAPrePersistedCategory_whenCallsFindAll_thenShouldReturnPaginated() {

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;
        final var expectedTotalPages = 1;

        final var documentaries = Category.newCategory("Documentaries", "Documentaries Category description", true);
        final var films = Category.newCategory("Films", "Films description", true);
        final var series = Category.newCategory("Series", "Series Category description", true);

        Assertions.assertEquals(0, repository.count());


        repository.saveAll(List.of(
                CategoryEntity.from(documentaries),
                CategoryEntity.from(films),
                CategoryEntity.from(series)
        ));

        Assertions.assertEquals(3, repository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "ASC");
        final var categoriesResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, categoriesResult.currentPage());
        Assertions.assertEquals(expectedPerPage, categoriesResult.perPage());
        Assertions.assertEquals(expectedTotal, categoriesResult.total());
        Assertions.assertEquals(expectedTotalPages, categoriesResult.items().size());

        Assertions.assertEquals(documentaries.getId(), categoriesResult.items().get(0).getId());
        Assertions.assertEquals(documentaries.getName(), categoriesResult.items().get(0).getName());


    }

    @Test
    public void givenEmptyCategoriesTable_whenCallsFindAll_thenShouldReturnEmptyPage() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;
        final var expectedTotalPages = 1;

        final var films = Category.newCategory("Films", "Films description", true);
        final var series = Category.newCategory("Series", "Series Category description", true);
        final var documentaries = Category.newCategory("Documentaries", "Documentaries Category description", true);

        Assertions.assertEquals(0, repository.count());


        repository.saveAll(List.of(
                CategoryEntity.from(documentaries),
                CategoryEntity.from(films),
                CategoryEntity.from(series)

        ));

        Assertions.assertEquals(3, repository.count());

        var query = new CategorySearchQuery(0, 1, "Documentaries", "name", "ASC");
        var categoriesResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, categoriesResult.currentPage());
        Assertions.assertEquals(expectedPerPage, categoriesResult.perPage());
        Assertions.assertEquals(expectedTotal, categoriesResult.total());
        Assertions.assertEquals(expectedTotalPages, categoriesResult.items().size());

        Assertions.assertEquals(documentaries.getId(), categoriesResult.items().get(0).getId());
        Assertions.assertEquals(documentaries.getName(), categoriesResult.items().get(0).getName());


        query = new CategorySearchQuery(0, 1, "Films", "name", "ASC");
        categoriesResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, categoriesResult.currentPage());
        Assertions.assertEquals(films.getId(), categoriesResult.items().get(0).getId());
        Assertions.assertEquals(films.getName(), categoriesResult.items().get(0).getName());

        query = new CategorySearchQuery(0, 1, "Series", "name", "ASC");
        categoriesResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, categoriesResult.currentPage());
        Assertions.assertEquals(series.getId(), categoriesResult.items().get(0).getId());
        Assertions.assertEquals(series.getName(), categoriesResult.items().get(0).getName());
    }

}

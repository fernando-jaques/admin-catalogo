package com.rocketsoft.admin.catalogo.domain;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.exceptions.DomainException;
import com.rocketsoft.admin.catalogo.domain.validation.Error;
import com.rocketsoft.admin.catalogo.domain.validation.handler.ThowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    public static final int ONE_ERROR = 1;

    @Test
    public void givenValidParams_whenCallNewCategory_thenReturnACategory() {


        final var expectedName = "Category Action Movies";

        final var actualCategory = Category.newCategory(expectedName, "A new category action movies", true);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals("A new category action movies", actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceveAnErrors() {


        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(null, expectedDescription, true);


        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> actualCategory.validate(new ThowsValidationHandler()));

        Error expectedError = actualException.getErrors().get(0);

        Assertions.assertEquals("'name' should be not null", expectedError.message());
        Assertions.assertEquals(ONE_ERROR, actualException.getErrors().size());

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNull(actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceveAnErrors() {


        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(" ", expectedDescription, true);


        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> actualCategory.validate(new ThowsValidationHandler()));

        Error expectedError = actualException.getErrors().get(0);

        Assertions.assertEquals("'name' should be not empty", expectedError.message());
        Assertions.assertEquals(ONE_ERROR, actualException.getErrors().size());
    }

    @Test
    public void givenInvalidLengthLessThan3Name_whenCallNewCategoryAndValidate_thenShouldReceveAnErrors() {


        final var expectedName = "fe ";
        final var expectedErrorMenssage = "'name' should be at least 3 characters";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);


        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> actualCategory.validate(new ThowsValidationHandler()));

        Error expectedError = actualException.getErrors().get(0);

        Assertions.assertEquals(expectedErrorMenssage, expectedError.message());
        Assertions.assertEquals(ONE_ERROR, actualException.getErrors().size());
    }

    @Test
    public void givenInvalidLengthMoreThan255Name_whenCallNewCategoryAndValidate_thenShouldReceveAnErrors() {


        final var expectedName = """
                loremp ipsum dolor sit amet, consectetur adipiscing elit.
                Donec auctor, nisl eget aliquam aliquam, nisl nisl aliquam nisl, nec aliquam nisl nisl nec.
                Donec auctor, nisl eget aliquam aliquam, nisl nisl aliquam nisl, nec aliquam nisl nisl nec.
                Donec auctor, nisl eget aliquam aliquam, nisl nisl aliquam nisl, nec aliquam nisl nisl nec.
                """;

        final var expectedErrorMenssage = "'name' should be at most 255 characters";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);


        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> actualCategory.validate(new ThowsValidationHandler()));

        Error expectedError = actualException.getErrors().get(0);

        Assertions.assertEquals(expectedErrorMenssage, expectedError.message());
        Assertions.assertEquals(ONE_ERROR, actualException.getErrors().size());
    }

    @Test
    public void givenAValidInactive_whenCallNewCategoryAndValidate_thenReturnCategoryInactive() {


        final var expectedName = "Films";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(
                () -> actualCategory.validate(new ThowsValidationHandler())
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActive_whenCallNewCategoryAndValidate_thenReturnCategoryActive() {
        final var expectedName = "Films";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(
                () -> actualCategory.validate(new ThowsValidationHandler())
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidNotActive_whenCallNewCategoryAndValidate_thenReturnCategoryNotActive() {
        final var expectedName = "Films";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(
                () -> actualCategory.validate(new ThowsValidationHandler())
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        actualCategory.desactive();

        Assertions.assertFalse(actualCategory.isActive());
    }


    @Test
    public void givenAValidNotActiveToActive_whenCallNewCategoryAndValidate_thenReturnCategoryActive() {
        final var expectedName = "Films";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(
                () -> actualCategory.validate(new ThowsValidationHandler())
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        actualCategory.desactive();

        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

        actualCategory.active();

        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }


    @Test
    public void givenAValidCategotyUpdated_whenCallNewCategoryAndValidate_thenReturnCategoryUpdated() {
        final var expectedName = "Films";
        final var expectedDescription = "A new category action movies";

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(
                () -> actualCategory.validate(new ThowsValidationHandler())
        );

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        actualCategory.desactive();

        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

        actualCategory.active();

        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNull(actualCategory.getDeletedAt());

        actualCategory.updateCategory("Films updated", "A new category action movies updated", false);

        Assertions.assertEquals("Films updated", actualCategory.getName());
        Assertions.assertEquals("A new category action movies updated", actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

    }
}


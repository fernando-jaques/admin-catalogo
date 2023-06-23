package com.rocketsoft.admin.catalogo.infrastructure.category;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.PostgresGatewayTest;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@PostgresGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void giverAnInvalidNullName_whenCallsSave_shouldReturnError() {

        final var aCategory = Category.newCategory(null, "Films description", true);

        final var categoryEntity = CategoryEntity.from(aCategory);

        var actualException = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> repository.save(categoryEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        final var expectedCause = "not-null property references a null or transient value : com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryEntity.name";

        Assertions.assertEquals(expectedCause, actualCause.getMessage());


    }
}

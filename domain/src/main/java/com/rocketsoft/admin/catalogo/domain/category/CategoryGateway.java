package com.rocketsoft.admin.catalogo.domain.category;

import com.rocketsoft.admin.catalogo.domain.paginarion.CategorySearchQuery;
import com.rocketsoft.admin.catalogo.domain.paginarion.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);

    Category update(Category aCategory);

    void deleteByID(CategoryID anID);

    Optional<Category> findByID(CategoryID anID);

    Optional<Category> find(CategorySearchQuery aQuery);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}

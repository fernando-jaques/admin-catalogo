package com.rocketsoft.admin.catalogo.application.category.create;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput with(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}

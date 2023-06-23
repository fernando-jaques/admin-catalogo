package com.rocketsoft.admin.catalogo.application.category.retrive.list;

import com.rocketsoft.admin.catalogo.application.UseCase;
import com.rocketsoft.admin.catalogo.domain.paginarion.CategorySearchQuery;
import com.rocketsoft.admin.catalogo.domain.paginarion.Pagination;

public abstract class ListCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}

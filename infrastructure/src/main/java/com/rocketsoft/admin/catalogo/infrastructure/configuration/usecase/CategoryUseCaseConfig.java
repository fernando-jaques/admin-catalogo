package com.rocketsoft.admin.catalogo.infrastructure.configuration.usecase;


import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.rocketsoft.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.rocketsoft.admin.catalogo.application.category.retrive.list.DefaultListCategoriesUseCase;
import com.rocketsoft.admin.catalogo.application.category.retrive.list.ListCategoryUseCase;
import com.rocketsoft.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.rocketsoft.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public static final String CREATE_CATEGORY_USE_CASE = "createCategoryUseCase";
    public static final String UPDATE_CATEGORY_USE_CASE = "updateCategoryUseCase";
    public static final String DELETE_CATEGORY_USE_CASE = "deleteCategoryUseCase";
    public static final String LIST_CATEGORY_USE_CASE = "listCategoryUseCase";


    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean(name = CREATE_CATEGORY_USE_CASE)
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean(name = UPDATE_CATEGORY_USE_CASE)
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean(name = DELETE_CATEGORY_USE_CASE)
    public UpdateCategoryUseCase deleteCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean(name = LIST_CATEGORY_USE_CASE)
    public ListCategoryUseCase listCategoryUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

}

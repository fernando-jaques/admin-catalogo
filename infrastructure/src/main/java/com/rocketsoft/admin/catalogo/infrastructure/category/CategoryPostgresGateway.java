package com.rocketsoft.admin.catalogo.infrastructure.category;

import com.rocketsoft.admin.catalogo.domain.category.Category;
import com.rocketsoft.admin.catalogo.domain.category.CategoryGateway;
import com.rocketsoft.admin.catalogo.domain.category.CategoryID;
import com.rocketsoft.admin.catalogo.domain.paginarion.CategorySearchQuery;
import com.rocketsoft.admin.catalogo.domain.paginarion.Pagination;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.rocketsoft.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.rocketsoft.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryPostgresGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryPostgresGateway(CategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public Category create(Category aCategory) {
        Objects.requireNonNull(aCategory);
        return save(aCategory);
    }

    @Override
    public Category update(Category aCategory) {
        Objects.requireNonNull(aCategory);
        return save(aCategory);
    }

    private Category save(Category aCategory) {
        return this.repository.save(CategoryEntity.from(aCategory)).toAggregated();
    }

    @Override
    public void deleteByID(CategoryID anID) {
        Objects.requireNonNull(anID);
        this.repository.deleteById(anID.getValue());
    }

    @Override
    public Optional<Category> findByID(CategoryID anID) {
        return repository.findById(anID.getValue())
                .map(CategoryEntity::toAggregated);
    }

    @Override
    public Optional<Category> find(CategorySearchQuery aQuery) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery aQuery) {

        final var pagination = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(
                        Direction.fromString(aQuery.direction()),
                        aQuery.sort()
                )
        );

        var specifications = Optional.ofNullable(aQuery.term())
                .filter(str -> !str.isBlank())
                .map(str ->
                        {
                            Specification<CategoryEntity> nameLike = SpecificationUtils.<CategoryEntity>like("name", str);
                            Specification<CategoryEntity> descriptionLike = SpecificationUtils.like("description", str);
                            return nameLike.or(descriptionLike);
                        }
                ).orElse(null);


        final var pageResult = repository.findAll(Specification.where(specifications), pagination);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryEntity::toAggregated).toList()
        );
    }
}

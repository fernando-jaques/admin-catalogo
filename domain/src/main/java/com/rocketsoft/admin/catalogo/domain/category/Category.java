package com.rocketsoft.admin.catalogo.domain.category;

import com.rocketsoft.admin.catalogo.domain.AggregateRoot;
import com.rocketsoft.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant anUpdateDate,
            final Instant aDeleteDate) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "Creation date is required");
        this.updatedAt = Objects.requireNonNull(anUpdateDate, "Update date is required");
        this.deletedAt = aDeleteDate;
    }

    public static Category with(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(anId, aName, aDescription, isActive, createdAt, updatedAt, deletedAt);
    }

    public static Category newCategory(final String name, final String description, final boolean active) {

        var id = CategoryID.unique();
        var now = Instant.now();
        var deletedAt = active ? null : now;

        return with(id, name, description, active, now, now, deletedAt);
    }

    public Category updateCategory(final String name, final String description, final boolean active) {

        var now = Instant.now();

        if (this.active != active) this.deletedAt = now;

        this.updatedAt = now;

        this.name = name;
        this.description = description;
        this.active = active;

        return this;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void desactive() {
        this.active = !this.active;
        this.deletedAt = Instant.now();
    }

    public void active() {
        this.active = !this.active;
        this.deletedAt = null;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler);
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
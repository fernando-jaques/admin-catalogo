package com.rocketsoft.admin.catalogo.domain.category;

import com.rocketsoft.admin.catalogo.domain.validation.Error;
import com.rocketsoft.admin.catalogo.domain.validation.ValidationHandler;
import com.rocketsoft.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 255;
    private final Category aCategory;

    public CategoryValidator(final Category aCategory, final ValidationHandler anHandler) {
        super(anHandler);
        this.aCategory = aCategory;
        validate();
    }

    @Override
    public void validate() {
        var name = aCategory.getName();
        checkNameConstraints(name);

    }

    private void checkNameConstraints(String name) {
        if (name == null) {
            this.validationHandler().append(new Error("'name' should be not null"));
            return;
        }

        String nameTrim = name.trim();

        if (nameTrim.isEmpty()) {
            this.validationHandler().append(new Error("'name' should be not empty"));
            return;
        }

        if (nameTrim.length() < MIN_NAME_LENGTH)
            this.validationHandler().append(new Error("'name' should be at least 3 characters"));

        if (nameTrim.length() > MAX_NAME_LENGTH)
            this.validationHandler().append(new Error("'name' should be at most 255 characters"));
    }
}

package com.rocketsoft.admin.catalogo.domain.paginarion;

public record CategorySearchQuery(
        String name,
        String description,
        String status,
        String term,
        String sort,
        String direction,
        int page,
        int perPage
) {


    public CategorySearchQuery(int page, int perPage, String term, String sort, String direction) {
        this(null, null, null, term, sort, direction, page, perPage);
    }
}

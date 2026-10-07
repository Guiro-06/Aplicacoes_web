package com.example.imagemPecas.Infra.Repository.specs;

import org.springframework.data.jpa.domain.Specification;

public final class GenericSpecs {

    private GenericSpecs() {
    }

    public static <T> Specification<T> conjunction() {
        return (root, q, cb) -> cb.conjunction();
    }
}

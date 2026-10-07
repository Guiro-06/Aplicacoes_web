package com.example.imagemPecas.Infra.Repository.specs;

import com.example.imagemPecas.domain.entity.Image;
import com.example.imagemPecas.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

public final class ImageSpecs {

    private ImageSpecs() {
    }

    public static Specification<Image> extensionEqual(ImageExtension extension) {
        return (root, q, cb) ->
                cb.equal(root.get("extension"), extension);
    }

    public static Specification<Image> nameLike(String query) {
        return (root, q, cb) ->
                cb.like(
                        cb.upper(root.get("name")),
                        "%" + query.toUpperCase() + "%"
                );
    }

    public static Specification<Image> tagsLike(String query) {
        return (root, q, cb) ->
                cb.like(
                        cb.upper(root.get("tags")),
                        "%" + query.toUpperCase() + "%"
                );
    }
}

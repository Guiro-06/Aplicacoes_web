package com.example.imagemPecas.Infra.Repository;

import com.example.imagemPecas.domain.entity.Image;
import com.example.imagemPecas.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.imagemPecas.Infra.Repository.specs.GenericSpecs.conjunction;
import static com.example.imagemPecas.Infra.Repository.specs.ImageSpecs.extensionEqual;
import static com.example.imagemPecas.Infra.Repository.specs.ImageSpecs.nameLike;
import static com.example.imagemPecas.Infra.Repository.specs.ImageSpecs.tagsLike;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface ImageRepository extends JpaRepository<Image, String>,
        JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(
            ImageExtension extension,
            String query
    ) {
        Specification<Image> spec = where(conjunction());

        if (extension != null) {
            spec = spec.and(extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            Specification<Image> nameSpec = nameLike(query);
            Specification<Image> tagsSpec = tagsLike(query);

            Specification<Image> nameOrTagsLike = anyOf(nameSpec, tagsSpec);
            spec = spec.and(nameOrTagsLike);
        }

        return findAll(spec);
    }
}

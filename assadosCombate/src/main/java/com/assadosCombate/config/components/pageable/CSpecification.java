package com.assadosCombate.config.components.pageable;

import org.springframework.data.jpa.domain.Specification;

public class CSpecification<T> {

    public Specification<T> getSpecification(CPageable cPageable){

        Specification<T> specification = Specification.where(null);

        // Dynamic filters
        if (!cPageable.getFilter().isEmpty()) {
            for (CFilter filter : cPageable.getFilter()) {
                specification = specification.and((root, query, criteriaBuilder) -> {
                    if (filter.getValue() instanceof String) {
                        return criteriaBuilder.like(root.get(filter.getColumn()), "%" + filter.getValue() + "%");
                    }

                    if (filter.getValue() instanceof Boolean) {
                        if(filter.getValue().equals(true)) {
                            return criteriaBuilder.isTrue(root.get(filter.getColumn()));
                        }

                        return criteriaBuilder.isFalse(root.get(filter.getColumn()));
                    }

                    return criteriaBuilder.equal(root.get(filter.getColumn()), filter.getValue());
                });
            }
        }

        return specification;
    }
}

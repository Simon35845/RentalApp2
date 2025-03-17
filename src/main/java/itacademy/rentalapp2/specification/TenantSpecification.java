package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.TenantEntity;
import itacademy.rentalapp2.entity.TenantEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class TenantSpecification {
    public static Specification<TenantEntity> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(name)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(TenantEntity_.name)),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<TenantEntity> surnameContains(String surname) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(surname)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(TenantEntity_.surname)),
                    "%" + surname.toLowerCase() + "%"
            );
        };
    }

    public static Specification<TenantEntity> patronymicContains(String patronymic) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(patronymic)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(TenantEntity_.patronymic)),
                    "%" + patronymic.toLowerCase() + "%"
            );
        };
    }

    public static Specification<TenantEntity> ageContains(Integer age) {
        return (root, query, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(TenantEntity_.age), age);
        };
    }

    public static Specification<TenantEntity> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(email)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(TenantEntity_.email)),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }
}

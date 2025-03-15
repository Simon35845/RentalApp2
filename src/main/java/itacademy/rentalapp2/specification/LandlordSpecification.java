package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.LandlordEntity;
import itacademy.rentalapp2.entity.LandlordEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class LandlordSpecification {
    public static Specification<LandlordEntity> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(name)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(LandlordEntity_.name)),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<LandlordEntity> surnameContains(String surname) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(surname)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(LandlordEntity_.surname)),
                    "%" + surname.toLowerCase() + "%"
            );
        };
    }

    public static Specification<LandlordEntity> patronymicContains(String patronymic) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(patronymic)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(LandlordEntity_.patronymic)),
                    "%" + patronymic.toLowerCase() + "%"
            );
        };
    }

    public static Specification<LandlordEntity> ageContains(Integer age) {
        return (root, query, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(LandlordEntity_.age), age);
        };
    }

    public static Specification<LandlordEntity> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(email)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(LandlordEntity_.email)),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }
}

package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.entity.AddressEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AddressSpecification {
    public static Specification<AddressEntity> cityContains(String city) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(city)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(AddressEntity_.city)),
                    "%" + city.toLowerCase() + "%"
            );
        };
    }

    public static Specification<AddressEntity> streetContains(String street) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(street)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(AddressEntity_.street)),
                    "%" + street.toLowerCase() + "%"
            );
        };
    }
}

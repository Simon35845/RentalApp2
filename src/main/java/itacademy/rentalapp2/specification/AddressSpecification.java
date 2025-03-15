package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.entity.AddressEntity_;
import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.entity.ApartmentEntity_;
import jakarta.persistence.criteria.Join;
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

    public static Specification<AddressEntity> houseNumberContains(Integer houseNumber) {
        return (root, query, criteriaBuilder) -> {
            if (houseNumber == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(AddressEntity_.houseNumber), houseNumber);
        };
    }
}

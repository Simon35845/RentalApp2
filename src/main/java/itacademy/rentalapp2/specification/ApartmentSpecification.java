package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ApartmentSpecification {
    public static Specification<ApartmentEntity> apartmentNumberContains(Integer apartmentNumber) {
        return (root, query, criteriaBuilder) -> {
            if (apartmentNumber == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ApartmentEntity_.apartmentNumber), apartmentNumber);
        };
    }

    public static Specification<ApartmentEntity> floorContains(Integer floor) {
        return (root, query, criteriaBuilder) -> {
            if (floor == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ApartmentEntity_.floor), floor);
        };
    }

    public static Specification<ApartmentEntity> countOfRoomsContains(Integer countOfRooms) {
        return (root, query, criteriaBuilder) -> {
            if (countOfRooms == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ApartmentEntity_.countOfRooms), countOfRooms);
        };
    }

    public static Specification<ApartmentEntity> totalSquareContains(Double totalSquare) {
        return (root, query, criteriaBuilder) -> {
            if (totalSquare == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ApartmentEntity_.totalSquare), totalSquare);
        };
    }

    public static Specification<ApartmentEntity> cityContains(String city) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(city)) {
                return criteriaBuilder.conjunction();
            }
            Join<ApartmentEntity, AddressEntity> addressJoin = root.join(ApartmentEntity_.address);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get(AddressEntity_.city)),
                    "%" + city.toLowerCase() + "%"
            );
        };
    }

    public static Specification<ApartmentEntity> streetContains(String street) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(street)) {
                return criteriaBuilder.conjunction();
            }
            Join<ApartmentEntity, AddressEntity> addressJoin = root.join(ApartmentEntity_.address);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(addressJoin.get(AddressEntity_.street)),
                    "%" + street.toLowerCase() + "%"
            );
        };
    }

    public static Specification<ApartmentEntity> houseNumberContains(Integer houseNumber) {
        return (root, query, criteriaBuilder) -> {
            if (houseNumber == null) {
                return criteriaBuilder.conjunction();
            }
            Join<ApartmentEntity, AddressEntity> addressJoin = root.join(ApartmentEntity_.address);
            return criteriaBuilder.equal(addressJoin.get(AddressEntity_.houseNumber), houseNumber);
        };
    }

    public static Specification<ApartmentEntity> byLandlordId(Long landlordId) {
        return (root, query, criteriaBuilder) -> {
            if (landlordId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(ApartmentEntity_.landlord).get(LandlordEntity_.id), landlordId);
        };
    }

    public static Specification<ApartmentEntity> byTenantId(Long tenantId) {
        return (root, query, criteriaBuilder) -> {
            if (tenantId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<ApartmentEntity, TenantEntity> tenantJoin = root.join(ApartmentEntity_.tenants);
            return criteriaBuilder.equal(tenantJoin.get(TenantEntity_.id), tenantId);
        };
    }
}

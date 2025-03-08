package itacademy.rentalapp2.specification;

import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.entity.ApartmentEntity_;
import org.springframework.data.jpa.domain.Specification;

public class ApartmentSpecification {
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
}

package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {
}

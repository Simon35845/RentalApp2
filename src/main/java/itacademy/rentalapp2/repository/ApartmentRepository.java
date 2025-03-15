package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long>,
        JpaSpecificationExecutor<ApartmentEntity> {
    boolean existsByAddressId(Long addressId);
    List<ApartmentEntity> findByLandlordId(Long landlordId);
}

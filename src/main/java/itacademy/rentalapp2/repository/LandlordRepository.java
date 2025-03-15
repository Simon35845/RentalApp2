package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.ApartmentEntity;
import itacademy.rentalapp2.entity.LandlordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordEntity, Long>,
        JpaSpecificationExecutor<LandlordEntity> {
    @Query("SELECT a FROM ApartmentEntity a WHERE a.landlord.id = :landlordId")
    Page<ApartmentEntity> findApartmentsByLandlordId(@Param("landlordId") Long landlordId, Pageable pageable);
}

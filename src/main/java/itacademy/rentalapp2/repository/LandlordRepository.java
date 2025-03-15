package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.LandlordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordEntity, Long>,
        JpaSpecificationExecutor<LandlordEntity> {
}

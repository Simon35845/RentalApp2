package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.LandlordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<LandlordEntity, Long> {
}

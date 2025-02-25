package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Page<AddressEntity> findByCityIgnoreCase(String city, Pageable pageable);
    Page<AddressEntity> findByStreetIgnoreCase(String street, Pageable pageable);
    Page<AddressEntity> findByCityIgnoreCaseAndStreetIgnoreCase(String city, String street, Pageable pageable);
}

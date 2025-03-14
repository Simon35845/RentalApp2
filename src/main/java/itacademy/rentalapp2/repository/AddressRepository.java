package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends
        JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {
    List<AddressEntity> findAllByOrderByIdAsc();
}

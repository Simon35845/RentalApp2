package itacademy.rentalapp2.repository;

import itacademy.rentalapp2.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByName(String name);
}

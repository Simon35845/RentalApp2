package itacademy.rentalapp2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "tenant")
@PrimaryKeyJoinColumn(name = "person_id")
public class TenantEntity extends PersonEntity {
}

package itacademy.rentalapp2.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "apartments")
@EqualsAndHashCode(exclude = "apartments", callSuper = true)
@Entity
@Table(name = "tenants")
public class TenantEntity extends PersonEntity {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_apartment",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id"))
    @Builder.Default
    private Set<ApartmentEntity> apartments = new HashSet<>();
}

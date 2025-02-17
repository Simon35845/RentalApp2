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
@EqualsAndHashCode(exclude = "apartments", callSuper = false)
@ToString(exclude = "apartments")
@Entity
@Table(name = "landlord")
@PrimaryKeyJoinColumn(name = "person_id")
public class LandlordEntity extends PersonEntity {
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "landlord")
    private Set<ApartmentEntity> apartments = new HashSet<>();
}

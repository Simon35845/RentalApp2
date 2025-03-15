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
@Table(name = "landlords")
public class LandlordEntity extends PersonEntity {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY, mappedBy = "landlord")
    @Builder.Default
    private Set<ApartmentEntity> apartments = new HashSet<>();
}

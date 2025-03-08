package itacademy.rentalapp2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "apartments")
@EqualsAndHashCode(exclude = "apartments")
@Entity
@Table(name = "addresses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"city", "street", "house_number"})
})
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER, mappedBy = "address")
    @Builder.Default
    private Set<ApartmentEntity> apartments = new HashSet<>();
}

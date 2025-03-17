package itacademy.rentalapp2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"apartment_number", "address_id"})
})
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apartment_number", nullable = false)
    private Integer apartmentNumber;

    @Column(nullable = false)
    private Integer floor;

    @Column(name = "count_of_rooms", nullable = false)
    private Integer countOfRooms;

    @Column(name = "total_square")
    private Double totalSquare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private LandlordEntity landlord;

    @ManyToMany(mappedBy = "apartments", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TenantEntity> tenants = new HashSet<>();
}

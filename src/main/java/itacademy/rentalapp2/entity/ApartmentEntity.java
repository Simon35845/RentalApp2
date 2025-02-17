package itacademy.rentalapp2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"apartment_number", "address_id"})
})
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apartment_number", nullable = false)
    private Integer apartmentNumber;

    @Column
    private Integer floor;

    @Column(name = "count_of_rooms")
    private Integer countOfRooms;

    @Column(name = "total_square")
    private Double totalSquare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;
}

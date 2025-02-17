package itacademy.rentalapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDto {
    private Long id;
    private Integer apartmentNumber;
    private Integer floor;
    private Integer countOfRooms;
    private Double totalSquare;
    private Long addressId;
}

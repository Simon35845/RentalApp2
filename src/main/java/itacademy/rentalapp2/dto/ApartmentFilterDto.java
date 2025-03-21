package itacademy.rentalapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentFilterDto extends AddressFilterDto {
    private Integer apartmentNumber;
    private Integer floor;
    private Integer countOfRooms;
    private Double totalSquare;
}

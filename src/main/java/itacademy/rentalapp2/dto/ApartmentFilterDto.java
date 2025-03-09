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
public class ApartmentFilterDto extends PageFilterDto {
    private Integer floor;
    private Integer countOfRooms;
    private Double totalSquare;
    private String city;
    private String street;
    private Integer houseNumber;
}

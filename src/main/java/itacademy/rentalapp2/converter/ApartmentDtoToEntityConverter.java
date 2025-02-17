package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.entity.ApartmentEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApartmentDtoToEntityConverter implements Converter<ApartmentDto, ApartmentEntity> {
    @Override
    public ApartmentEntity convert(ApartmentDto dto) {
        return ApartmentEntity.builder()
                .id(dto.getId())
                .apartmentNumber(dto.getApartmentNumber())
                .floor(dto.getFloor())
                .countOfRooms(dto.getCountOfRooms())
                .totalSquare(dto.getTotalSquare())
                //.address(AddressEntity.builder().id(dto.getAddressId()).build())
                .build();
    }
}

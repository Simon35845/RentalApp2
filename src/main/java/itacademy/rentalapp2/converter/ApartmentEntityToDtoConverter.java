package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.entity.ApartmentEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApartmentEntityToDtoConverter implements Converter<ApartmentEntity, ApartmentDto> {
    @Override
    public ApartmentDto convert(ApartmentEntity entity) {
        return ApartmentDto.builder()
                .id(entity.getId())
                .apartmentNumber(entity.getApartmentNumber())
                .floor(entity.getFloor())
                .countOfRooms(entity.getCountOfRooms())
                .totalSquare(entity.getTotalSquare())
                //.addressId(entity.getAddress().getId())
                .build();
    }
}

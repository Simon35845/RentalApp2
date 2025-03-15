package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.LandlordDto;
import itacademy.rentalapp2.entity.LandlordEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LandlordEntityToDtoConverter implements Converter<LandlordEntity, LandlordDto> {
    @Override
    public LandlordDto convert(LandlordEntity entity) {
        return LandlordDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .patronymic(entity.getPatronymic())
                .age(entity.getAge())
                .email(entity.getEmail())
                .build();
    }
}

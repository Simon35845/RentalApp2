package itacademy.rentalapp2.converter;

import itacademy.rentalapp2.dto.LandlordDto;
import itacademy.rentalapp2.entity.LandlordEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LandlordDtoToEntityConverter implements Converter<LandlordDto, LandlordEntity> {
    @Override
    public LandlordEntity convert(LandlordDto dto) {
        return LandlordEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .patronymic(dto.getPatronymic())
                .age(dto.getAge())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}

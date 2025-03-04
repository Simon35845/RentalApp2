package itacademy.rentalapp2;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;

public class MockConstants {
    public static final AddressDto TEST_ADDRESS = AddressDto.builder()
            .city("Город 43")
            .street("Тестовая")
            .houseNumber(423)
            .build();

    public static final AddressFilterDto TEST_FILTER = AddressFilterDto.builder()
            .pageNumber(1)
            .pageSize(5)
            .build();
}

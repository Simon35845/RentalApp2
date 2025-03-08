package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import org.springframework.data.domain.Page;

public interface ApartmentService {
    ApartmentDto saveApartment(ApartmentDto apartmentDto);
    ApartmentDto updateApartment(Long id, ApartmentDto apartmentDto);
    void deleteApartment(Long id);
    ApartmentDto getApartmentById(Long id);
    Page<ApartmentDto> getApartmentsByFilter(ApartmentFilterDto filter);
}

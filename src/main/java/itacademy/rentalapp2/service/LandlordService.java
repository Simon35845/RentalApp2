package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.LandlordDto;
import itacademy.rentalapp2.dto.LandlordFilterDto;
import org.springframework.data.domain.Page;

public interface LandlordService {
    LandlordDto saveLandlord(LandlordDto landlordDto);
    LandlordDto updateLandlord(Long id, LandlordDto landlordDto);
    void deleteLandlord(Long id);
    LandlordDto getLandlordById(Long id);
    Page<LandlordDto> getLandlordsByFilter(LandlordFilterDto filter);
}

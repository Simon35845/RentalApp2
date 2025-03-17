package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.dto.TenantDto;
import itacademy.rentalapp2.dto.TenantFilterDto;
import org.springframework.data.domain.Page;

public interface TenantService {
    TenantDto saveTenant(TenantDto tenantDto);
    TenantDto updateTenant(Long id, TenantDto tenantDto);
    void deleteTenant(Long id);
    TenantDto getTenantById(Long id);
    Page<TenantDto> getTenantsByFilter(TenantFilterDto filter);
    Page<ApartmentDto> getApartmentsByTenantId(Long id, ApartmentFilterDto filter);
    Page<ApartmentDto> getAllApartments(ApartmentFilterDto filter);
    void joinApartmentToTenant(Long tenantId, Long apartmentId);
    void detachApartmentFromTenant(Long tenantId, Long apartmentId);
}

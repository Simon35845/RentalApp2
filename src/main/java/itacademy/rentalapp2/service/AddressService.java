package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import org.springframework.data.domain.Page;

public interface AddressService {
    AddressDto saveAddress(AddressDto addressDto);
    AddressDto updateAddress(Long id, AddressDto addressDto);
    void deleteAddress(Long id);
    AddressDto getAddressById(Long id);
    Page<AddressDto> getAllAddresses(int page, int size);
    Page<AddressDto> getAddressesByCity(String filter, int page, int size);
}

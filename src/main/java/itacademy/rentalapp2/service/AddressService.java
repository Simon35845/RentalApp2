package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import org.springframework.data.domain.Page;

public interface AddressService {
    AddressDto saveAddress(AddressDto addressDto);
    AddressDto updateAddress(Long id, AddressDto addressDto);
    void deleteAddress1(Long id);
    AddressDto getAddressById(Long id);
    Page<AddressDto> getAddressesByFilter(AddressFilterDto filter);
}

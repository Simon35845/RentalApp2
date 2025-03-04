package itacademy.rentalapp2.service;

import itacademy.rentalapp2.dto.AddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static itacademy.rentalapp2.MockConstants.TEST_ADDRESS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;

    @Test
    void saveAddress() {
        AddressDto savedAddress = addressService.saveAddress(TEST_ADDRESS);
        assertNotNull(savedAddress.getId());
        assertEquals(TEST_ADDRESS.getCity(), savedAddress.getCity());
    }

    @Test
    void updateAddress() {
        AddressDto updatedAddress = addressService.updateAddress(1L ,TEST_ADDRESS);
        assertEquals(1L, updatedAddress.getId());
        assertEquals(TEST_ADDRESS.getCity(), updatedAddress.getCity());
    }

    @Test
    void deleteAddress() {
        addressService.deleteAddress(1L);
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(1L));
    }

    @Test
    void getAddressById() {
        assertNotNull(addressService.getAddressById(2L));
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(22L));
    }
}
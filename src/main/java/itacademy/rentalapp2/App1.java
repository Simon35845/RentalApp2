package itacademy.rentalapp2;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.dto.PageFilterDto;
import itacademy.rentalapp2.entity.AddressEntity;
import itacademy.rentalapp2.service.AddressService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.SQLOutput;

@SpringBootApplication
public class App1 {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App1.class, args);
        AddressService addressService = context.getBean(AddressService.class);

        addressService.getAddressesByFilter(AddressFilterDto.builder().build());

        PageFilterDto pageFilterDto = new PageFilterDto();
        System.out.println("Page Number: " + pageFilterDto.getPageNumber());
        System.out.println("Page Size: " + pageFilterDto.getPageSize());

        AddressFilterDto addressFilterDto = new AddressFilterDto();
        System.out.println("Address Page Number: " + addressFilterDto.getPageNumber());
        System.out.println("Address Page Size: " + addressFilterDto.getPageSize());



        // Создание тестового AddressDto
        /*AddressDto addressDto1 = AddressDto.builder()
                .city("New York")
                .street("5th Avenue")
                .houseNumber("123")
                .build();

        AddressDto addressDto2 = AddressDto.builder()
                .city("Old Yourik")
                .street("55th Avenue")
                .houseNumber("127")
                .build();

        // Сохранение адреса
        AddressDto savedAddress1 = addressService.saveAddress(addressDto1);
        System.out.println("Saved Address: " + savedAddress1);

        // Получение адреса по ID
        AddressDto retrievedAddress1 = addressService.getAddressById(savedAddress1.getId());
        System.out.println("Retrieved Address: " + retrievedAddress1);

        // Обновление адреса
        AddressDto updatedAddress1 = AddressDto.builder()
                .city("Los Angeles")
                .street("Sunset Blvd")
                .houseNumber("456")
                .build();
        AddressDto result = addressService.updateAddress(savedAddress1.getId(), updatedAddress1);
        System.out.println("Updated Address: " + result);

        // Поиск адресов по фильтру
        AddressFilterDto filter1 = AddressFilterDto.builder()
                .city("Los")
                .street("Sun")
                .pageNumber(0)
                .pageSize(10)
                .build();
        Page<AddressDto> addresses = addressService.getAddressesByFilter(filter1);
        System.out.println("Filtered Addresses: " + addresses.getContent());

        // Удаление адреса
        addressService.deleteAddress1(savedAddress1.getId());
        System.out.println("Address deleted.");

        AddressDto savedAddress2 = addressService.saveAddress(addressDto1);
        System.out.println("Saved Address: " + savedAddress2);*/
    }

}


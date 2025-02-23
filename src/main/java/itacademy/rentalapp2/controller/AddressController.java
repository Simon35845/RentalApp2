package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public String getAllAddresses(@RequestParam String city,
                                  @RequestParam String street,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size,
                                  Model model) {
        Page<AddressDto> addresses = addressService.getAllAddresses(city, street, page, size);
        model.addAttribute("addresses", addresses);
        model.addAttribute("city", city);
        model.addAttribute("street", street);
        return "addresses/list";

    }

    @GetMapping("/save")
    public String showSaveForm(Model model) {
        model.addAttribute("address", new AddressDto());
        return "addresses/save";
    }

    @PostMapping("/save")
    public String saveAddress(@ModelAttribute AddressDto addressDto) {
        addressService.saveAddress(addressDto);
        return "redirect:/addresses";
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AddressDto addressDto = addressService.getAddressById(id);
        model.addAttribute("address", addressDto);
        return "addresses/edit";
    }

    @PostMapping("edit/{id}")
    public String updateAddress(@PathVariable Long id,
                                @ModelAttribute AddressDto addressDto) {
        addressService.updateAddress(id, addressDto);
        return "redirect:/addresses";
    }

    @GetMapping
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }
}


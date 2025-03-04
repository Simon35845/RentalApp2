package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAllAddresses(@ModelAttribute("addressFilter") AddressFilterDto addressFilter,
                                  Model model) {
            Page<AddressDto> addressesPage = addressService.getAddressesByFilter(addressFilter);
            model.addAttribute("addresses", addressesPage);
            model.addAttribute("addressFilter", addressFilter);
        return "addresses/list";
    }

    @GetMapping("/save")
//    @PreAuthorize("hasRole('ADMIN')")
    public String showSaveForm(Model model) {
        model.addAttribute("address", new AddressDto());
        return "addresses/save";
    }

    @PostMapping("/save")
//    @PreAuthorize("hasRole('ADMIN')")
    public String saveAddress(@Valid @ModelAttribute AddressDto addressDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "addresses/save";
        }
        addressService.saveAddress(addressDto);
        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        AddressDto addressDto = addressService.getAddressById(id);
        model.addAttribute("address", addressDto);
        return "addresses/edit";
    }

    @PostMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String updateAddress(@PathVariable Long id,
                                @Valid @ModelAttribute AddressDto addressDto,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "addresses/edit";
        }
        addressService.updateAddress(id, addressDto);
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}

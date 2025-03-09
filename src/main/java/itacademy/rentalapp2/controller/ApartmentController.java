package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @GetMapping
    public String getAllApartments(@ModelAttribute("apartmentFilter") ApartmentFilterDto apartmentFilter,
                                   Model model) {
        Page<ApartmentDto> apartmentsPage = apartmentService.getApartmentsByFilter(apartmentFilter);
        model.addAttribute("apartments", apartmentsPage);
        model.addAttribute("apartmentFilter", apartmentFilter);
        return "apartments/list";
    }

    @GetMapping("/save1")
    public String showSaveFormStep1(@ModelAttribute("addressFilter") AddressFilterDto addressFilter, Model model) {
        Page<AddressDto> addressesPage = apartmentService.getAddressesByFilter(addressFilter);
        model.addAttribute("addresses", addressesPage);
        model.addAttribute("addressFilter", addressFilter);
        model.addAttribute("apartment", new ApartmentDto());
        return "apartments/save1";
    }

    @PostMapping("/save1")
    public String processSaveFormStep1(@RequestParam Long addressId, RedirectAttributes redirectAttributes) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setAddressId(addressId); // Сохраняем выбранный addressId
        redirectAttributes.addFlashAttribute("apartment", apartmentDto);
        return "redirect:/apartments/save2";
    }

    // Шаг 2: Заполнение данных квартиры
    @GetMapping("/save2")
    public String showSaveFormStep2(@ModelAttribute("apartment") ApartmentDto apartmentDto, Model model) {
        model.addAttribute("apartment", apartmentDto);
        return "apartments/save2";
    }

    // Обработка сохранения квартиры
    @PostMapping("/save2")
    public String saveApartment(@ModelAttribute ApartmentDto apartmentDto) {
        apartmentService.saveApartment(apartmentDto);
        return "redirect:/apartments";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, @RequestParam(required = false) Long addressId, Model model) {
        ApartmentDto apartmentDto = apartmentService.getApartmentById(id);
        if (addressId != null) {
            apartmentDto.setAddressId(addressId);
        }
        model.addAttribute("apartment", apartmentDto);
        return "apartments/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateApartment(@PathVariable Long id, @ModelAttribute ApartmentDto apartmentDto) {
        apartmentService.updateApartment(id, apartmentDto);
        return "redirect:/apartments";
    }
}

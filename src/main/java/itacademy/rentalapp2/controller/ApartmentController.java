package itacademy.rentalapp2.controller;

import itacademy.rentalapp2.dto.AddressDto;
import itacademy.rentalapp2.dto.AddressFilterDto;
import itacademy.rentalapp2.dto.ApartmentDto;
import itacademy.rentalapp2.dto.ApartmentFilterDto;
import itacademy.rentalapp2.service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String showSaveForm1(@ModelAttribute("addressFilter") AddressFilterDto addressFilter, Model model) {
        Page<AddressDto> addressesPage = apartmentService.getAddressesByFilter(addressFilter);
        model.addAttribute("addresses", addressesPage);
        model.addAttribute("addressFilter", addressFilter);
        model.addAttribute("apartment", new ApartmentDto());
        return "apartments/save1";
    }

    @PostMapping("/save1")
    public String saveApartment1(@RequestParam Long addressId, RedirectAttributes redirectAttributes) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setAddressId(addressId);
        redirectAttributes.addFlashAttribute("apartment", apartmentDto);
        return "redirect:/apartments/save2";
    }

    @GetMapping("/save2")
    public String showSaveForm2(@ModelAttribute("apartment") ApartmentDto apartmentDto, Model model) {
        model.addAttribute("apartment", apartmentDto);
        return "apartments/save2";
    }

    @PostMapping("/save2")
    public String saveApartment2(@ModelAttribute ApartmentDto apartmentDto) {
        apartmentService.saveApartment(apartmentDto);
        return "redirect:/apartments";
    }

    @GetMapping("/edit1/{id}")
    public String showEditForm1(@PathVariable Long id, Model model) {
        ApartmentDto apartmentDto = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartmentDto);
        return "apartments/edit1";
    }

    @PostMapping("/edit1/{id}")
    public String updateApartment1(@PathVariable Long id,
                                   @Valid @ModelAttribute ApartmentDto apartmentDto,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "apartments/edit1";
        }
        apartmentService.updateApartment(id, apartmentDto);
        return "redirect:/apartments";
    }

    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return "redirect:/apartments";
    }
}

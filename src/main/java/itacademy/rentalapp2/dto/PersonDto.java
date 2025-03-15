package itacademy.rentalapp2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonDto {
    private Long id;

    @NotBlank(message = "поле \"Имя\" не может быть пустым")
    private String name;

    @NotBlank(message = "поле \"Фамилия\" не может быть пустым")
    private String surname;

    private String patronymic;
    private Integer age;

    @NotBlank(message = "поле \"e-mail\" не может быть пустым")
    private String email;
}

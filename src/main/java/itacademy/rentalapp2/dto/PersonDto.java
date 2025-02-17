package itacademy.rentalapp2.dto;

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
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
    private String phoneNumber;
    private String email;
}

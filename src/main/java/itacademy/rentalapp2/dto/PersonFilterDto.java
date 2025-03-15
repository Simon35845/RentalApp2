package itacademy.rentalapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonFilterDto extends PageFilterDto {
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
    private String email;
}

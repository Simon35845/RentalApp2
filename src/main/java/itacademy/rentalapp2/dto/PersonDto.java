package itacademy.rentalapp2.dto;

import jakarta.persistence.Column;

public abstract class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;
    private String phoneNumber;
    private String email;
}

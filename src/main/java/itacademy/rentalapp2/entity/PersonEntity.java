package itacademy.rentalapp2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname", "phone_number"})
})
public abstract class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private String patronymic;

    @Column
    private Integer age;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column
    private String email;
}

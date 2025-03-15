package itacademy.rentalapp2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageFilterDto {
    @Builder.Default
    @NotNull
    private int pageNumber = 1;
    @Builder.Default
    @NotNull
    private int pageSize = 5;
}

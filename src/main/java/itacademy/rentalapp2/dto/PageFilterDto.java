package itacademy.rentalapp2.dto;

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
    private int pageNumber = 1;
    @Builder.Default
    private int pageSize = 5;
}

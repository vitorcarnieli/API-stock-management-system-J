package br.gov.es.conceicaodocastelo.stock.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record ItemRecordDto(
        @Length(min = 1, max = 255) String name,
        @Nullable @Length(min = 1, max = 255) String unitType,
        @Nullable @Length(max = 255) String description,
        @Min(1) Double amount
        ) {
}

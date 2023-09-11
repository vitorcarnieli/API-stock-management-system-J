package br.gov.es.conceicaodocastelo.stock.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record ItemRecordDto(
        @Length(min = 1, max = 255) String name,
        @Nullable String unitType,
        @Nullable String description,
        @Min(1) Integer amount
        ) {
}

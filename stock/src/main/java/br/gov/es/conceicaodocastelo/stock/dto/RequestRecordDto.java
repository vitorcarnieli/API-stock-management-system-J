package br.gov.es.conceicaodocastelo.stock.dto;

import org.hibernate.validator.constraints.Length;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Institution;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestRecordDto(
        @NotNull @NotBlank @Length(max = 255) String name,
        @NotNull @Min(1) Integer requiredAmount,
        @NotNull Institution institution,
        @NotNull Item item) {

}

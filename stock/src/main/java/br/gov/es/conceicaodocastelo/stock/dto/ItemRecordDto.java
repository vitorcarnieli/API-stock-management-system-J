package br.gov.es.conceicaodocastelo.stock.dto;

import br.gov.es.conceicaodocastelo.stock.models.StockGroupModel;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record ItemRecordDto(@Length(min = 1, max = 255) String name, @Nullable @Length(max = 255) String description , @Min(1) Double amount) {
}

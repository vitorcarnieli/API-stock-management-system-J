package br.gov.es.conceicaodocastelo.stock.dto;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import jakarta.annotation.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record StockGroupRecordDto(@NotNull @NotBlank @Length(max = 255) String name, @Nullable @Length(max = 151) String description, @Nullable List<ItemModel> items) {
}

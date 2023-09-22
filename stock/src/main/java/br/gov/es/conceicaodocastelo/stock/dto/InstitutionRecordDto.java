package br.gov.es.conceicaodocastelo.stock.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstitutionRecordDto(@NotBlank @NotNull List<String> names) {

}

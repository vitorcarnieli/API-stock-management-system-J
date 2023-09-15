package br.gov.es.conceicaodocastelo.stock.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SchoolRecordDto(@NotBlank @NotNull String name) {
    
}

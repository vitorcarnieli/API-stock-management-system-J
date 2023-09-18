package br.gov.es.conceicaodocastelo.stock.dto;

import java.util.List;

public record OrderRecordDto(String name, String observations, String school, List<List<String>> requests) {
    
}

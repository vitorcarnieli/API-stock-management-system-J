package br.gov.es.conceicaodocastelo.stock.dto;

import java.util.List;

public record OrderRecordDto(String name, String observations, List<List<Object>> requests) {

}

package br.gov.es.conceicaodocastelo.stock.dto;

import java.util.List;

public record OrderCreateRequestDto(
        String institutionId,
        String nameOrder,
        String descriptionOrder,
        List<String> itemsId,
        List<String> amounts) {

}
